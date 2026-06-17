package diff.app.view.feature.result.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import diff.app.theme.LocalAppDimens
import kotlin.math.abs

@Composable
fun TableDataRow(
    index: Int,
    x: Double,
    methodY: Double,
    exactY: Double,
    widths: List<Dp>,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(LocalAppDimens.current.paddingTiny),
    ) {
        DataCell(index.toString(), Modifier.width(widths.getOrElse(0) { 0.dp }))
        DataCell(format(x), Modifier.width(widths.getOrElse(1) { 0.dp }))
        DataCell(format(methodY), Modifier.width(widths.getOrElse(2) { 0.dp }))
        DataCell(format(exactY), Modifier.width(widths.getOrElse(3) { 0.dp }))
        DataCell(format(abs(exactY - methodY)), Modifier.width(widths.getOrElse(4) { 0.dp }))
    }
}
