package diff.app.domain.utils

fun interface Slope {
    fun apply(x: Double, y: Double): Double
}