package diff.app.data.model

import diff.app.domain.model.Point
import diff.app.domain.model.Problem
import diff.app.domain.model.SolveResult

data class Storage(
    val problem: Problem,
    val results: List<SolveResult>,
    val exact: List<Point>,
)
