# SuCorrientazoADomicilioS4N
El código correspondiente al presente repositorio hace referencia a la solución dada al problema que tiene la empresa SuCorrientazoADomicilio para realizar entregas de pedidos.

-> Input
Cada día la empresa debe entregar 20 archivos de textos que servirán de entrada para cada uno de los drones que realizarán los domicilios. Dichos archivos deben ir en la carpeta "Files/in" del repositorio (se exponen como ejemplo algunas rutas)

-> Output
Se mostrará entonces el reporte de cada dron al finalizar todas las entregas que se le asignaron en archivos de text (.txt). Dichos archivos se encontrarán en la carperta "Files/out".
Para obtener un reporte nuevo de cada día, la empresa debe borrar todos los output del día anterior. A la hora de mandar los drones con sus encargos, dicho archivos se vuelven a generar.

-> A tomar en cuenta
Los caracteres distintos a instrucciones que lee el Dron son ignorados por el programa. Por ejemplo, si la instrucción ingresada para ser leída por el Dron es: "AI/DAAAIA&", el Dron seguirá realizará la entrega según la instrucción "AIDAAAIA".

Si se dejan archivos de entrada (in.txt) vacíos, se ignoran, y se consideran inexistentes dentro del programa.

El dron en su trayecto puede salir del margen, sin embargo, que entregue un pedido por fuera del margen se toma como perdida de los demás encargos.

IMPORTANTE: El dron puede llevar 10 encargos. Si en cualquier momento el dron entrega un envío fuera del margen del barrio, los demás encargos que llevaba se consideran como perdidos. Por tanto, el dron volverá al restaurante para cargar otros 10.
