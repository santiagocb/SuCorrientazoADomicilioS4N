package co.com.sustantivos

import scala.util.Try

sealed trait Instruccion
case class A() extends Instruccion
case class I() extends Instruccion
case class D() extends Instruccion

object Instruccion {
  private[this] def newInstruccion(c: Char): Instruccion = {
    c match {
      case 'A' => A()
      case 'D' => D()
      case 'I' => I()
      case _ => throw new Exception(s"Caracter invalido para creacion de instruccion: $c")
    }
  }

  def newInstruccionTry(c: Char): Try[Instruccion] = { Try(newInstruccion(c)) }
}