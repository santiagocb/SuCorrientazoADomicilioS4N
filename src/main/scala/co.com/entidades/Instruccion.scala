package co.com.entidades

sealed trait Instruccion
case class A() extends Instruccion
case class I() extends Instruccion
case class D() extends Instruccion

object Instruccion {
  def newInstruccion(c: Char): Instruccion = {
    c match {
      case 'A' => A()
      case 'D' => D()
      case 'I' => I()
      case _ => throw new Exception(s"Caracter invalido para creacion de instruccion: $c")
    }
  }
}