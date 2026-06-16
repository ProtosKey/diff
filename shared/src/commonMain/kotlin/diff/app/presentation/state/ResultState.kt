package diff.app.presentation.state

import diff.app.domain.model.Point
import diff.app.domain.model.Solution
import diff.app.domain.utils.MethodKind

data class ResultState(
    val solutions: Map<MethodKind, Solution> = emptyMap(),
    val exact: List<Point> = emptyList(),
    val isLoading: Boolean = false,
)
