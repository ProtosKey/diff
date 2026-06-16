package diff.app.presentation.state

import diff.app.domain.model.Point
import diff.app.domain.model.SolveResult
import diff.app.domain.utils.MethodKind

data class ResultState(
    val results: Map<MethodKind, SolveResult> = emptyMap(),
    val exact: List<Point> = emptyList(),
    val isLoading: Boolean = false,
)
