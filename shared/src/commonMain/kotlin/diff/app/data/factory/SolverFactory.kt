package diff.app.data.factory

import diff.app.domain.basic.Solve
import diff.app.domain.solver.EulerSolver
import diff.app.domain.solver.MilneSolver
import diff.app.domain.solver.RungeSolver
import diff.app.domain.utils.MethodKind

object SolverFactory {
    fun create(kind: MethodKind): Solve = when (kind) {
        MethodKind.EULER -> EulerSolver()
        MethodKind.RUNGE -> RungeSolver()
        MethodKind.MILNE -> MilneSolver()
    }

    val all: List<Solve> = MethodKind.entries.map(::create)
}
