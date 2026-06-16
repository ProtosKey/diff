package diff.app.view.feature.result.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import diff.app.theme.LocalAppDimens
import kotlin.math.abs

@Composable
fun TableDataRow(index: Int, x: Double, methodY: Double, exactY: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(LocalAppDimens.current.paddingTiny),
    ) {
        DataCell(index.toString(), weight = 0.5f)
        DataCell(format(x), weight = 1f)
        DataCell(format(methodY), weight = 1.4f)
        DataCell(format(exactY), weight = 1.4f)
        DataCell(format(abs(exactY - methodY)), weight = 1.2f)
    }
}
