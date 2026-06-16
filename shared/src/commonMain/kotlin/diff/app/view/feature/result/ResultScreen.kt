package diff.app.view.feature.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import diff.app.data.MainStore
import diff.app.presentation.viewmodel.ResultViewModel
import org.koin.compose.koinInject
import diff.app.theme.LocalAppDimens
import diff.app.view.basic.factory
import diff.app.view.component.Empty
import diff.app.view.component.Message
import diff.app.view.component.NavigationBar
import diff.app.view.component.SolveFab
import diff.app.view.component.Title
import diff.app.view.feature.result.component.ResultsContent

class ResultScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val store = koinInject<MainStore>()
        val viewModel = viewModel<ResultViewModel>(factory = factory { ResultViewModel(store) })
        val state by viewModel.state.collectAsStateWithLifecycle()
        val message by viewModel.notification.collectAsStateWithLifecycle()
        var fabHeight by remember { mutableStateOf(0.dp) }

        Scaffold(
            bottomBar = { NavigationBar(navigator) },
            floatingActionButton = {
                SolveFab(
                    onClick = { viewModel.solve() },
                    onMeasured = { fabHeight = it },
                )
            },
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = LocalAppDimens.current.paddingMedium),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(LocalAppDimens.current.paddingSmall),
                ) {
                    Title(label = "Результаты")

                    when {
                        state.isLoading -> Box(
                            modifier = Modifier.fillMaxWidth().padding(32.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                        state.results.isEmpty() || state.exact.isEmpty() -> Empty(
                            help = "Нажмите «Решить»",
                            icon = Icons.Default.QueryStats,
                        )
                        else -> ResultsContent(state, fabHeight)
                    }
                }

                Message(
                    message = message.message,
                    isVisible = message.isVisible,
                    onClick = { viewModel.hideMessage() },
                    bottom = fabHeight,
                    type = message.messageType,
                )
            }
        }
    }
}
