package diff.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import diff.app.theme.AppTheme
import diff.app.view.feature.main.MainScreen
import org.koin.compose.KoinContext

@Preview
@Composable
fun App(
    onThemeChanged: @Composable (isDark: Boolean) -> Unit = {},
) = AppTheme(onThemeChanged) {
    KoinContext {
        Navigator(MainScreen())
    }
}
