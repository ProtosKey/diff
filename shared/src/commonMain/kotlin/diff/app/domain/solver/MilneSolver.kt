package diff.app.domain.solver

import diff.app.domain.basic.Solve
import diff.app.domain.model.Point
import diff.app.domain.model.Problem
import diff.app.domain.model.Solution
import diff.app.domain.model.SolveResult
import diff.app.domain.utils.MethodKind
import kotlin.math.abs
import kotlin.math.round

class MilneSolver : Solve {
    override val kind: MethodKind = MethodKind.MILNE

    override fun solve(problem: Problem): SolveResult {
        val step = problem.step.value
        val derivative = problem.equation.derivative
        val exact = problem.equation.real
        val stepCount = round(problem.interval.length / step).toInt()
        val tolerance = problem.epsilon.value

        if (stepCount > MAX_NODES) {
            return SolveResult.Failure(kind, "Слишком большое количество точек")
        }

        val xValues = DoubleArray(stepCount + 1) { i -> problem.point.x + i * step }
        val yValues = DoubleArray(stepCount + 1)
        val slopes = DoubleArray(stepCount + 1)
        yValues[0] = problem.point.y

        bootstrapWithRungeKutta(problem, step, xValues, yValues)
        for (i in 0..3) slopes[i] = derivative.apply(xValues[i], yValues[i])

        var maxCorrectorIterations = 0

        for (i in 3 until stepCount) {
            val predictor = yValues[i - 3] + 4.0 * step / 3.0 *
                (2.0 * slopes[i] - slopes[i - 1] + 2.0 * slopes[i - 2])

            var yCurrent = predictor
            var yPrevious: Double
            var iterations = 0
            do {
                yPrevious = yCurrent
                val slopeNext = derivative.apply(xValues[i + 1], yPrevious)
                yCurrent = yValues[i - 1] + step / 3.0 *
                    (slopes[i - 1] + 4.0 * slopes[i] + slopeNext)
                iterations++
            } while (abs(yCurrent - yPrevious) > tolerance && iterations < MAX_CORRECTOR_ITER)

            if (iterations >= MAX_CORRECTOR_ITER && abs(yCurrent - yPrevious) > tolerance) {
                return SolveResult.Failure(
                    kind = kind,
                    message = "Не удалось достичь заданной точности"
                )
            }

            if (iterations > maxCorrectorIterations) maxCorrectorIterations = iterations
            yValues[i + 1] = yCurrent
            slopes[i + 1] = derivative.apply(xValues[i + 1], yCurrent)
        }

        var maxError = 0.0
        for (i in 0..stepCount) {
            val error = abs(exact.apply(xValues[i], problem.point) - yValues[i])
            if (error > maxError) maxError = error
        }
        /*
        if (maxError > tolerance) {
            return SolveResult.Failure(kind, "Не удалось достичь заданной точности")
        }
         */

        val points = ArrayList<Point>(stepCount + 1).apply {
            for (i in 0..stepCount) add(Point(xValues[i], yValues[i]))
        }
        return SolveResult.Success(
            Solution(
                method = kind,
                points = points,
                step = step,
                error = maxError,
                iterations = maxCorrectorIterations,
                ratio = 1,
            ),
        )
    }

    private fun bootstrapWithRungeKutta(
        problem: Problem,
        step: Double,
        xValues: DoubleArray,
        yValues: DoubleArray,
    ) {
        val derivative = problem.equation.derivative
        var xValue = xValues[0]
        var yValue = yValues[0]
        for (i in 0 until 3) {
            val k1 = step * derivative.apply(xValue, yValue)
            val k2 = step * derivative.apply(xValue + step / 2.0, yValue + k1 / 2.0)
            val k3 = step * derivative.apply(xValue + step / 2.0, yValue + k2 / 2.0)
            val k4 = step * derivative.apply(xValue + step, yValue + k3)
            xValue += step
            yValue += (k1 + 2.0 * k2 + 2.0 * k3 + k4) / 6.0
            yValues[i + 1] = yValue
        }
    }

    private companion object {
        const val MAX_CORRECTOR_ITER = 10
        const val MAX_NODES = 100_000
    }
}
