package diff.app.presentation.state

import diff.app.domain.model.Point
import diff.app.domain.utils.MethodKind

data class MainState(
    val solutions: Map<MethodKind, List<Point>> = emptyMap(),
    val exact: List<Point> = emptyList(),
    val visible: Map<MethodKind, Boolean> = emptyMap(),
    val isLoading: Boolean = false,
)
