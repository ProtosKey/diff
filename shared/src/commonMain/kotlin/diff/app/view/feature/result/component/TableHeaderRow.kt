package diff.app.view.feature.result.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import diff.app.theme.LocalAppDimens

@Composable
fun TableHeaderRow(widths: List<Dp>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(LocalAppDimens.current.paddingTiny),
    ) {
        TABLE_HEADERS.forEachIndexed { index, header ->
            HeaderCell(header, Modifier.width(widths.getOrElse(index) { 0.dp }))
        }
    }
}
