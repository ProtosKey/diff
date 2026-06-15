package diff.app.domain.solver

import diff.app.domain.basic.Solve
import diff.app.domain.model.Point
import diff.app.domain.model.Problem
import diff.app.domain.model.Solution
import diff.app.domain.utils.MethodKind
import kotlin.math.abs
import kotlin.math.round

class EulerSolver : Solve {
    override val kind: MethodKind = MethodKind.EULER

    override fun solve(problem: Problem): Solution {
        val step = problem.step.value
        val points = integrate(problem, step)
        val half = integrate(problem, step / 2.0)

        val denominator = (1 shl kind.order) - 1.0
        var maxError = 0.0
        for (i in points.indices) {
            val halfIndex = 2 * i
            if (halfIndex >= half.size) break
            val error = abs(points[i].y - half[halfIndex].y) / denominator
            if (error > maxError) maxError = error
        }
        return Solution(kind, points, step, maxError)
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
}
