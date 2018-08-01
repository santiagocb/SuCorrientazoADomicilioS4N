package co.com.Sustantivos

case class Entrega(entrega: List[Instruccion])

object Entrega {
  def newEntrega(entregaString: String): Entrega = {
    Entrega(entregaString.toList.map(char => Instruccion.newInstruccionTry(char))
      .filter(tryI => tryI.isSuccess).map(tryI => tryI.get))
  }
}