package diff.app.view.feature.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import diff.app.data.MainStore
import diff.app.data.factory.EquationFactory
import diff.app.presentation.viewmodel.InputViewModel
import org.koin.compose.koinInject
import diff.app.theme.LocalAppDimens
import diff.app.view.basic.factory
import diff.app.view.component.ChipGrid
import diff.app.view.component.Message
import diff.app.view.component.NavigationBar
import diff.app.view.component.SolveFab
import diff.app.view.component.Title
import diff.app.view.feature.input.component.EquationChip
import diff.app.view.feature.input.component.FieldLabel
import diff.app.view.feature.input.component.NumberField

class InputScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val store = koinInject<MainStore>()
        val viewModel = viewModel<InputViewModel>(factory = factory { InputViewModel(store) })
        val form by viewModel.form.collectAsStateWithLifecycle()
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
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = fabHeight + LocalAppDimens.current.paddingLarge),
                    verticalArrangement = Arrangement.spacedBy(LocalAppDimens.current.paddingSmall),
                ) {
                    Title(label = "Ввод")

                    FieldLabel("Уравнение")
                    ChipGrid(
                        maxLines = 3,
                        horizontalSpacing = LocalAppDimens.current.paddingSmall,
                        verticalSpacing = LocalAppDimens.current.paddingSmall,
                    ) {
                        EquationFactory.all.forEachIndexed { index, equation ->
                            EquationChip(
                                label = equation.title,
                                selected = form.equationIndex == index,
                                onClick = { viewModel.updateEquationIndex(index) },
                            )
                        }
                    }

                    NumberField(
                        label = "Левая граница",
                        value = form.start,
                        onValueChange = viewModel::updateStart,
                    )
                    NumberField(
                        label = "Правая граница",
                        value = form.end,
                        onValueChange = viewModel::updateEnd,
                    )
                    NumberField(
                        label = "Начальное значение",
                        value = form.initialY,
                        onValueChange = viewModel::updateInitialY,
                    )
                    NumberField(
                        label = "Шаг",
                        value = form.step,
                        onValueChange = viewModel::updateStep,
                    )
                    NumberField(
                        label = "Точность",
                        value = form.epsilon,
                        onValueChange = viewModel::updateEpsilon,
                    )
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
