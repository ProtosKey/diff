package diff.app.view.feature.result.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import diff.app.domain.model.Point
import diff.app.domain.model.Problem
import diff.app.theme.LocalAppDimens
import kotlin.math.abs

private val TABLE_MAX_HEIGHT = 280.dp
private const val WIDEST_SAMPLE_LIMIT = 1024
internal val TABLE_HEADERS = listOf("i", "x", "y", "Точное", "|Δ|")

private fun scanWidest(
    widest: Array<String>,
    index: Int,
    point: Point,
    problem: Problem,
) {
    if (!point.x.isFinite() || !point.y.isFinite()) return
    val exactY = problem.equation.real.apply(point.x, problem.point)
    if (!exactY.isFinite()) return
    val parts = arrayOf(
        index.toString(),
        format(point.x),
        format(point.y),
        format(exactY),
        formatScientific(abs(exactY - point.y)),
    )
    for (k in parts.indices) if (parts[k].length > widest[k].length) widest[k] = parts[k]
}

@Composable
fun MethodTable(points: List<Point>, problem: Problem) {
    val isolateScroll = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource,
            ): Offset = Offset(0f, available.y)

            override suspend fun onPostFling(
                consumed: Velocity,
                available: Velocity,
            ): Velocity = Velocity(0f, available.y)
        }
    }

    val widest = remember(points, problem) {
        val w = Array(TABLE_HEADERS.size) { "" }
        val n = points.size
        if (n == 0) return@remember w
        val sampleSize = minOf(n, WIDEST_SAMPLE_LIMIT)
        val stride = maxOf(1, n / sampleSize)
        var i = 0
        while (i < n) {
            scanWidest(w, i, points[i], problem)
            i += stride
        }
        if ((n - 1) % stride != 0) scanWidest(w, n - 1, points[n - 1], problem)
        w
    }

    val measurer = rememberTextMeasurer()
    val density = LocalDensity.current
    val bodyStyle = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace)
    val headerStyle = MaterialTheme.typography.labelLarge

    val columnWidths: List<Dp> = remember(widest, density, bodyStyle, headerStyle) {
        List(TABLE_HEADERS.size) { k ->
            val headerPx = measurer.measure(TABLE_HEADERS[k], headerStyle).size.width
            val dataPx = if (widest[k].isEmpty()) 0
            else measurer.measure(widest[k], bodyStyle).size.width
            with(density) { maxOf(headerPx, dataPx).toDp() + 4.dp }
        }
    }

    val spacing = LocalAppDimens.current.paddingTiny
    val totalWidth = columnWidths.fold(0.dp) { acc, w -> acc + w } +
        spacing * (TABLE_HEADERS.size - 1)

    BoxWithConstraints {
        val needsHScroll = totalWidth > maxWidth
        val hScroll = rememberScrollState()
        val outerModifier = if (needsHScroll) Modifier.horizontalScroll(hScroll) else Modifier
        Column(
            modifier = outerModifier,
            verticalArrangement = Arrangement.spacedBy(spacing),
        ) {
            TableHeaderRow(widths = columnWidths)
            LazyColumn(
                modifier = Modifier
                    .width(totalWidth)
                    .heightIn(max = TABLE_MAX_HEIGHT)
                    .nestedScroll(isolateScroll),
                verticalArrangement = Arrangement.spacedBy(spacing),
            ) {
                items(count = points.size, key = { it }) { i ->
                    val methodPoint = points[i]
                    val exactY = problem.equation.real.apply(methodPoint.x, problem.point)
                    TableDataRow(
                        index = i,
                        x = methodPoint.x,
                        methodY = methodPoint.y,
                        exactY = exactY,
                        widths = columnWidths,
                    )
                }
            }
        }
    }
}
