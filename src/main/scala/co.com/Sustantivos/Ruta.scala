package co.com.Sustantivos

case class Ruta(ruta: List[Entrega])

object Ruta {
  def newRuta(rutaString: List[String]): Ruta = Ruta(rutaString.map(str => Entrega.newEntrega(str)))
}