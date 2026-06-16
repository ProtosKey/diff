package diff.app.view.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ChipGrid(
    modifier: Modifier = Modifier,
    maxLines: Int = 3,
    horizontalSpacing: Dp = 8.dp,
    verticalSpacing: Dp = 8.dp,
    content: @Composable () -> Unit,
) {
    BoxWithConstraints(modifier = modifier) {
        val widthCap = constraints.maxWidth
        val scrollState = rememberScrollState()
        Layout(
            content = content,
            modifier = Modifier.horizontalScroll(scrollState),
        ) { measurables, _ ->
            val hPx = horizontalSpacing.roundToPx()
            val vPx = verticalSpacing.roundToPx()
            val placeables = measurables.map { it.measure(Constraints()) }

            if (placeables.isEmpty()) {
                return@Layout layout(0, 0) { }
            }

            val rowsFit = mutableListOf<MutableList<Placeable>>()
            var fitSuccess = true
            run packing@{
                var current = mutableListOf<Placeable>()
                var currentW = 0
                for (p in placeables) {
                    val needed = if (current.isEmpty()) p.width else currentW + hPx + p.width
                    if (current.isEmpty() || needed <= widthCap) {
                        current.add(p)
                        currentW = needed
                    } else {
                        if (rowsFit.size + 1 >= maxLines) {
                            fitSuccess = false
                            return@packing
                        }
                        rowsFit.add(current)
                        current = mutableListOf(p)
                        currentW = p.width
                    }
                }
                if (current.isNotEmpty()) rowsFit.add(current)
            }

            val rows: List<List<Placeable>> = if (fitSuccess) {
                rowsFit
            } else {
                val packed = Array(maxLines) { mutableListOf<Placeable>() }
                val widths = IntArray(maxLines)
                for (p in placeables) {
                    var minIdx = 0
                    for (i in 1 until maxLines) {
                        if (widths[i] < widths[minIdx]) minIdx = i
                    }
                    val needed = if (packed[minIdx].isEmpty()) p.width else widths[minIdx] + hPx + p.width
                    packed[minIdx].add(p)
                    widths[minIdx] = needed
                }
                packed.toList()
            }

            val rowHeights = rows.map { row -> row.maxOfOrNull { it.height } ?: 0 }
            val rowWidths = rows.map { row ->
                if (row.isEmpty()) 0 else row.sumOf { it.width } + (row.size - 1) * hPx
            }
            val totalWidth = (rowWidths.maxOrNull() ?: 0).coerceAtLeast(0)
            val totalHeight = rowHeights.sum() +
                (rowHeights.size - 1).coerceAtLeast(0) * vPx

            layout(width = totalWidth, height = totalHeight) {
                var y = 0
                for ((i, row) in rows.withIndex()) {
                    var x = 0
                    for (p in row) {
                        p.place(x, y)
                        x += p.width + hPx
                    }
                    y += rowHeights[i] + vPx
                }
            }
        }
    }
}
