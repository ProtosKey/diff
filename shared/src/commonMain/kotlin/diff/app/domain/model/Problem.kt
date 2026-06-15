package diff.app.domain.model

import diff.app.domain.exception.InitException

data class Problem(
    val equation: Equation,
    val interval: Interval,
    val point: Point,
    val step: Step,
    val epsilon: Epsilon,
) {
    init {
        if (point.x != interval.start) throw InitException("Начальное условие должно совпадать с левой границей интервала")
        if (step.value > interval.length) throw InitException("Шаг не должен превышать длину интервала")
        if (interval.length / step.value < MIN_NODES_FOR_MULTISTEP - 1) throw InitException(
            "Для многошаговых методов нужно не меньше $MIN_NODES_FOR_MULTISTEP узлов сетки"
        )
    }

    val count: Int get() = (interval.length / step.value).toInt() + 1

    private companion object {
        const val MIN_NODES_FOR_MULTISTEP = 4
    }
}
