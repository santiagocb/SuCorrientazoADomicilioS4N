package co.com

import co.com.Servicios.InterpretacionServicioCorrientazo

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object SuCorrientazoADomicilio {
  def main(args: Array[String]): Unit = {
    val listaArchivos = List("in1.txt", "in2.txt", "in3.txt", "in4.txt", "in5.txt")
    val res = Await.result(Future.sequence(InterpretacionServicioCorrientazo.corrientizarDron(listaArchivos)), 10 seconds)
    println(res)
  }
}
