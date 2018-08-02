package co.com

import co.com.Dron.InterpretacionServicioDron
import co.com.Servicios.{InterpretacionServicioArchivo, InterpretacionServicioCorrientazo}
import co.com.Sustantivos._
import org.scalatest.FunSuite

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
    val pos = Posicion(Coordenada(11, 3), N())
  }

  test("Moviendo el dron de posición con una instrucción") {
    val char = 'A'
    val dron = Dron.Dron(1, Sustantivos.Posicion(Coordenada(0, 10), N()), 3)

    val dronArriba = Instruccion.newInstruccionTry(char).map(ins => InterpretacionServicioDron.realizarInstruccion(dron, ins))
    assert(dronArriba.isSuccess)
  }

  test("Creando una entrega") {
    val str = "AAA"
    val entrega = Entrega.newEntrega(str)
    assert(entrega == Sustantivos.Entrega(List(A(), A(), A())))
  }

  test("Mostrando rastro") {
    val entregaStr = "AAAAIAAD"
    val entrega = Entrega.newEntrega(entregaStr)
    val dron = Dron.Dron(1, Sustantivos.Posicion(Coordenada(0, 0), N()), 3)
    val entregadoEn = InterpretacionServicioDron.mostrarRastro(dron, entrega)
    //println(entregadoEn)
  }

  test("Mandando al dron a hacer una entrega") {
    val entregaStr = "AAAAIAAD"
    val entrega = Entrega.newEntrega(entregaStr)
    val dron = Dron.Dron(1, Sustantivos.Posicion(Coordenada(0, 0), N()), 3)
    val entregadoEn = InterpretacionServicioDron.realizarEntrega(dron, entrega)
    assert(entregadoEn == Right(Dron.Dron(1, Sustantivos.Posicion(Coordenada(-2, 4), N()), 2)))
  }

  test("Mandando al dron a hacer una entrega que sale del margen y vuelve") {
    val entregaStr = "AADDAAADD"
    val entrega = Entrega.newEntrega(entregaStr)
    val dron = Dron.Dron(1, Sustantivos.Posicion(Coordenada(0, 10), N()), 3)
    val entregadoEn = InterpretacionServicioDron.realizarEntrega(dron, entrega)
    assert(entregadoEn == Right(Dron.Dron(1,Posicion(Coordenada(0,9),N()),2)))
  }

  test("Mandando al dron a hacer una ruta (vuelve a casa a los 3 encargos)") {
    val archivo = List("AAAAIAAD", "DDAIAD", "DA", "DAAA")
    val ruta = Ruta.newRuta(archivo)
    val dron = Dron.Dron(1, Sustantivos.Posicion(Coordenada(0, 0), N()), 3)
  }

  test("Mandando al dron a hacer una ruta fallida") {
    val archivo = List("AAAAIAAD", "DDAIAD", "DA", "DAAA", "AAAAAAAA", "AA", "A", "AAAAA", "AAAAA","D")
    val ruta = Ruta.newRuta(archivo)
    val dron = Dron.Dron(1, Sustantivos.Posicion(Coordenada(0, 0), N()), 3)
    println(InterpretacionServicioDron.realizarRuta(dron, ruta))
  }

  test("Leyendo archivo") {
    val archivo = List("AAAAIAAD", "DDAIAD", "DA", "DAAA")
    val nombreArchivo = "in.txt"
    val despuesLeer = InterpretacionServicioArchivo.leerArchivo(nombreArchivo)
    assert(despuesLeer == archivo)
  }

  test("Corrientazo") {
    InterpretacionServicioCorrientazo.corrientizarDron("in.txt")
  }

}
