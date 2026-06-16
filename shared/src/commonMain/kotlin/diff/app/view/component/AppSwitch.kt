package diff.app.view.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import diff.app.theme.LocalAppDimens

data class SwitchColors(
    val checkedThumb: Color,
    val checkedTrack: Color,
    val checkedBorder: Color,
    val uncheckedThumb: Color,
    val uncheckedTrack: Color,
    val uncheckedBorder: Color,
)

object SwitchDefaults {
    @Composable
    fun colors(
        checkedThumb: Color = MaterialTheme.colorScheme.onPrimary,
        checkedTrack: Color = MaterialTheme.colorScheme.primary,
        checkedBorder: Color = MaterialTheme.colorScheme.primary,
        uncheckedThumb: Color = MaterialTheme.colorScheme.outline,
        uncheckedTrack: Color = MaterialTheme.colorScheme.surface,
        uncheckedBorder: Color = MaterialTheme.colorScheme.outlineVariant,
    ): SwitchColors = SwitchColors(
        checkedThumb = checkedThumb,
        checkedTrack = checkedTrack,
        checkedBorder = checkedBorder,
        uncheckedThumb = uncheckedThumb,
        uncheckedTrack = uncheckedTrack,
        uncheckedBorder = uncheckedBorder,
    )
}

@Composable
fun AppSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    colors: SwitchColors = SwitchDefaults.colors(),
) {
    val trackColor by animateColorAsState(if (checked) colors.checkedTrack else colors.uncheckedTrack)
    val borderColor by animateColorAsState(if (checked) colors.checkedBorder else colors.uncheckedBorder)
    val thumbColor by animateColorAsState(if (checked) colors.checkedThumb else colors.uncheckedThumb)
    val thumbSize by animateDpAsState(if (checked) LocalAppDimens.current.thumbChecked else LocalAppDimens.current.thumbUnchecked)
    val thumbOffset by animateDpAsState(
        if (checked) LocalAppDimens.current.trackWidth - LocalAppDimens.current.strokeThin * 2
                - LocalAppDimens.current.paddingSmall - LocalAppDimens.current.thumbUnchecked else LocalAppDimens.current.paddingSmall,
    )
    Box(
        modifier = modifier
            .size(
                width = LocalAppDimens.current.trackWidth,
                height = LocalAppDimens.current.trackHeight
            )
            .clip(CircleShape)
            .background(color = trackColor)
            .border(
                width = LocalAppDimens.current.strokeThin,
                color = borderColor,
                shape = CircleShape
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.CenterStart,
    ) {
        Box(
            modifier = Modifier
                .offset(x = thumbOffset)
                .size(thumbSize)
                .background(color = thumbColor, shape = CircleShape),
        )
    }
}
