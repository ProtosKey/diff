package diff.app.domain.model

import diff.app.domain.exception.InitException
import kotlin.jvm.JvmInline

@JvmInline
value class Step(val value: Double) {
    init {
        if (!value.isFinite()) throw InitException("Шаг должен быть конечным числом")
        if (value <= 0) throw InitException("Шаг должен быть положительным числом")
    }
}
