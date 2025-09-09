// Main.kt
import kotlinx.coroutines.*
import kotlin.system.exitProcess

//Clases y subclases
open class Pescado(
    val nombre: String,
    val precioPorKilo: Double,
    var stockKilos: Double
) {
    init {
        require(precioPorKilo > 0) { "El precio debe ser mayor que 0." }
        require(stockKilos >= 0) { "El stock no puede ser negativo." }
    }

    open fun descripcion(): String {
        return "Pescado genérico: $nombre"
    }
}
class Salmon(nombre: String, precioPorKilo: Double, stockKilos: Double) :
    Pescado(nombre, precioPorKilo, stockKilos) {
    override fun descripcion(): String {
        return "Salmon fresco: $nombre"
    }
}
class PescadoBlanco(nombre: String, precioPorKilo: Double, stockKilos: Double) :
    Pescado(nombre, precioPorKilo, stockKilos) {
    override fun descripcion(): String {
        return "Pescado blanco delicioso: $nombre"
    }
}
//Control de calidad
sealed class ResultadoCalidad {
    data class Exitoso(val mensaje: String) : ResultadoCalidad()
    data class Error(val motivo: String) : ResultadoCalidad()
}
// 3. Función de control de calidad (corrutina)
suspend fun controlarCalidad(pescado: Pescado): ResultadoCalidad {
    println("⏳ Revisando calidad de ${pescado.nombre}...")
    delay(2000L) // simula proceso lento
    return if (pescado.stockKilos > 0) {
        ResultadoCalidad.Exitoso("✅ ${pescado.nombre} pasó el control de calidad.")
    } else {
        ResultadoCalidad.Error("❌ ${pescado.nombre} no tiene stock disponible.")
    }
}
// 4. Menú principal
val inventario = mutableListOf<Pescado>()

fun main() = runBlocking {
    while (true) {
        println(
            """
            -----------------------------
            🐟 Sistema - Salmones del Sur SPA
            -----------------------------
            1. Registrar pescado
            2. Mostrar pescados
            3. Filtrar pescados caros (> $5000)
            4. Calcular valor total del stock
            5. Control de calidad
            6. Salir
            -----------------------------
            Selecciona una opción:
        """.trimIndent()
        )

        when (readLine()?.toIntOrNull()) {
            1 -> registrarPescado()
            2 -> mostrarPescados()
            3 -> filtrarCaros()
            4 -> calcularValorTotal()
            5 -> ejecutarControlCalidad()
            6 -> {
                println("👋 Saliendo del sistema...")
                exitProcess(0)
            }
            else -> println("⚠️ Opción no válida.")
        }
    }
}

// -----------------------------
// Funciones de menú
// -----------------------------
fun registrarPescado() {
    try {
        println("👉 Ingresa el nombre:")
        val nombre = readLine()!!

        println("👉 Ingresa el precio por kilo:")
        val precio = readLine()!!.toDouble()

        println("👉 Ingresa el stock en kilos:")
        val stock = readLine()!!.toDouble()

        println("👉 Tipo de pescado (1=Salmon, 2=Pescado Blanco, 0=Genérico):")
        val tipo = readLine()?.toIntOrNull()

        val pescado = when (tipo) {
            1 -> Salmon(nombre, precio, stock)
            2 -> PescadoBlanco(nombre, precio, stock)
            else -> Pescado(nombre, precio, stock)
        }

        inventario.add(pescado)
        println("✅ Pescado registrado con éxito.")

    } catch (e: Exception) {
        println("⚠️ Error al registrar: ${e.message}")
    }
}

fun mostrarPescados() {
    if (inventario.isEmpty()) {
        println("📭 No hay pescados registrados.")
    } else {
        inventario.forEach {
            println("🐟 ${it.descripcion()} - Precio: $${it.precioPorKilo} | Stock: ${it.stockKilos} kg")
        }
    }
}

fun filtrarCaros() {
    val caros = inventario.filter { it.precioPorKilo > 5000 }
    if (caros.isEmpty()) {
        println("📉 No hay pescados con precio mayor a $5000.")
    } else {
        caros.forEach {
            println("💰 ${it.nombre} cuesta $${it.precioPorKilo} por kilo.")
        }
    }
}

fun calcularValorTotal() {
    val total = inventario.sumOf { it.precioPorKilo * it.stockKilos }
    println("📊 Valor total del stock: $$total")
}

suspend fun ejecutarControlCalidad() {
    if (inventario.isEmpty()) {
        println("📭 No hay pescados registrados para controlar.")
        return
    }
    for (pescado in inventario) {
        val resultado = controlarCalidad(pescado)
        when (resultado) {
            is ResultadoCalidad.Exitoso -> println(resultado.mensaje)
            is ResultadoCalidad.Error -> println(resultado.motivo)
        }
    }
}
