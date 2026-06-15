package diff.app.domain.model

data class Solution(
    val method: MethodKind,
    val points: List<Point>,
    val step: Double,
    val error: Double,
)
