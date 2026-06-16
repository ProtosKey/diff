package diff.app.view.feature.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import diff.app.data.MainStore
import diff.app.presentation.viewmodel.MainViewModel
import org.koin.compose.koinInject
import diff.app.theme.LocalAppDimens
import diff.app.view.basic.factory
import diff.app.view.component.Empty
import diff.app.view.component.Message
import diff.app.view.component.NavigationBar
import diff.app.view.component.Title
import diff.app.view.feature.main.component.Graph

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val store = koinInject<MainStore>()
        val viewModel = viewModel<MainViewModel>(factory = factory { MainViewModel(store) })
        val state by viewModel.state.collectAsStateWithLifecycle()
        val message by viewModel.notification.collectAsStateWithLifecycle()

        Scaffold(
            bottomBar = { NavigationBar(navigator) },
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
                    Title(label = "Главная")

                    when {
                        state.isLoading -> Box(
                            modifier = Modifier.fillMaxWidth().padding(32.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                        state.solutions.isEmpty() || state.exact.isEmpty() -> Empty(
                            help = "Нажмите «Решить» на странице ввода",
                            icon = Icons.Default.ShowChart,
                        )
                        else -> Graph(
                            state = state,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }

                Message(
                    message = message.message,
                    isVisible = message.isVisible,
                    onClick = { viewModel.hideMessage() },
                    bottom = 0.dp,
                    type = message.messageType,
                )
            }
        }
    }
}
