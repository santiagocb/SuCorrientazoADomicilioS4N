package co.com.Servicios

import co.com.Dron.{Dron, InterpretacionServicioDron}
import co.com.Sustantivos.{Coordenada, N, Posicion, Ruta}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

sealed trait ServicioCorrientazoAlgebra {
  def corrientizarDron(archivoIn: List[String]): IndexedSeq[Future[Boolean]]
}

sealed trait InterpretacionServicioCorrientazo extends ServicioCorrientazoAlgebra {
  def corrientizarDron(archivosIn: List[String]): IndexedSeq[Future[Boolean]] = {
      Range(1, archivosIn.size + 1).zip(archivosIn)
        .map(tupla => InterpretacionServicioDron
        .realizarRuta(Dron(tupla._1, Posicion(Coordenada(0, 0), N()), 3), Ruta.newRuta(InterpretacionServicioArchivo.leerArchivo(tupla._2)))
        .map(reporte => InterpretacionServicioArchivo.generarReporte(InterpretacionServicioParse.reporteToString(reporte), tupla._1)))
  }
}

object InterpretacionServicioCorrientazo extends InterpretacionServicioCorrientazo
