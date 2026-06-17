package diff.app.view.feature.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import diff.app.domain.utils.MethodKind
import diff.app.presentation.state.MainState
import diff.app.theme.LocalAppDimens

@Composable
fun MethodLegend(
    state: MainState,
    onToggle: (MethodKind) -> Unit,
    modifier: Modifier = Modifier,
) {
    val present = MethodKind.entries.filter { state.solutions.containsKey(it) }
    if (present.isEmpty()) return
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(LocalAppDimens.current.paddingSmall),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        present.forEach { kind ->
            val visible = state.visible[kind] ?: true
            LegendChip(
                kind = kind,
                visible = visible,
                onClick = { onToggle(kind) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}
