package co.com.Dron

import java.util.concurrent.Executors

import co.com.Sustantivos.{Entrega, Posicion, _}

import scala.concurrent.{ExecutionContext, Future}

sealed trait ServicioDronAlgebra {
  def realizarInstruccion(dron: Dron, instruccion: Instruccion): Dron
  def mostrarRastro(dron: Dron, entrega: Entrega): List[Dron]
  def realizarEntrega(dron: Dron, entrega: Entrega): Either[Dron, Dron]
  def realizarRuta(dron: Dron, ruta: Ruta): Future[Reporte]
}

sealed trait InterpretacionServicioDron extends ServicioDronAlgebra {
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

  def realizarInstruccion(dron: Dron, instruccion: Instruccion): Dron = {
    instruccion match {
      case i: A => Dron(dron.id, avanzar(dron.posicionActual), dron.encargos)
      case i: D => Dron(dron.id, girarDerecha(dron.posicionActual), dron.encargos)
      case i: I => Dron(dron.id, girarIzquierda(dron.posicionActual), dron.encargos)
    }
  }

  def mostrarRastro(dron: Dron, entrega: Entrega): List[Dron] = {
    entrega.entrega.foldLeft(List(dron))((huella, item) => {
      huella :+ realizarInstruccion(huella.last, item)
    })
  }

  def realizarEntrega(dron: Dron, entrega: Entrega): Either[Dron, Dron] = {
    val res = entrega.entrega.foldLeft(List(dron))((huella, item) => {
      huella :+ realizarInstruccion(huella.last, item)
    }).last
    Dron.newDronTry(res.id, res.posicionActual, res.encargos - 1)
  }

  def realizarRuta(dron: Dron, ruta: Ruta): Future[Reporte] = {
    implicit val context = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(5))
    val r: List[Either[Dron, Dron]] = List(Right(dron))
    Future(Reporte(ruta.ruta.foldLeft(r)((reporte, entrega) => {
      reporte :+ reporte.last.fold(
        dron => {
          if(dron.encargos == 0)  realizarEntrega(volverACasa(dron), entrega)
          else  Dron.newDronTry(dron.id, dron.posicionActual, dron.encargos - 1)
        },
        dron => {
          if(dron.encargos == 0) realizarEntrega(volverACasa(dron), entrega)
          else realizarEntrega(dron, entrega)
        }
      )
    }).drop(1)))
  }

  private def volverACasa(dron: Dron): Dron = {
    Dron(dron.id, Posicion(Coordenada(0, 0), N()), 3)
  }
}

object InterpretacionServicioDron extends InterpretacionServicioDron
