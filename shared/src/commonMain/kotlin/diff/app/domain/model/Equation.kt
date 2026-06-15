package diff.app.domain.model

import diff.app.domain.basic.Real
import diff.app.domain.basic.Slope

data class Equation(val title: String, val derivative: Slope, val real: Real)
