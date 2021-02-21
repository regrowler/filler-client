import Game.Companion.gameError
import Game.Companion.gameFinishedMessage
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import utils.accentColor
import utils.serviceScope

typealias func = () -> Unit

@ExperimentalMaterialApi
@Composable
fun game(game: Game) {
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            controlPanel(game, Modifier.weight(1f))
            WordsMatrix(game.wordsMatrixModel.value, Modifier.weight(game.wordsMatrixModel.value.size.value.toFloat()))
        }
        if (game.status.value == Game.Companion.Status.ERROR || game.status.value == Game.Companion.Status.GAME_FINISHED) {
            Box(
                Modifier
                    .align(Center)
                    .background(Color.White)
                    .border(Dp(3f), accentColor)
                    .padding(Dp(16f))
            ) {
                Text(
                    text = if (game.status.value == Game.Companion.Status.ERROR) gameError else gameFinishedMessage,
                    modifier = Modifier.align(Center),
                    fontSize = TextUnit.Sp(40),
                    color = accentColor
                )
            }
        }
    }
}


class Game {
    var wordsMatrixModel = mutableStateOf(WordsMatrixModel(defConfig, onGameFinished = {
        status.value = Status.GAME_FINISHED
    }))
    var status = mutableStateOf(Status.IN_ACTION)

    fun newGame() {
        wordsMatrixModel.value.wordsMapConfig = defConfig2
        status.value = Status.IN_ACTION
    }

    companion object {
        enum class Status {
            ERROR, IN_ACTION, GAME_FINISHED
        }

        const val gameFinishedMessage = "Произошла победа!"
        const val gameError = "Произошла ошибка"

        val defConfig = WordsMapConfig(
            size = 10,
            words = arrayListOf(
                Word(0, 0, "fuck", Direction.HORIZONTAL, false),
                Word(0, 1, "kek", Direction.VERTICAL, false),
                Word(2, 4, "kek123", Direction.HORIZONTAL, false)
            )
        )

        val defConfig2 = WordsMapConfig(
            size = 10,
            words = arrayListOf(
                Word(0, 0, "fuck", Direction.HORIZONTAL, false),
                Word(2, 4, "kek123", Direction.HORIZONTAL, false)
            )
        )
    }
}