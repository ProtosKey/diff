package diff.app.view.feature.result.component

internal fun format(value: Double): String {
    val rounded = (value * 1_000_000).let {
        if (it >= 0) (it + 0.5).toLong() else (it - 0.5).toLong()
    } / 1_000_000.0
    val asStr = rounded.toString()
    return if (asStr.endsWith(".0")) asStr.dropLast(2) else asStr
}
