package thread

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.*
import utils.*

@Composable
fun ThreadRowHeader(
    modifier: Modifier,
    onSortingOrderChange: (ThreadSortingOrder) -> Unit
) {
    Row(
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(2.5f / 10f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = {
                        onSortingOrderChange(ThreadSortingOrder.Name)
                    }
                ),
            text = "Name",
            style = TextStyle.Default.copy(
                fontSize = 24.sp
            )
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(2.5f / 7.5f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = {
                        onSortingOrderChange(ThreadSortingOrder.State)
                    }
                ),
            text = "State",
            style = TextStyle.Default.copy(
                fontSize = 24.sp
            )
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(1.5f / 5f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = {
                        onSortingOrderChange(ThreadSortingOrder.Priority)
                    }
                ),
            text = "Priority",
            style = TextStyle.Default.copy(
                fontSize = 24.sp
            )
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(1.75f / 3.5f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = {
                        onSortingOrderChange(ThreadSortingOrder.Time)
                    }
                ),
            text = "Time",
            style = TextStyle.Default.copy(
                fontSize = 24.sp
            )
        )
    }
}