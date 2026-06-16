package diff.app.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import diff.app.theme.LocalAppDimens
import diff.app.view.feature.input.InputScreen
import diff.app.view.feature.main.MainScreen
import diff.app.view.feature.result.ResultScreen

@Composable
fun NavigationBar(navigator: Navigator) {
    val screen = navigator.lastItem

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .height(LocalAppDimens.current.navbar),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(LocalAppDimens.current.strokeThin)
                .background(MaterialTheme.colorScheme.outlineVariant),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NavigationElem(
                selected = screen is MainScreen,
                onClick = { if (screen !is MainScreen) navigator.replaceAll(MainScreen()) },
                icon = Icons.Default.ShowChart,
                label = "Главная",
            )

            NavigationElem(
                selected = screen is InputScreen,
                onClick = { if (screen !is InputScreen) navigator.replaceAll(InputScreen()) },
                icon = Icons.Default.Edit,
                label = "Ввод",
            )

            NavigationElem(
                selected = screen is ResultScreen,
                onClick = { if (screen !is ResultScreen) navigator.replaceAll(ResultScreen()) },
                icon = Icons.Default.BarChart,
                label = "Результаты",
            )
        }
    }
}
