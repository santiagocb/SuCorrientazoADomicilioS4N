package co.com

import co.com.dron.{Dron, ServicioDron}
import co.com.servicios.{ServicioArchivo, ServicioCorrientazo}
import co.com.sustantivos._
import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration._

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

  /*test("Moviendo el dron de posición con una instrucción") {
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
  }*/

  test("Mandando al dron a hacer una ruta (vuelve a casa a los 10 encargos)") {
    val archivo = List("AAAAIA/AD", "DDAI*AD", "DA", "DAAA", "IIA", "AAI", "DDAA", "DAA", "A", "DDA", "A")
    val ruta = Ruta.newRuta(archivo)
    val dron = Dron(1, sustantivos.Posicion(Coordenada(0, 0), N()), 10)
    assert(Await.result(ServicioDron.realizarRutaAsync(dron, ruta), 10 seconds).lista.last.isRight)
  }

  test("Mandando al dron a hacer una ruta fallida") {
    val archivo = List("AAAAIAAD", "DDAIAD", "DA", "DAAA", "AAAAAAA/A", "AA", "A", "AAA*AA", "AAAAA", "D")
    val ruta = Ruta.newRuta(archivo)
    val dron = Dron(1, sustantivos.Posicion(Coordenada(0, 0), N()), 10)
    assert(Await.result(ServicioDron.realizarRutaAsync(dron, ruta), 10 seconds).lista.last.isLeft)
  }

  test("Leyendo archivo") {
    val archivo = List("AAAAIAAD", "DDAIAD", "DA", "DAAA", "AAAAAAAA", "AA")
    val nombreArchivo = "in1.txt"
    val despuesLeer = ServicioArchivo.leerArchivo(nombreArchivo)
    assert(despuesLeer == archivo)
  }

  test("Leyendo archivos de un directorio y corrientazo") {
    val directorio = "src/main/scala/co.com/files/in/"
    println(ServicioCorrientazo.corrientizarDrones(directorio))
  }

}
