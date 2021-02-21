import androidx.compose.desktop.Window
import androidx.compose.desktop.WindowEvents
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import utils.AppProps
import utils.serviceScope

var game = Game()

@ExperimentalMaterialApi
fun main() = Window(
    title = "Filler",
    size = IntSize(800, 1000),
    events = WindowEvents(onResize = {
        AppProps.onResize(it)
    })
) {
//    var game by remember {
//        mutableStateOf(Game()
//        )
//    }
//    var listener=GameListener(onStatusChanged = {
//
//    })
    var game by remember { mutableStateOf(game) }
    var showLabel by remember { mutableStateOf(true) }
    var scope = serviceScope()
    scope.launch {
        while (true) {
            delay(1000)
            showLabel = !showLabel
        }
    }
    MaterialTheme {
        game(game)
    }
}