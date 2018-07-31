package co.com.Dron

import co.com.Entrega.Entrega
import co.com.Posicion.Posicion
import co.com.sustantivos._

import scala.util.Try

sealed trait ServicioDronAlgebra {
  def realizarInstruccion(dron: Dron, instruccion: Instruccion): Try[Dron]
  def mostrarRastro(dron: Dron, entrega: Entrega): List[Try[Dron]]
  def realizarEntrega(dron: Dron, entrega: Entrega): Try[Dron]
  def realizarRuta(dron: Dron, ruta: Ruta): List[Try[Dron]]
  def volverACasa(dron: Dron): Dron
}

sealed trait InterpretacionServicioDron extends ServicioDronAlgebra {
  private def avanzar(posicionActual: Posicion): Try[Posicion] = {
    posicionActual.orientacion match {
      case n: N => Posicion.newPosicionTry(Coordenada(posicionActual.coordenada.x, posicionActual.coordenada.y + 1), posicionActual.orientacion)
      case n: S => Posicion.newPosicionTry(Coordenada(posicionActual.coordenada.x, posicionActual.coordenada.y - 1), posicionActual.orientacion)
      case n: E => Posicion.newPosicionTry(Coordenada(posicionActual.coordenada.x + 1, posicionActual.coordenada.y), posicionActual.orientacion)
      case n: O => Posicion.newPosicionTry(Coordenada(posicionActual.coordenada.x - 1, posicionActual.coordenada.y), posicionActual.orientacion)
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

  def realizarInstruccion(dron: Dron, instruccion: Instruccion): Try[Dron] = {
    instruccion match {
      case i: A => avanzar(dron.posicionActual).map(nuevaPos => Dron(dron.id, nuevaPos, dron.encargos))
      case i: D => Try{Dron(dron.id, girarDerecha(dron.posicionActual), dron.encargos)}
      case i: I => Try{Dron(dron.id, girarIzquierda(dron.posicionActual), dron.encargos)}
    }
  }

  def mostrarRastro(dron: Dron, entrega: Entrega): List[Try[Dron]] = {
    entrega.entrega.foldLeft(List(Try{dron}))((huella, item) => {
      huella :+ item.flatMap(ins => huella.last.flatMap(lastDron => realizarInstruccion(lastDron, ins)))
    })
  }

  def realizarEntrega(dron: Dron, entrega: Entrega): Try[Dron] = {
    entrega.entrega.foldLeft(List(Try{dron}))((huella, item) => {
      huella :+ item.flatMap(ins => huella.last.flatMap(lastDron => realizarInstruccion(lastDron, ins)))
    }).last.map(dron => Dron(dron.id, dron.posicionActual, dron.encargos - 1))
  }

  def realizarRuta(dron: Dron, ruta: Ruta): List[Try[Dron]] = {
    ruta.ruta.foldLeft(List(Try{dron}))((reporte, entrega) => {
      reporte :+ reporte.last.flatMap(lastEntrega => realizarEntrega(lastEntrega, entrega))
    })
  }

  def volverACasa(dron: Dron): Dron = ???
}

object InterpretacionServicioDron extends InterpretacionServicioDron
