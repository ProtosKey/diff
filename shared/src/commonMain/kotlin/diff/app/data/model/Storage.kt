package diff.app.data.model

import diff.app.domain.model.Point
import diff.app.domain.model.Problem
import diff.app.domain.model.Solution

data class Storage(
    val problem: Problem,
    val solutions: List<Solution>,
    val exact: List<Point>,
)
