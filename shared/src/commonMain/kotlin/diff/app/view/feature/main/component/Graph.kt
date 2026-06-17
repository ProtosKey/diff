package diff.app.view.feature.main.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import diff.app.domain.model.Point
import diff.app.domain.utils.MethodKind
import diff.app.presentation.state.MainState
import diff.app.theme.realColor
import diff.app.theme.methodColor
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.round

private data class Bounds(
    val minHor: Double,
    val maxHor: Double,
    val minVer: Double,
    val maxVer: Double,
)

private const val MAX_Y_ASPECT = 5.0
private const val OFFSCREEN = 1_000_000.0

private fun computeBounds(state: MainState): Bounds {
    val finite = (state.exact + state.solutions.values.flatten())
        .filter { it.x.isFinite() && it.y.isFinite() }
    if (finite.isEmpty()) return Bounds(-10.0, 10.0, -10.0, 10.0)

    val minX = finite.minOf { it.x }
    val maxX = finite.maxOf { it.x }
    val minY = finite.minOf { it.y }
    val rawMaxY = finite.maxOf { it.y }

    val xRange = (maxX - minX).coerceAtLeast(1.0)
    val yAspectCap = minY + xRange * MAX_Y_ASPECT
    val maxY = minOf(rawMaxY, yAspectCap)

    val padX = xRange * 0.1
    val padY = (maxY - minY).coerceAtLeast(1.0) * 0.1
    return Bounds(minX - padX, maxX + padX, minY - padY, maxY + padY)
}

@Composable
fun Graph(
    state: MainState,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    val methodColors = remember {
        MethodKind.entries.associateWith { methodColor(it) }
    }
    val exactStrokeColor = realColor()

    val bounds = remember(state.exact, state.solutions) { computeBounds(state) }

    var zoom by remember { mutableStateOf(1f) }
    var panOffset by remember { mutableStateOf(Offset.Zero) }
    var canvasSize by remember { mutableStateOf(Offset(1f, 1f)) }

    val padding = 80f

    fun toMath(offset: Offset): Pair<Double, Double> {
        val dw = canvasSize.x - padding * 2
        val dh = canvasSize.y - padding * 2
        val mx = ((offset.x - panOffset.x) / zoom - padding) / dw *
            (bounds.maxHor - bounds.minHor) + bounds.minHor
        val my = bounds.maxVer - (((offset.y - panOffset.y) / zoom - padding) / dh *
            (bounds.maxVer - bounds.minVer))
        return mx to my
    }

    val textMeasurer = rememberTextMeasurer()
    val labelStyle = TextStyle(
        color = colors.onSurfaceVariant.copy(alpha = 0.5f),
        fontSize = MaterialTheme.typography.bodySmall.fontSize,
    )
    val axisLabelStyle = TextStyle(
        color = colors.onSurfaceVariant,
        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
    )

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .clipToBounds()
            .onGloballyPositioned {
                canvasSize = Offset(it.size.width.toFloat(), it.size.height.toFloat())
            }
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoomChange, _ ->
                    val newZoom = (zoom * zoomChange).coerceIn(0.01f, 10_000f)
                    panOffset = centroid + (panOffset - centroid) * (newZoom / zoom) + pan
                    zoom = newZoom
                }
            },
    ) {
        val drawWidth = size.width - padding * 2
        val drawHeight = size.height - padding * 2

        fun toOffset(mathX: Double, mathY: Double): Offset {
            val xProgress = (mathX - bounds.minHor) / (bounds.maxHor - bounds.minHor)
            val yProgress = (bounds.maxVer - mathY) / (bounds.maxVer - bounds.minVer)
            val px = (padding + xProgress * drawWidth).coerceIn(-OFFSCREEN, OFFSCREEN)
            val py = (padding + yProgress * drawHeight).coerceIn(-OFFSCREEN, OFFSCREEN)
            val base = Offset(px.toFloat(), py.toFloat())
            return base * zoom + panOffset
        }

        val (topLeftMx, topLeftMy) = toMath(Offset.Zero)
        val (bottomRightMx, bottomRightMy) = toMath(Offset(size.width, size.height))

        val zeroPos = toOffset(0.0, 0.0)

        drawGrid(
            minHorizon = min(topLeftMx, bottomRightMx),
            maxHorizon = max(topLeftMx, bottomRightMx),
            minVertical = min(topLeftMy, bottomRightMy),
            maxVertical = max(topLeftMy, bottomRightMy),
            color = colors.outlineVariant.copy(alpha = .4f),
            measurer = textMeasurer,
            style = labelStyle,
            offset = ::toOffset,
        )

        drawLine(
            colors.outlineVariant,
            Offset(zeroPos.x, 0f),
            Offset(zeroPos.x, size.height),
            1.dp.toPx(),
        )
        drawLine(
            colors.outlineVariant,
            Offset(0f, zeroPos.y),
            Offset(size.width, zeroPos.y),
            1.dp.toPx(),
        )

        val yLabelLeft = zeroPos.x + 10f
        if (yLabelLeft in 0f..size.width) {
            drawText(textMeasurer, "Y", Offset(yLabelLeft, 10f), axisLabelStyle)
        }
        val xLabelTop = zeroPos.y - 55f
        if (xLabelTop in 0f..size.height) {
            drawText(textMeasurer, "X", Offset(size.width - 40f, xLabelTop), axisLabelStyle)
        }

        val dotRadius = 5.dp.toPx()
        val dotHole = 3.dp.toPx()

        if (state.exact.isNotEmpty()) {
            drawPolyline(state.exact, exactStrokeColor, 1.5.dp.toPx(), ::toOffset)
        }

        state.solutions
            .filter { (kind, _) -> state.visible[kind] ?: true }
            .forEach { (kind, points) ->
                val curveColor = methodColors[kind] ?: colors.outline
                drawPolyline(points, curveColor, 2.dp.toPx(), ::toOffset)
                drawPoints(points, curveColor, dotRadius, dotHole, ::toOffset)
            }
    }
}

private fun DrawScope.drawPoints(
    points: List<Point>,
    color: Color,
    radius: Float,
    holeRadius: Float,
    toOffset: (Double, Double) -> Offset,
) {
    points.forEach { point ->
        if (!point.x.isFinite() || !point.y.isFinite()) return@forEach
        val center = toOffset(point.x, point.y)
        if (!center.x.isFinite() || !center.y.isFinite()) return@forEach
        drawCircle(color = color, radius = radius, center = center)
        drawCircle(color = Color.White, radius = holeRadius, center = center)
    }
}

private fun DrawScope.drawPolyline(
    points: List<Point>,
    color: Color,
    width: Float,
    toOffset: (Double, Double) -> Offset,
) {
    val path = Path()
    var first = true
    points.forEach { point ->
        if (!point.x.isFinite() || !point.y.isFinite()) return@forEach
        val pos = toOffset(point.x, point.y)
        if (!pos.x.isFinite() || !pos.y.isFinite()) {
            first = true
            return@forEach
        }
        if (first) path.moveTo(pos.x, pos.y) else path.lineTo(pos.x, pos.y)
        first = false
    }
    drawPath(path = path, color = color, style = Stroke(width = width))
}

private fun DrawScope.drawGrid(
    minHorizon: Double,
    maxHorizon: Double,
    minVertical: Double,
    maxVertical: Double,
    color: Color,
    measurer: TextMeasurer,
    style: TextStyle,
    offset: (Double, Double) -> Offset,
    stroke: Dp = 1.dp,
) {
    val horizon = gridStep((maxHorizon - minHorizon) / 8.0)
    val vertical = gridStep((maxVertical - minVertical) / 16.0)
    val precisionX = precisionFor(horizon)
    val precisionY = precisionFor(vertical)

    var current = floor(minHorizon / horizon) * horizon
    while (current <= maxHorizon + horizon) {
        val value = offset(current, 0.0).x
        if (value.isFinite() && value in 0f..size.width) {
            drawLine(
                color = color,
                start = Offset(value, 0f),
                end = Offset(value, size.height),
                strokeWidth = stroke.toPx(),
            )
            drawText(
                textMeasurer = measurer,
                text = format(current, precisionX),
                style = style,
                topLeft = Offset(value + 15f, size.height - 50f),
            )
        }
        current += horizon
    }

    var currentVertical = floor(minVertical / vertical) * vertical
    while (currentVertical <= maxVertical + vertical) {
        val canvasY = offset(0.0, currentVertical).y
        if (canvasY.isFinite() && canvasY in 0f..size.height) {
            drawLine(
                color = color,
                start = Offset(0f, canvasY),
                end = Offset(size.width, canvasY),
                strokeWidth = stroke.toPx(),
            )
            drawText(
                textMeasurer = measurer,
                text = format(currentVertical, precisionY),
                style = style,
                topLeft = Offset(20f, canvasY + 10f),
            )
        }
        currentVertical += vertical
    }
}

private fun gridStep(step: Double): Double {
    if (step <= 0.0) return 1.0
    val magnitude = 10.0.pow(floor(log10(step)))
    val ratio = step / magnitude
    return magnitude * when {
        ratio < 1.5 -> 1.0
        ratio < 3.5 -> 2.0
        ratio < 7.5 -> 5.0
        else -> 10.0
    }
}

private fun precisionFor(step: Double): Int {
    if (step >= 1.0) return 0
    return (-floor(log10(step))).toInt().coerceIn(0, 8)
}

private fun format(value: Double, precision: Int): String {
    if (precision <= 0) {
        val rounded = round(value).toLong()
        if (rounded == 0L) return "0"
        return rounded.toString()
    }
    val factor = 10.0.pow(precision)
    val scaled = round(value * factor).toLong()
    if (scaled == 0L) return "0"
    val sign = if (scaled < 0) "-" else ""
    val absScaled = abs(scaled)
    val intPart = absScaled / factor.toLong()
    val fracPart = absScaled % factor.toLong()
    val fracStr = fracPart.toString().padStart(precision, '0')
    return "$sign$intPart.$fracStr"
}
