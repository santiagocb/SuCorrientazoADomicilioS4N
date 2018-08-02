package co.com.dron

import co.com.sustantivos.Posicion

case class Dron(id: Int, posicionActual: Posicion, encargos: Int)

object Dron {
  private[this] def newDron(id: Int, posicionActual: Posicion, encargos: Int): Either[Dron, Dron] = {
    if (Math.abs(posicionActual.coordenada.x) > 10 || Math.abs(posicionActual.coordenada.y) > 10) Left(Dron(id, posicionActual, encargos))
    else Right(Dron(id, posicionActual, encargos))
  }
  def newDronTry(id: Int, posicionActual: Posicion, encargos: Int): Either[Dron, Dron] = newDron(id, posicionActual, encargos)
}