package diff.app.domain.model

import diff.app.domain.exception.InitException

class Point(val x: Double, val y: Double) {
    init {
        if (!x.isFinite() || !y.isFinite()) throw InitException("Точка должна быть конечным числом")
    }
}
