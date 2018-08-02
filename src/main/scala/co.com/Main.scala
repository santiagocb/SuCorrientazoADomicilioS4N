package co.com

import co.com.Servicios.InterpretacionServicioCorrientazo

object SuCorrientazoADomicilio {
  def main(args: Array[String]): Unit = {
    InterpretacionServicioCorrientazo.corrientizarDron("in.txt")
  }
}
