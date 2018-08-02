package co.com.Servicios

import co.com.Sustantivos._

sealed trait ServicioParseAlgebra {
  def reporteToString(reporte: Reporte): List[String]
}

sealed trait InterpretacionServicioParse extends ServicioParseAlgebra {
  private def orientacionToString(orientacion: Orientacion): String = {
    orientacion match {
      case n: N => "Norte"
      case n: S => "Sur"
      case n: O => "Occidente"
      case n: E => "Oriente"
    }
  }

  def reporteToString(reporte: Reporte): List[String] = {
    reporte.lista.map(tryDron =>
      tryDron.fold(e => "Falló el envío",
        dron => s"(${dron.posicionActual.coordenada.x}, ${dron.posicionActual.coordenada.y}) dirección ${orientacionToString(dron.posicionActual.orientacion)}"
      ))
  }
}

object InterpretacionServicioParse extends InterpretacionServicioParse
