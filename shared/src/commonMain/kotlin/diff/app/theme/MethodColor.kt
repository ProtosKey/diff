package diff.app.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import diff.app.domain.utils.MethodKind

private val EulerColor = Color(0xFF1E88E5)
private val RungeColor = Color(0xFF43A047)
private val MilneColor = Color(0xFFFB8C00)

fun methodColor(kind: MethodKind): Color = when (kind) {
    MethodKind.EULER -> EulerColor
    MethodKind.RUNGE -> RungeColor
    MethodKind.MILNE -> MilneColor
}

@Composable
@ReadOnlyComposable
fun realColor(): Color = MaterialTheme.colorScheme.error
