import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(
            size = DpSize(1440.dp, 810.dp)
        )
    ) {
        MaterialTheme {
            MainWindow(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
