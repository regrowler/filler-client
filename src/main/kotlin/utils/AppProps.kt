package utils

import androidx.compose.desktop.AppManager
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.IntSize


fun AppManager.isAppHigher(): Boolean {
    var windows = AppManager.windows
    return if (windows.isNotEmpty()) {
        val window = focusedWindow ?: windows[0]
        window.height > window.width
    } else false
}

object AppProps {
    val isAppHigher = mutableStateOf(AppManager.isAppHigher())
    fun onResize(intSize: IntSize) {
        isAppHigher.value = intSize.height > intSize.width
    }
}