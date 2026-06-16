package diff.app.view.feature.result.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import diff.app.domain.model.Point
import diff.app.domain.model.Solution
import diff.app.theme.LocalAppDimens

private val TABLE_MAX_HEIGHT = 280.dp

@Composable
fun MethodTable(solution: Solution, exact: List<Point>) {
    val count = minOf(solution.points.size, exact.size)
    Column(
        verticalArrangement = Arrangement.spacedBy(LocalAppDimens.current.paddingTiny),
    ) {
        TableHeaderRow()
        LazyColumn(
            modifier = Modifier.heightIn(max = TABLE_MAX_HEIGHT),
            verticalArrangement = Arrangement.spacedBy(LocalAppDimens.current.paddingTiny),
        ) {
            items(count = count, key = { it }) { i ->
                val methodPoint = solution.points[i]
                val exactPoint = exact[i]
                TableDataRow(
                    index = i,
                    x = methodPoint.x,
                    methodY = methodPoint.y,
                    exactY = exactPoint.y,
                )
            }
        }
    }
}
