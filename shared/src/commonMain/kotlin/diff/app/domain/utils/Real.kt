package diff.app.domain.utils

import diff.app.domain.model.Point

fun interface Real {
    fun apply(x: Double, point: Point): Double
}
