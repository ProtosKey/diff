package diff.app.domain.model

import diff.app.domain.utils.MethodKind

sealed class SolveResult {
    abstract val kind: MethodKind

    data class Success(val solution: Solution) : SolveResult() {
        override val kind: MethodKind = solution.method
    }

    data class Failure(
        override val kind: MethodKind,
        val message: String,
    ) : SolveResult()
}
