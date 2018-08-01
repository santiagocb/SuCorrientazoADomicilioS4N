package co.com.Dron

import co.com.Sustantivos.Posicion

import scala.util.Try

case class Dron(id: Int, posicionActual: Posicion, encargos: Int)

object Dron {
  private[this] def newDron(id: Int, posicionActual: Posicion, encargos: Int): Dron = {
    if (Math.abs(posicionActual.coordenada.x) > 10 || Math.abs(posicionActual.coordenada.y) > 10) throw new Exception("Dron en posicion indebida")
    else Dron(id, posicionActual, encargos)
  }
  def newDronTry(id: Int, posicionActual: Posicion, encargos: Int): Try[Dron] = Try(newDron(id, posicionActual, encargos))
}