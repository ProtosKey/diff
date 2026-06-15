package diff.app.domain.model

import diff.app.domain.utils.MethodKind

data class Solution(
    val method: MethodKind,
    val points: List<Point>,
    val step: Double,
    val error: Double,
)
