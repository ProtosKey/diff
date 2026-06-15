package diff.app.domain.model

import diff.app.domain.exception.InitException

data class Interval(val start: Double, val end: Double) {
    init {
        if (!start.isFinite()) throw InitException("Левая граница должна быть конечным числом")
        if (!end.isFinite()) throw InitException("Правая граница должна быть конечным числом")
        if (start >= end) throw InitException("Левая граница должна быть меньше правой")
    }

    val length: Double get() = end - start
}
