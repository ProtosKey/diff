package diff.app.presentation.state

import diff.app.domain.utils.MethodKind
import diff.app.presentation.model.PointData

data class MainState(
    val solutions: Map<MethodKind, List<PointData>> = emptyMap(),
    val exact: List<PointData> = emptyList(),
    val visible: Map<MethodKind, Boolean> = emptyMap(),
    val isLoading: Boolean = false,
)
