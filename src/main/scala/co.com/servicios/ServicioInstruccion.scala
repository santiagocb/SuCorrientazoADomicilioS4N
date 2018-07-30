package co.com.servicios

sealed trait ServicioInstruccionAlgebra {
  //def moverAdelante(posicionActual: Posicion): Posicion
}

sealed trait InterpretacionServicioInstruccion extends ServicioInstruccionAlgebra {
  /*def mover(posicionActual: Posicion, instruccion: Instruccion): Posicion = {

  }*/
}

object InterpretacionServicioInstruccion extends InterpretacionServicioInstruccion