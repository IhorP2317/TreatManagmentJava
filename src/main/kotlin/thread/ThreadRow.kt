package thread

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.*
import java.text.*
import java.util.*

@Composable
fun ThreadRow(
    modifier: Modifier,
    thread: Thread,
    onClick: () -> Unit,
    onStop: () -> Unit,
    recompositionValue: Long
) {
    val interactionSource = remember { MutableInteractionSource() }

    val threadStateChangeTime by remember(thread.state) {
        mutableStateOf(SimpleDateFormat("HH:mm:ss").format(Date(System.currentTimeMillis())))
    }

    Row(
        modifier = modifier
            .hoverable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                onClick = onClick
            )
//            .background(color = if (isChosen) Color(0x44000000) else if (isRent) Color(0x44115511) else Color.Transparent)
            .background(color = when (thread.state) {
                Thread.State.NEW -> Color(0x44119911)
                Thread.State.RUNNABLE -> Color(0x44444444)
                Thread.State.BLOCKED -> Color(0x44111111)
                Thread.State.WAITING -> Color(0x44111177)
                Thread.State.TIMED_WAITING -> Color(0x44113377)
                Thread.State.TERMINATED -> Color(0x44991111)
                null -> Color.Transparent
            })
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(2.5f / 10f),
            text = thread.name,
            style = TextStyle.Default.copy(
                fontSize = 16.sp
            )
        )
        Text(
            modifier = Modifier.fillMaxWidth(2.5f / 7.5f),
            text = thread.state.toString(),
            style = TextStyle.Default.copy(
                fontSize = 16.sp
            )
        )
        Text(
            modifier = Modifier.fillMaxWidth(1.5f / 5f),
            text = thread.priority.toString(),
            style = TextStyle.Default.copy(
                fontSize = 16.sp
            )
        )
        Text(
            modifier = Modifier.fillMaxWidth(1.75f / 3.5f),
            text = threadStateChangeTime,
            style = TextStyle.Default.copy(
                fontSize = 16.sp
            )
        )
        Button(
            onClick = onStop,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red, contentColor = Color.Black)

        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Stop",
                style = TextStyle.Default.copy(
                    fontSize = 16.sp
                )
            )

        }

    }
}