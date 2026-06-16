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
            title = "y' = -y",
            derivative = { _, y -> -y },
            real = { x, point -> point.y * exp(-(x - point.x)) },
        ),
        Equation(
            title = "y' = x + y",
            derivative = { x, y -> x + y },
            real = { x, point ->
                (point.y + point.x + 1.0) * exp(x - point.x) - x - 1.0
            },
        ),
        Equation(
            title = "y' = y - x",
            derivative = { x, y -> y - x },
            real = { x, point ->
                (x + 1.0) + (point.y - point.x - 1.0) * exp(x - point.x)
            },
        ),
        Equation(
            title = "y' = 1 - y",
            derivative = { _, y -> 1.0 - y },
            real = { x, point ->
                1.0 + (point.y - 1.0) * exp(-(x - point.x))
            },
        ),
        Equation(
            title = "y' = 2x",
            derivative = { x, _ -> 2.0 * x },
            real = { x, point -> point.y + x * x - point.x * point.x },
        ),
        Equation(
            title = "y' = sin(x)",
            derivative = { x, _ -> sin(x) },
            real = { x, point -> point.y + cos(point.x) - cos(x) },
        ),
        Equation(
            title = "y' = x · y",
            derivative = { x, y -> x * y },
            real = { x, point ->
                point.y * exp((x * x - point.x * point.x) / 2.0)
            },
        ),
        Equation(
            title = "y' = -2x · y",
            derivative = { x, y -> -2.0 * x * y },
            real = { x, point ->
                point.y * exp(point.x * point.x - x * x)
            },
        ),
        Equation(
            title = "y' = y · cos(x)",
            derivative = { x, y -> y * cos(x) },
            real = { x, point -> point.y * exp(sin(x) - sin(point.x)) },
        ),
    )
}
