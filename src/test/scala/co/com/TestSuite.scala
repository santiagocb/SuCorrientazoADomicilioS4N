package co.com

import co.com.Dron.InterpretacionServicioDron
import co.com.sustantivos._
import org.scalatest.FunSuite

import scala.util.Success

class TestSuite extends FunSuite{
  test("Creating a coord") {
    assert(Coordenada(5, 5) == Coordenada(5, 5))
  }

  test("Creating a instrucción") {
    val inst = Instruccion.newInstruccionTry('A')
    val inst2 = Instruccion.newInstruccionTry('B')
    assert(!inst.isFailure)
    assert(inst2.isFailure)
  }

  test("Creación posición") {
    val pos = Posicion.Posicion.newPosicionTry(Coordenada(11 ,5), N())
    assert(pos.isFailure)
  }

  test("Moviendo el dron de posición con una instrucción") {
    val char = 'A'
    val dron = Dron.Dron(1, Posicion.Posicion(Coordenada(0, 10), N()), 3)

    val dronArriba = Instruccion.newInstruccionTry(char).flatMap(ins => InterpretacionServicioDron.realizarInstruccion(dron, ins))
    assert(dronArriba.isFailure)
  }

  test("Creando una entrega") {
    val str = "AAA"
    val entrega = Entrega.Entrega.newEntrega(str)
    assert(entrega == Entrega.Entrega(List(Success(A()), Success(A()), Success(A()))))
  }

  test("Mostrando rastro") {
    val entregaStr = "AAAAIAAD"
    val entrega = Entrega.Entrega.newEntrega(entregaStr)
    val dron = Dron.Dron(1, Posicion.Posicion(Coordenada(0, 0), N()), 3)
    val entregadoEn = InterpretacionServicioDron.mostrarRastro(dron, entrega)
    //println(entregadoEn)
  }

  test("Mandando al dron a hacer una entrega") {
    val entregaStr = "AAAAIAAD"
    val entrega = Entrega.Entrega.newEntrega(entregaStr)
    val dron = Dron.Dron(1, Posicion.Posicion(Coordenada(0, 0), N()), 3)
    val entregadoEn = InterpretacionServicioDron.realizarEntrega(dron, entrega)
    assert(entregadoEn == Success(Dron.Dron(1, Posicion.Posicion(Coordenada(-2, 4), N()), 2)))
  }

  test("Mandando al dron a hacer una ruta (vuelve a casa a los 3)") {
    val archivo = List("AAAAIAAD", "DDAIAD", "DA", "DAAA")
    val ruta = Ruta.newRuta(archivo)
    //println(ruta.ruta)
    val dron = Dron.Dron(1, Posicion.Posicion(Coordenada(0, 0), N()), 3)
    println(InterpretacionServicioDron.realizarRuta(dron, ruta))

  }
}
