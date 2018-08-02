package co.com.Servicios

import java.io.{File, PrintWriter}

import scala.io.Source


sealed trait ServicioArchivoAlgebra {
  def leerDirectorio(nombreDir: String): Option[List[String]]
  def leerArchivo(rutaArchivo: String): List[String]
  def generarReporte(ruta: List[String], id: Int): Boolean
}

sealed trait InterpretacionServicioArchivo extends ServicioArchivoAlgebra {
  def leerDirectorio(nombreDir: String): Option[List[String]] = {
    val directory = new File(nombreDir)
    if(directory.exists() && directory.isDirectory) {
      Some(directory.listFiles(_.isFile).toList.map(_.getName)
        .filter(str => str.contains("in"))
        .sortWith((x, y) => x < y).sortWith((x, y) => x.size < y.size))
    }
    else None
  }

  def leerArchivo(nombreArchivo: String): List[String] = {
    Source.fromFile(s"src/main/scala/co.com/Files/${nombreArchivo}").getLines.toList
  }

  def generarReporte(ruta: List[String], id: Int): Boolean = {
    val writer = new PrintWriter(new File(s"src/main/scala/co.com/Files/out${id}.txt"))
    writer.write("== Reporte de entregas ==\n")
    ruta.foreach(str => writer.write(s"${str}\n"))
    writer.close()
    true
  }
}

object InterpretacionServicioArchivo extends InterpretacionServicioArchivo