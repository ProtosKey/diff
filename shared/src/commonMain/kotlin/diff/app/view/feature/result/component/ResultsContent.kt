package diff.app.view.feature.result.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import diff.app.domain.model.SolveResult
import diff.app.domain.utils.MethodKind
import diff.app.presentation.state.ResultState
import diff.app.theme.LocalAppDimens

private val methodOrder = listOf(MethodKind.EULER, MethodKind.RUNGE, MethodKind.MILNE)

@Composable
fun ResultsContent(state: ResultState, fabHeight: Dp) {
    val problem = state.problem ?: return
    val hasHalving = state.results.values.any {
        it is SolveResult.Success && it.solution.ratio > 1
    }
    var showAll by rememberSaveable { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(LocalAppDimens.current.paddingSmall),
        contentPadding = PaddingValues(
            top = LocalAppDimens.current.paddingSmall,
            bottom = fabHeight + LocalAppDimens.current.paddingLarge,
        ),
    ) {
        if (hasHalving) {
            item(key = "toggle") {
                PointsToggleCard(showAll = showAll, onShowAllChange = { showAll = it })
            }
        }
        methodOrder.forEach { kind ->
            val result = state.results[kind] ?: return@forEach
            item(key = kind) {
                when (result) {
                    is SolveResult.Success -> MethodSection(
                        kind = kind,
                        solution = result.solution,
                        problem = problem,
                        showAll = showAll,
                    )
                    is SolveResult.Failure -> MethodErrorCard(
                        kind = kind,
                        message = result.message,
                    )
                }
            }
        }
    }
}
