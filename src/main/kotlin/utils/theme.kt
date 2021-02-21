package utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces

val accentColor = rgb(216, 27, 96)
val accentColor70 = rgb(216, 27, 96, 0xCC)

fun rgb(red: Int, green: Int, blue: Int, alpha: Int = 0xFF) = Color(red, green, blue, alpha)