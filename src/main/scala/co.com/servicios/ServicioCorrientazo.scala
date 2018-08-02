package co.com.servicios

import co.com.dron.{Dron, ServicioDron}
import co.com.sustantivos.{Coordenada, N, Posicion, Ruta}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

sealed trait ServicioCorrientazoAlgebra {
  def corrientizarDrones(dir: String):  Option[IndexedSeq[Future[Boolean]]]
}

sealed trait ServicioCorrientazo extends ServicioCorrientazoAlgebra {
  def corrientizarDrones(dir: String): Option[IndexedSeq[Future[Boolean]]] = {
    ServicioArchivo.leerDirectorio(dir).map(dir => {
      Range(1, dir.size + 1).zip(dir)
        .map(tupla => ServicioDron
          .realizarRutaAsync(Dron(tupla._1, Posicion(Coordenada(0, 0), N()), 10), Ruta
            .newRuta(ServicioArchivo.leerArchivo(tupla._2)))
          .map(reporte => ServicioArchivo.generarReporte(ServicioParse.reporteToString(reporte), tupla._1)))
    })
  }
}

object ServicioCorrientazo extends ServicioCorrientazo
