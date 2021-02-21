package utils

import androidx.compose.ui.unit.Dp

val Number.dp: Dp
    get() {
        return Dp(toFloat())
    }