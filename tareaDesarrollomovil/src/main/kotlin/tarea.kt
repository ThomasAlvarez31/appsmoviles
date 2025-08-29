// Clase abstracta
abstract class TransporteCarga(
    val patente: String,
    val peso: Double,
    val capacidad: Double
) {
    abstract fun calcularCostoEnvio(): Double

    open fun mostrarInfo() {
        println("Patente: $patente | Peso: $peso | Capacidad: $capacidad | Costo: ${calcularCostoEnvio()}")
    }
}

// Subclases
class Camion(patente: String, peso: Double, capacidad: Double) :
    TransporteCarga(patente, peso, capacidad) {
    override fun calcularCostoEnvio(): Double {
        return peso * 2.5   // fórmula simple
    }
}

class Camioneta(patente: String, peso: Double, capacidad: Double) :
    TransporteCarga(patente, peso, capacidad) {
    override fun calcularCostoEnvio(): Double {
        return peso * 1.5 + 100
    }
}

class Drone(patente: String, peso: Double, capacidad: Double) :
    TransporteCarga(patente, peso, capacidad) {
    override fun calcularCostoEnvio(): Double {
        return peso * 5.0   // mucho más caro por ser pequeño
    }
}

// Función para validar
fun validarDatos(patente: String, peso: Double, capacidad: Double): Result<Unit> {
    if (patente.isBlank()) {
        return Result.failure(Exception("La patente no puede estar vacía."))
    }
    if (peso > capacidad) {
        return Result.failure(Exception("El peso excede la capacidad del vehículo."))
    }
    if (peso <= 0 || capacidad <= 0) {
        return Result.failure(Exception("Peso y capacidad deben ser mayores que 0."))
    }
    return Result.success(Unit)
}

fun main() {
    val vehiculos = mutableListOf<TransporteCarga>()

    for (i in 1..2) {
        try {
            println("\n=== Registro de vehículo #$i ===")
            print("Ingrese patente: ")
            val patente = readLine() ?: ""

            print("Ingrese peso de carga: ")
            val peso = readLine()!!.toDouble()

            print("Ingrese capacidad máxima: ")
            val capacidad = readLine()!!.toDouble()

            println("Seleccione tipo de transporte: (1=Camion, 2=Camioneta, 3=Drone)")
            val tipo = readLine()!!.toInt()

            // Validamos antes de crear
            val validacion = validarDatos(patente, peso, capacidad)
            if (validacion.isFailure) {
                println("Error: ${validacion.exceptionOrNull()?.message}")
                continue
            }

            val vehiculo: TransporteCarga = when (tipo) {
                1 -> Camion(patente, peso, capacidad)
                2 -> Camioneta(patente, peso, capacidad)
                3 -> Drone(patente, peso, capacidad)
                else -> throw Exception("Tipo no válido")
            }

            vehiculos.add(vehiculo)
            println("Vehículo registrado con éxito.")

        } catch (e: Exception) {
            println("Error en el registro: ${e.message}")
        }
    }

    println("\n=== Vehículos registrados ===")
    vehiculos.forEach { it.mostrarInfo() }
}
