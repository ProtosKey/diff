package diff.app.domain.model

import diff.app.domain.utils.Real
import diff.app.domain.utils.Slope

data class Equation(val title: String, val derivative: Slope, val real: Real)
