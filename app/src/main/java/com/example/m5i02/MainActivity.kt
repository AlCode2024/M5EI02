package com.example.m5ei02

import java.text.DecimalFormat

fun formatNumber(number: Int): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(number)
}

fun calcularPrecioEntrada(edad: Int, diaSemana: String): Pair<Int, String> {
    // Verificar si la edad es válida
    if (edad < 0 || edad > 100) {
        return Pair(0, "Error: Edad no válida")
    }

    // Niños menores de 4 años no pagan
    if (edad < 4) {
        return Pair(0, "Entrada gratuita")
    }

    // Niños de 4 a 15 años
    if (edad in 4..15) {
        return Pair(15_000, "Sin descuento")
    }

    // Adultos entre 16 y 60 años
    if (edad in 16..60) {
        var precio = 30_000
        val descuento = if (diaSemana.lowercase() == "lunes" || diaSemana.lowercase() == "martes") {
            precio -= 5_000
            "Descuento de ${formatNumber(5_000)} aplicado"
        } else {
            "Sin descuento"
        }
        return Pair(precio, descuento)
    }

    // Adultos mayores de 60 años
    if (edad > 60) {
        return Pair(20_000, "Sin descuento")
    }

    return Pair(0, "Error inesperado")
}

fun main() {
    var continuar = true
    while (continuar) {
        println("¡Bienvenido al sistema de compra de entradas del Parque de Diversiones!")
        println("Por favor, sigue las instrucciones para obtener el precio de tus entradas.")

        // Para hacer mas facil el funcionamiento limito el numero de entradas a 5, o puede que tenga un ciclo gigante.
        var cantidadEntradas: Int? = null
        while (cantidadEntradas == null || cantidadEntradas !in 1..5) {
            println("¿Cuántas entradas desea comprar? (Máximo 5 entradas):")
            println("1. Ingresar el número de entradas")
            println("2. Cancelar compra y salir")
            val opcion = readLine()

            when (opcion) {
                "1" -> {
                    println("Ingrese la cantidad de entradas:")
                    val inputCantidad = readLine()?.toIntOrNull()
                    if (inputCantidad == null || inputCantidad !in 1..5) {
                        println("Por favor, ingrese un número válido entre 1 y 5.")
                    } else {
                        cantidadEntradas = inputCantidad
                    }
                }
                "2" -> {
                    println("Compra cancelada. ¡Gracias por visitar nuestro sistema!")
                    return // Salir del programa
                }
                else -> {
                    println("Opción no válida. Por favor, ingrese 1 o 2.")
                }
            }
        }

        println("\nNota: Los adultos entre 16 y 60 años tienen un descuento de ${formatNumber(5_000)} los lunes y martes.")
        println("Este descuento se aplica automáticamente al calcular el precio.\n")

        // Variable para almacenar el total a pagar y los detalles de cada entrada
        var totalAPagar = 0
        val detallesEntradas = mutableListOf<String>()

        // Bucle para procesar la cantidad de entradas deseada
        for (i in 1..cantidadEntradas) {
            println("Procesando la entrada $i de $cantidadEntradas...")

            // Bucle para asegurarnos de que se ingresa una edad válida
            var edad: Int? = null
            while (edad == null) {
                println("Ingrese la edad para la entrada $i (entre 0 y 100 años):")
                val inputEdad = readLine()?.toIntOrNull()
                if (inputEdad == null || inputEdad < 0 || inputEdad > 100) {
                    println("Error: Edad no válida. Por favor, ingrese un número entre 0 y 100.")
                } else {
                    edad = inputEdad
                }
            }

            // Bucle para seleccionar el día de la semana
            var diaSemana: String? = null
            while (diaSemana == null) {
                println("Seleccione el día de la semana para la entrada $i:")
                println("1. Lunes")
                println("2. Martes")
                println("3. Miércoles")
                println("4. Jueves")
                println("5. Viernes")
                println("6. Sábado")
                println("7. Domingo")

                when (readLine()) {
                    "1" -> diaSemana = "lunes"
                    "2" -> diaSemana = "martes"
                    "3" -> diaSemana = "miércoles"
                    "4" -> diaSemana = "jueves"
                    "5" -> diaSemana = "viernes"
                    "6" -> diaSemana = "sábado"
                    "7" -> diaSemana = "domingo"
                    else -> println("Opción no válida. Por favor, ingrese un número entre 1 y 7.")
                }
            }

            // Calculamos el precio de la entrada y acumular en el total a pagar
            val (precioEntrada, descuento) = calcularPrecioEntrada(edad, diaSemana)
            totalAPagar += precioEntrada

            // Detalles de la entrada a la lista con el número de entrada
            detallesEntradas.add("Entrada $i: Edad: $edad, Día: $diaSemana, Precio: ${formatNumber(precioEntrada)}, Descuento: $descuento")
        }

        // Detalle de cada entrada y el total a pagar
        println("\n--- Detalles de las entradas ---")
        for (detalle in detallesEntradas) {
            println(detalle)
        }
        println("\nEl total a pagar por $cantidadEntradas entradas es: ${formatNumber(totalAPagar)}.")

        // Qué desea hacer a continuación
        var opcionFinal: String? = null
        while (opcionFinal == null) {
            println("\n¿Qué desea hacer ahora?")
            println("1. Pagar")
            println("2. Cancelar la compra")
            println("3. Volver a empezar")
            opcionFinal = readLine()

            when (opcionFinal) {
                "1" -> {
                    println("¡Gracias por su compra! ¡Le deseamos una excelente visita al parque!")
                    continuar = false // Terminar el programa
                }
                "2" -> {
                    println("Compra cancelada. ¡Que tenga un buen día! Estaremos atentos cuando lo requiera.")
                    continuar = false // Terminar el programa
                }
                "3" -> {
                    println("Reiniciando el sistema...\n")
                    opcionFinal = null // Solo reiniciar la opción final
                }
                else -> {
                    println("Opción no válida. Por favor, elija una opción válida.")
                    opcionFinal = null // Volver a pedir la opción final
                }
            }
        }
    }
}
