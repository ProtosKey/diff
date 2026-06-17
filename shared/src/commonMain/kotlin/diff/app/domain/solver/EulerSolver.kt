package diff.app.domain.solver

import diff.app.domain.basic.Solve
import diff.app.domain.model.Point
import diff.app.domain.model.Problem
import diff.app.domain.model.Solution
import diff.app.domain.model.SolveResult
import diff.app.domain.utils.MethodKind
import kotlin.math.abs
import kotlin.math.round

class EulerSolver : Solve {
    override val kind: MethodKind = MethodKind.EULER

    override fun solve(problem: Problem): SolveResult {
        val tolerance = problem.epsilon.value
        val denominator = (1 shl kind.order) - 1.0
        val originalStep = problem.step.value
        var coarseStep = originalStep
        var coarse = integrate(problem, coarseStep)
            ?: return SolveResult.Failure(kind, "Слишком большое количество точек")
        var fine = integrate(problem, coarseStep / 2.0)
            ?: return SolveResult.Failure(kind, "Слишком большое количество точек")
        var runge = abs(coarse.last().y - fine.last().y) / denominator
        var halving = 0

        while (runge > tolerance && halving < MAX_HALVING) {
            coarseStep /= 2.0
            coarse = fine
            val nextFine = integrate(problem, coarseStep / 2.0) ?: break
            fine = nextFine
            runge = abs(coarse.last().y - fine.last().y) / denominator
            halving++
        }

        if (runge > tolerance) {
            return SolveResult.Failure(
                kind = kind,
                message = "Не удалось достичь заданной точности"
            )
        }

        val finalStep = coarseStep / 2.0
        val ratio = round(originalStep / finalStep).toInt().coerceAtLeast(1)
        return SolveResult.Success(
            Solution(
                method = kind,
                points = fine,
                step = finalStep,
                error = runge,
                iterations = halving,
                ratio = ratio,
            ),
        )
    }

    private fun integrate(problem: Problem, step: Double): List<Point>? {
        val derivative = problem.equation.derivative
        val stepCount = round(problem.interval.length / step).toInt()
        if (stepCount > MAX_NODES) return null
        val result = ArrayList<Point>(stepCount + 1)
        var xValue = problem.point.x
        var yValue = problem.point.y
        result.add(Point(xValue, yValue))
        repeat(stepCount) {
            val slope = derivative.apply(xValue, yValue)
            val xNext = xValue + step
            val yPredictor = yValue + step * slope
            val yNext = yValue + step / 2.0 * (slope + derivative.apply(xNext, yPredictor))
            xValue = xNext
            yValue = yNext
            result.add(Point(xValue, yValue))
        }
        return result
    }

    private companion object {
        const val MAX_HALVING = 10
        const val MAX_NODES = 100_000
    }
}
