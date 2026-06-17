package diff.app.domain.model

import diff.app.domain.exception.InitException
import kotlin.jvm.JvmInline

@JvmInline
value class Step(val value: Double) {
    init {
        if (!value.isFinite()) throw InitException("Слишком большое значение шага")
        if (value <= 0) throw InitException("Шаг должен быть положительным числом")
    }
}
