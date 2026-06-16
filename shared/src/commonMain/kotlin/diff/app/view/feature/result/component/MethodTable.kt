package diff.app.view.feature.result.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import diff.app.domain.model.Point
import diff.app.domain.model.Problem
import diff.app.theme.LocalAppDimens

private val TABLE_MAX_HEIGHT = 280.dp

@Composable
fun MethodTable(points: List<Point>, problem: Problem) {
    Column(
        verticalArrangement = Arrangement.spacedBy(LocalAppDimens.current.paddingTiny),
    ) {
        TableHeaderRow()
        LazyColumn(
            modifier = Modifier.heightIn(max = TABLE_MAX_HEIGHT),
            verticalArrangement = Arrangement.spacedBy(LocalAppDimens.current.paddingTiny),
        ) {
            items(count = points.size, key = { it }) { i ->
                val methodPoint = points[i]
                val exactY = problem.equation.real.apply(methodPoint.x, problem.point)
                TableDataRow(
                    index = i,
                    x = methodPoint.x,
                    methodY = methodPoint.y,
                    exactY = exactY,
                )
            }
        }
    }
}
