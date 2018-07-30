package co.com.entidades

case class Coordenada (x: Int, y: Int)

object Coordenada {
  def newCoordenada(x: Int, y: Int): Coordenada = {
    if (Math.abs(x) > 10 || Math.abs(y) > 10) throw new Exception("Sobrepasa el límite del barrio")
    else Coordenada(x, y)
  }
}

