package co.com.Servicios

import scala.io.Source

sealed trait ServicioArchivoAlgebra {
  def leerArchivo(rutaArchivo: String): List[String]
}

sealed trait InterpretacionServicioArchivo extends ServicioArchivoAlgebra {
  def leerArchivo(nombreArchivo: String): List[String] = {
    Source.fromFile(s"/home/Documents/SuCorrientazoADomicilio${nombreArchivo}").getLines.toList
  }
}

object InterpretacionServicioArchivo extends InterpretacionServicioArchivo