package co.com.Sustantivos

sealed trait Orientacion
case class N() extends Orientacion
case class S() extends Orientacion
case class E() extends Orientacion
case class O() extends Orientacion
