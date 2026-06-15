package diff.app.data.factory

import diff.app.domain.model.Equation
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.sin

object EquationFactory {
    val all: List<Equation> = listOf(
        Equation(
            title = "y' = y",
            derivative = { _, y -> y },
            real = { x, point -> point.y * exp(x - point.x) },
        ),
        Equation(
            title = "y' = x + y",
            derivative = { x, y -> x + y },
            real = { x, point ->
                (point.y + point.x + 1.0) * exp(x - point.x) - x - 1.0
            },
        ),
        Equation(
            title = "y' = y · cos(x)",
            derivative = { x, y -> y * cos(x) },
            real = { x, point -> point.y * exp(sin(x) - sin(point.x)) },
        ),
    )
}
