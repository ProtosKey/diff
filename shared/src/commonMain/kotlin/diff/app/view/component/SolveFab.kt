package diff.app.view.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import diff.app.theme.LocalAppDimens

@Composable
fun SolveFab(
    onClick: () -> Unit,
    onMeasured: (Dp) -> Unit = {},
) {
    val density = LocalDensity.current
    ExtendedFloatingActionButton(
        modifier = Modifier.onGloballyPositioned { coords ->
            onMeasured(with(density) { coords.size.height.toDp() })
        },
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = RoundedCornerShape(LocalAppDimens.current.radiusMedium),
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp,
        ),
    ) {
        Button(
            icon = Icons.Default.PlayArrow,
            label = "Решить",
            description = "Запустить решение",
        )
    }
}
