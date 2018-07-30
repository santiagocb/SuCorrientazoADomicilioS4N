package co.com.servicios

import co.com.entidades._

sealed trait ServicioDronAlgebra {
  def adelante(posicionActual: Posicion): Posicion
  def girarDerecha(posicionActual: Posicion): Posicion
  def realizarMovimiento(posicionActual: Posicion, instruccion: Instruccion): Posicion
}

sealed trait InterpretacionServicioDron extends ServicioDronAlgebra {
  def adelante(posicionActual: Posicion): Posicion = {
    posicionActual.orientacion match {
      case n: N => Posicion.newPosicion(Coordenada.newCoordenada(posicionActual.coordenada.x, posicionActual.coordenada.y + 1), posicionActual.orientacion)
      case n: S => Posicion.newPosicion(Coordenada.newCoordenada(posicionActual.coordenada.x, posicionActual.coordenada.y - 1), posicionActual.orientacion)
      case n: E => Posicion.newPosicion(Coordenada.newCoordenada(posicionActual.coordenada.x + 1, posicionActual.coordenada.y), posicionActual.orientacion)
      case n: O => Posicion.newPosicion(Coordenada.newCoordenada(posicionActual.coordenada.x - 1, posicionActual.coordenada.y), posicionActual.orientacion)
    }
  }

  def girarDerecha(posicionActual: Posicion): Posicion = {
    posicionActual.orientacion match {
      case n: N => Posicion.newPosicion(posicionActual.coordenada, E())
      case n: S => Posicion.newPosicion(posicionActual.coordenada, O())
      case n: E => Posicion.newPosicion(posicionActual.coordenada, S())
      case n: O => Posicion.newPosicion(posicionActual.coordenada, N())
    }
  }

  def girarIzquierda(posicionActual: Posicion): Posicion = {
    posicionActual.orientacion match {
      case n: N => Posicion.newPosicion(posicionActual.coordenada, O())
      case n: S => Posicion.newPosicion(posicionActual.coordenada, E())
      case n: E => Posicion.newPosicion(posicionActual.coordenada, N())
      case n: O => Posicion.newPosicion(posicionActual.coordenada, S())
    }
  }

  def realizarMovimiento(posicionActual: Posicion, instruccion: Instruccion): Posicion = {
    instruccion match {
      case i: A => adelante(posicionActual)
      case i: D => girarDerecha(posicionActual)
      case i: I => girarIzquierda(posicionActual)
    }
  }
}

object InterpretacionServicioDron extends InterpretacionServicioDron
