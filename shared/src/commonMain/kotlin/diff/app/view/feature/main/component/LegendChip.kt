package diff.app.view.feature.main.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import diff.app.domain.utils.MethodKind
import diff.app.theme.LocalAppDimens
import diff.app.theme.methodColor

@Composable
fun LegendChip(
    kind: MethodKind,
    visible: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val color = methodColor(kind)
    val shortLabel = kind.short
    val shape = RoundedCornerShape(LocalAppDimens.current.radiusMedium)
    Surface(
        modifier = modifier.clip(shape).clickable(onClick = onClick),
        shape = shape,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant,
        ),
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = LocalAppDimens.current.paddingSmall,
                vertical = LocalAppDimens.current.paddingTiny,
            ),
            horizontalArrangement = Arrangement.spacedBy(LocalAppDimens.current.paddingTiny),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(LocalAppDimens.current.legendDot)
                    .background(
                        color = if (visible) color else color.copy(alpha = 0.25f),
                        shape = CircleShape,
                    ),
            )
            Text(
                text = shortLabel,
                style = MaterialTheme.typography.labelMedium,
                color = if (visible) MaterialTheme.colorScheme.onSurfaceVariant
                else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
            )
        }
    }
}
