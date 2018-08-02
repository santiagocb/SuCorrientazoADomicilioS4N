package co.com.Sustantivos

import co.com.Dron.Dron

case class Reporte (lista: List[Either[Dron, Dron]])

object Reporte {
  def newReporte(lista: List[Either[Dron, Dron]]): Unit = {
  }
}
//validar que se manda a imprimir en cada caso
