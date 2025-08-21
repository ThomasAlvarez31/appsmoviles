fun main() {
    // VARIABLES Y OPERADORES
    val numeroEntero: Int = 10
    val numeroDecimal: Double = 3.5

    val suma = numeroEntero + numeroDecimal
    val resta = numeroEntero - numeroDecimal
    val multiplicacion = numeroEntero * numeroDecimal
    val division = numeroEntero / numeroDecimal

    println("La suma es: $suma")
    println("La resta es: $resta")
    println("La multiplicación es: $multiplicacion")
    println("La división es: $division")

    // NULL SAFETY
    var texto: String? = "Hola Kotlin"
    println("La longitud del texto es: ${texto?.length}")  // Uso de ?.

    texto = null
    println("La longitud del texto ahora es: ${texto?.length}")  // No da error, devuelve null

    /*
     Reflexión:
     En Java, para evitar un NullPointerException, tenemos que hacer algo como:

     if (texto != null) {
         System.out.println(texto.length());
     } else {
         System.out.println("null");
     }

     y en Kotlin simplifica este control usando el operador ?. que hace la verificación automáticamente.
    */
    // CONDICIONAL WHEN

    val dia: Int = 3

    val nombreDia = when (dia) {
        1 -> "Lunes"
        2 -> "Martes"
        3 -> "Miércoles"
        4 -> "Jueves"
        5 -> "Viernes"
        6 -> "Sábado"
        7 -> "Domingo"
        else -> "Número inválido"
    }

    println("El día seleccionado es: $nombreDia")
}

/*
 Preguntas de Reflexión:

 1. Diferencias y similitudes entre Kotlin y Java:
    - Kotlin requiere menos código "boilerplate" que Java.
    - Kotlin tiene Null Safety incorporado, lo que evita muchos errores de NullPointerException.
    - La sintaxis de when en Kotlin es más expresiva y flexible que switch en Java.
    - Ambos lenguajes corren sobre la JVM y se integran bien entre sí.
    - Me gusta mas Kotlin porque es mas facil de entender
 2. Aplicación en proyectos móviles:
    - Null Safety permite que las apps móviles sean más robustas y menos propensas a fallos.
    - La expresión when es útil para manejar múltiples casos, como estados de la interfaz,
      selección de menús o validación de entradas de usuario de forma clara y legible.
*/
