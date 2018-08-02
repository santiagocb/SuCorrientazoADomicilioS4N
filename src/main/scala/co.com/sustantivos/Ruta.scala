package co.com.sustantivos

case class Ruta(listaEntrega: List[Entrega])

object Ruta {
  def newRuta(rutaString: List[String]): Ruta = Ruta(rutaString.map(str => Entrega.newEntrega(str)))
}