package diff.app.view.feature.result.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import diff.app.domain.model.Point
import diff.app.domain.model.Solution
import diff.app.domain.utils.MethodKind
import diff.app.theme.LocalAppDimens
import diff.app.theme.methodColor

@Composable
fun MethodSection(kind: MethodKind, solution: Solution, exact: List<Point>) {
    val color = methodColor(kind)
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(LocalAppDimens.current.radiusMedium),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant),
    ) {
        Column(
            modifier = Modifier.padding(LocalAppDimens.current.paddingMedium),
            verticalArrangement = Arrangement.spacedBy(LocalAppDimens.current.paddingSmall),
        ) {
            MethodHeader(color = color, label = kind.label, error = solution.error)
            MethodTable(solution = solution, exact = exact)
        }
    }
}
