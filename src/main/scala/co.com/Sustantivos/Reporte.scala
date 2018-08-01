package co.com.Sustantivos

import co.com.Dron.Dron

case class Reporte (lista: List[Either[String, Dron]])

object Reporte {
  def newReporte(lista: List[Either[String, Dron]]): Unit = {
  }
}
//validar que se manda a imprimir en cada caso
