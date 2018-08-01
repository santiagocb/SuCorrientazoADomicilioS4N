package co.com.Servicios

import java.io.{File, PrintWriter}

import scala.io.Source


sealed trait ServicioArchivoAlgebra {
  def leerArchivo(rutaArchivo: String): List[String]
  def generarReporte(ruta: List[String]): Unit
}

sealed trait InterpretacionServicioArchivo extends ServicioArchivoAlgebra {
  def leerArchivo(nombreArchivo: String): List[String] = {
    Source.fromFile(s"src/main/scala/co.com/Files/${nombreArchivo}").getLines.toList
  }

  def generarReporte(ruta: List[String]): Unit = {
    val writer = new PrintWriter(new File("src/main/scala/co.com/Files/out.txt"))
    ruta.foreach(str => writer.write(s"${str}\n"))
    writer.close()
  }
}

object InterpretacionServicioArchivo extends InterpretacionServicioArchivo