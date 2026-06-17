package diff.app.view.feature.result.component

import kotlin.math.abs

private const val PRECISION = 5
private const val FACTOR = 100_000L

internal fun format(value: Double): String {
    if (value.isNaN()) return "NaN"
    if (value.isInfinite()) return if (value > 0) "∞" else "-∞"
    val scaled = value * FACTOR
    val rounded = if (scaled >= 0) (scaled + 0.5).toLong() else (scaled - 0.5).toLong()
    if (rounded == 0L) return "0.00000"
    val sign = if (rounded < 0) "-" else ""
    val absR = abs(rounded)
    val intPart = absR / FACTOR
    val fracPart = absR % FACTOR
    val fracStr = fracPart.toString().padStart(PRECISION, '0')
    return "$sign$intPart.$fracStr"
}
