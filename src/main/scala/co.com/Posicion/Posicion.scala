package co.com.Posicion

import co.com.sustantivos.{Coordenada, Orientacion}

import scala.util.Try

case class Posicion(coordenada: Coordenada, orientacion: Orientacion)

object Posicion {
  private[this] def newPosicion(coordenada: Coordenada, orientacion: Orientacion): Posicion = {
    if (Math.abs(coordenada.x) > 10 || Math.abs(coordenada.y) > 10) throw new Exception("Posición INDEBIDA")
    else Posicion(coordenada, orientacion)
  }
  def newPosicionTry(coordenada: Coordenada, orientacion: Orientacion): Try[Posicion] = Try(newPosicion(coordenada, orientacion))
}