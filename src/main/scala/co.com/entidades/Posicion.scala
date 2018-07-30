package co.com.entidades

import scala.util.Try

case class Posicion(coordenada: Coordenada, orientacion: Orientacion)

object Posicion {
  def newPosicion(coordenada: Coordenada, orientacion: Orientacion): Posicion = {
    if (Try(coordenada).isFailure) throw new Exception("Mala la coordenada")
    else Posicion(coordenada, orientacion)
  }
}