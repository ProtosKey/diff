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
        var fine = integrate(problem, coarseStep / 2.0)
        var runge = abs(coarse.last().y - fine.last().y) / denominator
        var halvings = 0

        while (runge > tolerance && halvings < MAX_HALVING) {
            coarseStep /= 2.0
            coarse = fine
            fine = integrate(problem, coarseStep / 2.0)
            runge = abs(coarse.last().y - fine.last().y) / denominator
            halvings++
        }

        if (runge > tolerance) {
            return SolveResult.Failure(
                kind = kind,
                message = "Не удалось достичь точности ε = $tolerance за $MAX_HALVING половинений шага. " +
                    "Текущая оценка по Рунге: $runge",
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
                iterations = halvings,
                ratio = ratio,
            ),
        )
    }

    private fun integrate(problem: Problem, step: Double): List<Point> {
        val derivative = problem.equation.derivative
        val stepCount = round(problem.interval.length / step).toInt()
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
        const val MAX_HALVING = 15
    }
}
