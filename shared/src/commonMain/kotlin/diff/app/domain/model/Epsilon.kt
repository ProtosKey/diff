package diff.app.domain.model

import diff.app.domain.exception.InitException
import kotlin.jvm.JvmInline

@JvmInline
value class Epsilon(val value: Double) {
    init {
        if (!value.isFinite()) throw InitException("Слишком большое значение точности")
        if (value <= 0) throw InitException("Точность должна быть положительным числом")
    }
}
