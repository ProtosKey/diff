package diff.app.view.feature.result.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import diff.app.domain.utils.MethodKind
import diff.app.theme.LocalAppDimens

@Composable
fun MethodErrorCard(kind: MethodKind, message: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(LocalAppDimens.current.radiusMedium),
        color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.15f),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.error),
    ) {
        Column(
            modifier = Modifier.padding(LocalAppDimens.current.paddingMedium),
            verticalArrangement = Arrangement.spacedBy(LocalAppDimens.current.paddingTiny),
        ) {
            Text(
                text = kind.label,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}
