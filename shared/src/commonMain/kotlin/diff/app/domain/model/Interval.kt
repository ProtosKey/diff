package diff.app.domain.model

import diff.app.domain.exception.InitException

data class Interval(val start: Double, val end: Double) {
    init {
        if (!start.isFinite()) throw InitException("Слишком большое значение левой границы")
        if (!end.isFinite()) throw InitException("Слишком большое значение правой границы")
        if (start >= end) throw InitException("Левая граница должна быть меньше правой")
    }

    val length: Double get() = end - start
}
