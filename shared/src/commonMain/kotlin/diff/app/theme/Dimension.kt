package diff.app.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val LocalAppDimens = staticCompositionLocalOf { AppDimens }

object AppDimens {
    val paddingTiny = 4.dp
    val paddingSmall = 8.dp
    val paddingMedium = 16.dp
    val paddingLarge = 24.dp

    val thumbUnchecked = 16.dp
    val thumbChecked = 20.dp

    val strokeThin = 1.dp

    val message = 48.dp
    val navbar = 84.dp

    val iconMedium = 24.dp
    val iconLarge = 48.dp

    val radiusMedium = 12.dp

    val legendDot = 12.dp

    val trackWidth = 52.dp
    val trackHeight = 32.dp
}
