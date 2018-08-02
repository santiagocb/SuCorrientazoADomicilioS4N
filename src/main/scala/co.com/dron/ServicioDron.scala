package co.com.dron

import java.util.concurrent.Executors

import co.com.sustantivos.{Entrega, Posicion, _}

import scala.concurrent.{ExecutionContext, Future}

sealed trait ServicioDronAlgebra {
  def realizarRutaAsync(dron: Dron, ruta: Ruta): Future[Reporte]
}

sealed trait ServicioDron extends ServicioDronAlgebra {
  implicit val context = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(20))

  private def avanzar(posicionActual: Posicion): Posicion = {
    posicionActual.orientacion match {
      case n: N => Posicion(Coordenada(posicionActual.coordenada.x, posicionActual.coordenada.y + 1), posicionActual.orientacion)
      case n: S => Posicion(Coordenada(posicionActual.coordenada.x, posicionActual.coordenada.y - 1), posicionActual.orientacion)
      case n: E => Posicion(Coordenada(posicionActual.coordenada.x + 1, posicionActual.coordenada.y), posicionActual.orientacion)
      case n: O => Posicion(Coordenada(posicionActual.coordenada.x - 1, posicionActual.coordenada.y), posicionActual.orientacion)
    }
  }

  private def girarDerecha(posicionActual: Posicion): Posicion = {
    posicionActual.orientacion match {
      case n: N => Posicion(posicionActual.coordenada, E())
      case n: S => Posicion(posicionActual.coordenada, O())
      case n: E => Posicion(posicionActual.coordenada, S())
      case n: O => Posicion(posicionActual.coordenada, N())

    }
  }

  private def girarIzquierda(posicionActual: Posicion): Posicion = {
    posicionActual.orientacion match {
      case n: N => Posicion(posicionActual.coordenada, O())
      case n: S => Posicion(posicionActual.coordenada, E())
      case n: E => Posicion(posicionActual.coordenada, N())
      case n: O => Posicion(posicionActual.coordenada, S())
    }
  }

  private def realizarInstruccion(dron: Dron, instruccion: Instruccion): Dron = {
    instruccion match {
      case i: A => Dron(dron.id, avanzar(dron.posicionActual), dron.encargos)
      case i: D => Dron(dron.id, girarDerecha(dron.posicionActual), dron.encargos)
      case i: I => Dron(dron.id, girarIzquierda(dron.posicionActual), dron.encargos)
    }
  }

  private def mostrarRastro(dron: Dron, entrega: Entrega): List[Dron] = {
    entrega.entrega.foldLeft(List(dron))((huella, item) => {
      huella :+ realizarInstruccion(huella.last, item)
    })
  }

  private def realizarEntrega(dron: Dron, entrega: Entrega): Either[Dron, Dron] = {
    val res = entrega.entrega.foldLeft(List(dron))((huella, item) => {
      huella :+ realizarInstruccion(huella.last, item)
    }).last
    Dron.newDronTry(res.id, res.posicionActual, res.encargos - 1)
  }

  def realizarRutaAsync(dron: Dron, ruta: Ruta): Future[Reporte] = Future(realizarRutaSync(dron, ruta))

  private def realizarRutaSync(dron: Dron, ruta: Ruta): Reporte = {
    Reporte(ruta.listaEntrega.foldLeft(List[Either[Dron, Dron]](Right(dron)))
      ((reporte, entrega) => {
        reporte :+ reporte.last.fold(
          dronMalo => {
            if(dronMalo.encargos == 0)  realizarEntrega(volverACasa(dronMalo), entrega)
            else  Dron.newDronTry(dronMalo.id, dronMalo.posicionActual, dronMalo.encargos - 1)
          },
          dronBueno => {
            if(dronBueno.encargos == 0) realizarEntrega(volverACasa(dronBueno), entrega)
            else realizarEntrega(dronBueno, entrega)
          }
        )
      }).drop(1))
  }

  private def volverACasa(dron: Dron): Dron = {
    Dron(dron.id, Posicion(Coordenada(0, 0), N()), 10)
  }
}

object ServicioDron extends ServicioDron
