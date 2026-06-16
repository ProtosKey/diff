package diff.app.view.feature.result.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import diff.app.domain.model.SolveResult
import diff.app.domain.utils.MethodKind
import diff.app.presentation.state.ResultState
import diff.app.theme.LocalAppDimens

private val methodOrder = listOf(MethodKind.EULER, MethodKind.RUNGE, MethodKind.MILNE)

@Composable
fun ResultsContent(state: ResultState, fabHeight: Dp) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(LocalAppDimens.current.paddingSmall),
        contentPadding = PaddingValues(
            top = LocalAppDimens.current.paddingSmall,
            bottom = fabHeight + LocalAppDimens.current.paddingLarge,
        ),
    ) {
        methodOrder.forEach { kind ->
            val result = state.results[kind] ?: return@forEach
            item(key = kind) {
                when (result) {
                    is SolveResult.Success -> MethodSection(
                        kind = kind,
                        solution = result.solution,
                        exact = state.exact,
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
