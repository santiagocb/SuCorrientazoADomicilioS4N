package co.com.Entrega

import co.com.sustantivos.Instruccion

import scala.util.Try

case class Entrega(entrega: List[Try[Instruccion]])

object Entrega {
  def newEntrega(entregaString: String): Entrega = Entrega(entregaString.toList.map(char => Instruccion.newInstruccionTry(char)))
}