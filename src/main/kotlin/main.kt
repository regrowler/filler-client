import androidx.compose.desktop.Window
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

fun main() = Window(title = "Filler") {
    var text by remember { mutableStateOf("Hello, World!") }
    var wordMap by remember { mutableStateOf(getMap()) }
    MaterialTheme {
        WordsMatrix(wordMap)
    }
}

fun getMap(): WordsMap {
    return WordsMap(
        size = 10,
        words = arrayListOf(
            WordsMap.Word(0, 0, "fuck", WordsMap.Direction.HORIZONTAL),
            WordsMap.Word(0, 1, "kek", WordsMap.Direction.VERTICAL)
        )
    )
}