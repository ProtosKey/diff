package diff.app.view.feature.result.component

import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.round

private const val PRECISION = 5
private const val FACTOR = 100_000L
private const val MANTISSA_PRECISION = 4
private const val MANTISSA_FACTOR = 10_000L

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

internal fun formatScientific(value: Double): String {
    if (value.isNaN()) return "NaN"
    if (value.isInfinite()) return if (value > 0) "∞" else "-∞"
    if (value == 0.0) return "0"
    val absValue = abs(value)
    val exponent = floor(log10(absValue)).toInt()
    val mantissa = value / 10.0.pow(exponent)
    val scaled = mantissa * MANTISSA_FACTOR
    val rounded = if (scaled >= 0) (scaled + 0.5).toLong() else (scaled - 0.5).toLong()
    val sign = if (rounded < 0) "-" else ""
    val absR = abs(rounded)
    val intPart = absR / MANTISSA_FACTOR
    val fracPart = absR % MANTISSA_FACTOR
    val fracStr = fracPart.toString().padStart(MANTISSA_PRECISION, '0')
    val mantissaStr = "$intPart.$fracStr"
    return "$sign$mantissaStr·10${toSuperscript(exponent)}"
}

private fun toSuperscript(value: Int): String {
    if (value == 0) return "⁰"
    val sb = StringBuilder()
    if (value < 0) sb.append('⁻')
    val abs = abs(value).toString()
    for (ch in abs) sb.append(SUPERSCRIPT_DIGITS[ch - '0'])
    return sb.toString()
}

private val SUPERSCRIPT_DIGITS = charArrayOf(
    '⁰', '¹', '²', '³', '⁴', '⁵', '⁶', '⁷', '⁸', '⁹',
)
