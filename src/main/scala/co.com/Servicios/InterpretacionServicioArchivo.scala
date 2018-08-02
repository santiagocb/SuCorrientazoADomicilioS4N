package co.com.Servicios

import java.io.{File, PrintWriter}

import scala.io.Source


sealed trait ServicioArchivoAlgebra {
  def leerArchivo(rutaArchivo: String): List[String]
  def generarReporte(ruta: List[String]): Boolean
}

sealed trait InterpretacionServicioArchivo extends ServicioArchivoAlgebra {
  def leerArchivo(nombreArchivo: String): List[String] = {
    Source.fromFile(s"src/main/scala/co.com/Files/${nombreArchivo}").getLines.toList
  }

  def generarReporte(ruta: List[String]): Boolean = {
    val writer = new PrintWriter(new File("src/main/scala/co.com/Files/out.txt"))
    writer.write("== Reporte de entregas ==\n")
    ruta.foreach(str => writer.write(s"${str}\n"))
    writer.close()
    true
  }
}

object InterpretacionServicioArchivo extends InterpretacionServicioArchivo