package co.com.Servicios

import co.com.Dron.{Dron, InterpretacionServicioDron}
import co.com.Sustantivos.{Coordenada, N, Posicion, Ruta}

sealed trait ServicioCorrientazoAlgebra {
  def corrientizarDron(archivoIn: String): Boolean
}

sealed trait InterpretacionServicioCorrientazo extends ServicioCorrientazoAlgebra {
  def corrientizarDron(archivoIn: String): Boolean = {
    val dronInicio = Dron(1, Posicion(Coordenada(0, 0), N()), 3)
    val reporte = InterpretacionServicioDron.realizarRuta(dronInicio, Ruta.newRuta(InterpretacionServicioArchivo.leerArchivo(archivoIn)))
    InterpretacionServicioArchivo.generarReporte(InterpretacionServicioParse.reporteToString(reporte))
  }
}

object InterpretacionServicioCorrientazo extends InterpretacionServicioCorrientazo
