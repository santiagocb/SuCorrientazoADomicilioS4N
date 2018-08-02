package co.com.Servicios

import co.com.Dron.{Dron, InterpretacionServicioDron}
import co.com.Sustantivos.{Coordenada, N, Posicion, Ruta}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

sealed trait ServicioCorrientazoAlgebra {
  def corrientizarDrones(dir: String):  Option[IndexedSeq[Future[Boolean]]]
}

sealed trait InterpretacionServicioCorrientazo extends ServicioCorrientazoAlgebra {
  def corrientizarDrones(dir: String): Option[IndexedSeq[Future[Boolean]]] = {
    InterpretacionServicioArchivo.leerDirectorio(dir).map(dir => {
      Range(1, dir.size + 1).zip(dir)
        .map(tupla => InterpretacionServicioDron
          .realizarRuta(Dron(tupla._1, Posicion(Coordenada(0, 0), N()), 10), Ruta.newRuta(InterpretacionServicioArchivo.leerArchivo(tupla._2)))
          .map(reporte => InterpretacionServicioArchivo.generarReporte(InterpretacionServicioParse.reporteToString(reporte), tupla._1)))
    })

  }
}

object InterpretacionServicioCorrientazo extends InterpretacionServicioCorrientazo
