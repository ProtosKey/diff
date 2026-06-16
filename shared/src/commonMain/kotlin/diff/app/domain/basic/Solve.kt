package diff.app.domain.basic

import diff.app.domain.model.Problem
import diff.app.domain.model.SolveResult
import diff.app.domain.utils.MethodKind

interface Solve {
    val kind: MethodKind
    fun solve(problem: Problem): SolveResult
}
