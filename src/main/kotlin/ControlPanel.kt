import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import utils.accentColor

@ExperimentalMaterialApi
@Composable
fun controlPanel(game: Game, modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier.weight(1f).fillMaxHeight().padding(end = Dp(2f)),
            colors = object : ButtonColors {
                override fun backgroundColor(enabled: Boolean): Color = accentColor

                override fun contentColor(enabled: Boolean): Color = Color.White

            },
            onClick = {
                game.newGame()
            }) {
            Text(
                text = "Новая игра",
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = TextUnit.Sp(21)
            )
        }
        Box(modifier.weight(1f).fillMaxHeight().background(accentColor).padding(start = Dp(2f))) {
            Text(
                text = "Угадано ${game.wordsMatrixModel.value.words.count { it.value.found }} слов из ${game.wordsMatrixModel.value.words.size}",
                modifier = Modifier.align(Center),
                color = Color.White,
                fontSize = TextUnit.Sp(21)
            )
        }
    }
}