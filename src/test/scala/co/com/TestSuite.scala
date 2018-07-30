package co.com

import co.com.entidades._
import co.com.servicios.InterpretacionServicioDron
import org.scalatest.FunSuite

import scala.util.{Success, Try}

class TestSuite extends FunSuite{
  test("Creating a coord") {
    val res = Try(Coordenada.newCoordenada(5, 5))
    assert(res == Success(Coordenada(5,5)))
  }

  test("Creating a instruction") {
    val res = Try(entidades.Instruccion.newInstruccion('A'))
    assert(res == Success(A()))
  }
  test("Coordenada indebida") {
    val res1 = Try(Coordenada.newCoordenada(11, 2))
    assert(res1.isFailure)
  }

  test("Creación posición") {
    val pos = Try(Posicion.newPosicion(Coordenada.newCoordenada(9, 2), N()))
    assert(pos.isSuccess)
  }

  test("Mover hacia arriba") {
    val str = "AAAAIAAD"
    val list = List(Posicion.newPosicion(Coordenada.newCoordenada(0, 0), N()))
    println(list)
    val o = str.foldLeft(list)((acu, char) => {
      acu :+ InterpretacionServicioDron.realizarMovimiento(acu.last, Instruccion.newInstruccion(char))
    })

    val l = list.foldLeft(list)((acu, pos) => {
      acu :+ InterpretacionServicioDron.adelante(pos)
    })
    println(o)

    str.toList.map(char => Instruccion.newInstruccion(char))


    /*val init = Try(Posicion.newPosicion(Coordenada.newCoordenada(0, 10), N()))
    println(init)
    val res = init.map(pos => InterpretacionServicioInstruccion.moverAdelante(pos))
    println(res)*/
  }
}
