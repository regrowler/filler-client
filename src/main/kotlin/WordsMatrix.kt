import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import java.util.*

@Composable
fun WordsMatrix(wordsMap: WordsMap) {
    Row(Modifier.fillMaxSize().background(Color.DarkGray)) {
        repeat(wordsMap.size) { horizontalIndex ->
            Column {
                repeat(wordsMap.size) { verticalIndex ->
                    Box(
                        Modifier
                            .padding(WordsMap.ITEM_PADDING)
                            .align(Alignment.CenterHorizontally)
                            .weight(1f, true)
                            .clickable {
                                var t = 0
                            }
                    ) {
                        wordsMap.words.find {
                            when (it.direction) {
                                WordsMap.Direction.VERTICAL -> {
                                    (verticalIndex >= it.startY && verticalIndex < (it.startY + it.word.length)) && horizontalIndex == it.startX
                                }
                                WordsMap.Direction.HORIZONTAL -> {
                                    (horizontalIndex >= it.startX && horizontalIndex < (it.startX + it.word.length)) && verticalIndex == it.startY
                                }
                            }
                        }?.let {
                            when (it.direction) {
                                WordsMap.Direction.VERTICAL -> {
                                    val charIndex = verticalIndex - it.startY
                                    Text(it.word[charIndex].toString())
                                }
                                WordsMap.Direction.HORIZONTAL -> {
                                    val charIndex = horizontalIndex - it.startX
                                    Text(it.word[charIndex].toString())
                                }
                            }
                            return@Box
                        }
                        val min = 'a'
                        val max = 'z'
                        val diff = max - min
                        val random = Random()
                        var i = min + random.nextInt(diff + 1)
                        Text(i.toString())
                    }
                }
            }
        }
    }
}


data class WordsMap(
    val size: Int,
    val words: List<Word>
) {
    fun calculateMapSize(): Dp = (ITEM_SIZE + ITEM_PADDING * 2) * size

    data class Word(
        val startX: Int,
        val startY: Int,
        val word: String,
        val direction: Direction,
        val found: Boolean = false
    )

    enum class Direction {
        HORIZONTAL, VERTICAL
    }

    companion object {
        val ITEM_SIZE = Dp(50f)
        val ITEM_PADDING = Dp(4f)
    }
}