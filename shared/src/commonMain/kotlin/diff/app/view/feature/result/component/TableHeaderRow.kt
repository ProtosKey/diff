package diff.app.view.feature.result.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import diff.app.theme.LocalAppDimens

@Composable
fun TableHeaderRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(LocalAppDimens.current.paddingTiny),
    ) {
        HeaderCell("i", weight = 0.5f)
        HeaderCell("x", weight = 1f)
        HeaderCell("y", weight = 1.4f)
        HeaderCell("Точное", weight = 1.4f)
        HeaderCell("|Δ|", weight = 1.2f)
    }
}
