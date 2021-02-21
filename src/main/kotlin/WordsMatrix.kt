import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import utils.*
import java.util.*
import kotlin.collections.ArrayList

@Composable
fun WordsMatrix(wordsMatrixModel: WordsMatrixModel, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        Row(modifier.align(Center).fillMaxSize().aspectRatio(1f, !AppProps.isAppHigher.value)) {
            repeat(wordsMatrixModel.size.value) { horizontalIndex ->
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxSize()
                ) {
                    repeat(wordsMatrixModel.size.value) { verticalIndex ->
                        var wordIndex = wordsMatrixModel.map.value[horizontalIndex][verticalIndex].second
                        var found =
                            wordIndex >= 0 && wordIndex < wordsMatrixModel.words.size && wordsMatrixModel.words[wordIndex].value.found
                        Box(
                            Modifier
                                .padding(wordsMatrixModel.itemPadding.value)
                                .fillMaxSize()
                                .align(Alignment.CenterHorizontally)
                                .weight(1f, true)
                                .background(if (found) accentColor70 else Color.White)
                                .aspectRatio(1f, false)
                                .clickable(enabled = !found) {
                                    wordsMatrixModel.itemClicked(horizontalIndex, verticalIndex)
                                }
                        ) {
                            if (found) {
                                Text(
                                    text = wordsMatrixModel.map.value[horizontalIndex][verticalIndex].first.toUpperCase(),
                                    modifier = Modifier.align(Center),
                                    color = Color.White
                                )
                            } else {
                                Text(
                                    text = wordsMatrixModel.map.value[horizontalIndex][verticalIndex].first.toLowerCase(),
                                    modifier = Modifier.align(Center)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

class WordsMatrixModel(
    wordsMapConfig: WordsMapConfig,
    var onGameFinished: (() -> Unit)? = null
) {
    var words = arrayListOf(*wordsMapConfig.words.map { mutableStateOf(it) }.toTypedArray())
    var firstClickedItem: Pair<Int, Int>? = null

    val size = mutableStateOf(wordsMapConfig.size)
    val itemPadding = mutableStateOf(wordsMapConfig.itemPadding)
    val map = mutableStateOf(calculateMap())

    var wordsMapConfig: WordsMapConfig = wordsMapConfig
        set(value) {
            size.value = value.size
            words.clear()
            words.addAll(value.words.map { mutableStateOf(it) })
            map.value = calculateMap()
            itemPadding.value = value.itemPadding
            field = value
        }

    fun itemClicked(x: Int, y: Int) {
        val clickedWordIndex = map.value[x][y].second
        if (clickedWordIndex >= 0) {
            val clickedWord = words[clickedWordIndex].value
            if (firstClickedItem == null) {
                if (clickedWord.startX == x && clickedWord.startY == y)
                    firstClickedItem = Pair(x, y)
            } else {
                val firstClickedWordIndex = map.value[firstClickedItem!!.first][firstClickedItem!!.second].second
                if (firstClickedWordIndex == clickedWordIndex) {
                    var clickedWordEndX: Int
                    val clickedWordEndY: Int
                    when (clickedWord.direction) {
                        Direction.HORIZONTAL -> {
                            clickedWordEndX = clickedWord.startX + clickedWord.word.length - 1
                            clickedWordEndY = clickedWord.startY
                        }
                        Direction.VERTICAL -> {
                            clickedWordEndX = clickedWord.startX
                            clickedWordEndY = clickedWord.startY + clickedWord.word.length - 1
                        }
                    }
                    if (clickedWordEndX == x && clickedWordEndY == y) {
                        setWordFound(clickedWordIndex, true)
                        checkGameCompleted()
                    }
                }
                firstClickedItem = null
            }
        } else {
            firstClickedItem = null
        }
    }

    private fun checkGameCompleted() {
        if (!words.any { !it.value.found }) {
            onGameFinished?.invoke()
        }
    }

    fun setWordFound(index: Int, found: Boolean) {
        words[index].value = words[index].value.copy(found = found)
    }

    private fun calculateMap(): List<List<Pair<String, Int>>> {
        var map = emptyMap()
        repeat(size.value) { horizontalIndex ->
            repeat(size.value) { verticalIndex ->
                words.indexOfFirst {
                    with(it.value) {
                        when (direction) {
                            Direction.VERTICAL -> {
                                (verticalIndex >= startY && verticalIndex < (startY + word.length)) && horizontalIndex == startX
                            }
                            Direction.HORIZONTAL -> {
                                (horizontalIndex >= startX && horizontalIndex < (startX + word.length)) && verticalIndex == startY
                            }
                        }
                    }
                }.let {
                    if (it >= 0 && it < words.size) {
                        words[it].let { word ->

                            when (word.value.direction) {
                                Direction.VERTICAL -> {
                                    val charIndex = verticalIndex - word.value.startY
                                    map[horizontalIndex][verticalIndex] =
                                        Pair(word.value.word[charIndex].toString(), it)
                                }
                                Direction.HORIZONTAL -> {
                                    val charIndex = horizontalIndex - word.value.startX
                                    map[horizontalIndex][verticalIndex] =
                                        Pair(word.value.word[charIndex].toString(), it)
                                }
                            }
                            return@repeat
                        }
                    } else {
                        val min = 'a'
                        val max = 'z'
                        val diff = max - min
                        val random = Random()
                        val i = min + random.nextInt(diff + 1)
                        map[horizontalIndex][verticalIndex] = Pair(i.toString(), -1)
                    }
                }
            }
        }
        return map
    }

    private fun emptyMap(): ArrayList<ArrayList<Pair<String, Int>>> {
        var map = ArrayList<ArrayList<Pair<String, Int>>>()
        repeat(size.value) { index ->
            map.add(arrayListOf())
            repeat(size.value) {
                map[index].add(Pair("", -1))
            }
        }
        return map
    }
}

data class WordsMapConfig(
    val size: Int,
    val words: List<Word>,
    val itemPadding: Dp = 5.dp
)

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