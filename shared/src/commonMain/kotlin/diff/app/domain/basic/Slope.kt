package diff.app.domain.basic

fun interface Slope {
    fun apply(x: Double, y: Double): Double
}