package co.com

import co.com.dron.{Dron, ServicioDron}
import co.com.servicios.{ServicioArchivo, ServicioCorrientazo}
import co.com.sustantivos._
import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration._

class TestSuite extends FunSuite {

  test("El dron puede hacer una ruta normal (día de trabajo) (vuelve a la empresa después de 10 encargos)") {
    val archivo = List("AAAAIA/AD", "DDAI*AD", "DA", "DAAA", "IIA", "AAI", "DDAA", "DAA", "A", "DDA", "A")
    val ruta = Ruta.newRuta(archivo)
    val dron = Dron(1, sustantivos.Posicion(Coordenada(0, 0), N()), 10)
    assert(Await.result(ServicioDron.realizarRutaAsync(dron, ruta), 10 seconds).lista.last.isRight)
  }

  test("El dron puede hacer una ruta afuera del barrio pero se marca como fallida (Left)") {
    val archivo = List("AAAAIAAD", "DDAIAD", "DA", "DAAA", "AAAAAAA/A", "AA", "A", "AAA*AA", "AAAAA", "D")
    val ruta = Ruta.newRuta(archivo)
    val dron = Dron(1, sustantivos.Posicion(Coordenada(0, 0), N()), 10)
    assert(Await.result(ServicioDron.realizarRutaAsync(dron, ruta), 10 seconds).lista.last.isLeft)
  }

  test("Si el in.txt es un File vacío, no considerarlo -> es como si no existiera (None)") {
    val archivo = "empty.txt"
    assert(ServicioArchivo.leerArchivo(archivo) == None)
  }

  test("El directorio del que se lee no existe (donde están los in)") {
    var nombreArchivo = "noexisteestefile.txt"
    assert(ServicioArchivo.leerArchivo(nombreArchivo) == None)
  }

  test("Cuando el dron hace una entrega por fuera del barrio, las demás entregas se toman como fallidas" +
    "hasta terminar los 10 encargos que se le asignaron, y vuelve a casa para hacer otros 10 encargos") {
    val archivo = List("AAAA", "AAA", "AAA", "A", "AA", "AA", "DAAI", "IAADD", "AAAA", "A", "A")
    val ruta = Ruta.newRuta(archivo)
    val dron = Dron(1, sustantivos.Posicion(Coordenada(0, 0), N()), 10)
    val reporte = Await.result(ServicioDron.realizarRutaAsync(dron, ruta), 10 seconds)
    assert(reporte.lista.count(x => x.isLeft) != 0)
    assert(Right(Dron(1,Posicion(Coordenada(0,1),N()),9)) == reporte.lista.last)
  }

  test("Leyendo archivos de un directorio y haciendo un corrientazo (un día con todos los drones) y" +
    "creando reportes escritos (out.txt)") {
    val directorio = "src/main/scala/co.com/files/in/"
    assert(!ServicioCorrientazo.corrientizarDrones(directorio).isEmpty)
  }
}
