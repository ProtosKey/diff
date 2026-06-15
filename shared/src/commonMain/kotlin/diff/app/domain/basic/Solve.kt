package diff.app.domain.basic

import diff.app.domain.utils.MethodKind
import diff.app.domain.model.Problem
import diff.app.domain.model.Solution

interface Solve {
    val kind: MethodKind
    fun solve(problem: Problem): Solution
}
