package diff.app.data.utils

import diff.app.data.factory.EquationFactory
import diff.app.data.factory.SolverFactory
import diff.app.data.model.InputForm
import diff.app.data.model.Storage
import diff.app.domain.exception.InitException
import diff.app.domain.model.Epsilon
import diff.app.domain.model.Interval
import diff.app.domain.model.Point
import diff.app.domain.model.Problem
import diff.app.domain.model.Step
import kotlin.math.round

object SolveAction {
    fun solve(inputForm: InputForm): Storage {
        val problem = buildProblem(inputForm)
        val results = SolverFactory.all.map { it.solve(problem) }
        val exact = sampleReal(problem)
        return Storage(problem, results, exact)
    }

    private fun buildProblem(inputForm: InputForm): Problem {
        val start = StringParser.parseDouble(inputForm.start, "x₀")
        val end = StringParser.parseDouble(inputForm.end, "xₙ")
        val initialY = StringParser.parseDouble(inputForm.initialY, "y₀")
        val stepValue = StringParser.parseDouble(inputForm.step, "h")
        val epsilonValue = StringParser.parseDouble(inputForm.epsilon, "ε")
        val equation = EquationFactory.all.getOrNull(inputForm.equationIndex)
            ?: throw InitException("Не выбрано уравнение")
        return Problem(
            equation = equation,
            interval = Interval(start, end),
            point = Point(start, initialY),
            step = Step(stepValue),
            epsilon = Epsilon(epsilonValue),
        )
    }

    private fun sampleReal(problem: Problem): List<Point> {
        val step = problem.step.value
        val n = round(problem.interval.length / step).toInt()
        val real = problem.equation.real
        val point = problem.point
        return (0..n).map { i ->
            val x = point.x + i * step
            Point(x, real.apply(x, point))
        }
    }
}
