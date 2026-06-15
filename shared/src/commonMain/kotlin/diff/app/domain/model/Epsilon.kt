package diff.app.domain.model

import diff.app.domain.exception.InitException
import kotlin.jvm.JvmInline

@JvmInline
value class Epsilon(val value: Double) {
    init {
        if (!value.isFinite()) throw InitException("Точность должна быть конечным числом")
        if (value <= 0) throw InitException("Точность должна быть положительным числом")
    }
}
