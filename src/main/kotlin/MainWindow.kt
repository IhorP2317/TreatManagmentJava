import androidx.compose.desktop.ui.tooling.preview.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import guest.FoodGuest
import guest.TableGuest
import kotlinx.coroutines.*
import thread.*
import utils.*
import java.text.*
import java.util.*

@Composable
@Preview
fun MainWindow(
    modifier: Modifier
) {
    val lock = Any()

    var tablesAmountText by remember {
        mutableStateOf("10")
    }

    var waitersAmountText by remember {
        mutableStateOf("20")
    }

    val tableThreads = remember {
        mutableStateListOf<Thread>()
    }

    val foodThreads = remember {
        mutableStateListOf<Thread>()
    }

    val restaurant by remember(tablesAmountText, waitersAmountText) {
        mutableStateOf(
            Restaurant(
                tablesAmountText.ifBlank { "1" }.toInt(),
                waitersAmountText.ifBlank { "1" }.toInt(),
                tableThreads,
                foodThreads
            )
        )
    }

    var tableThreadsSortingOrder by remember {
        mutableStateOf<ThreadSortingOrder>(ThreadSortingOrder.None)
    }

    var foodThreadsSortingOrder by remember {
        mutableStateOf<ThreadSortingOrder>(ThreadSortingOrder.None)
    }

    var threadListsRecompositionValue by remember {
        mutableStateOf(0L)
    }

    LaunchedEffect(key1 = true, block = {
        while (true) {
            threadListsRecompositionValue++

            tableThreads.forEach {
                if (it.state == Thread.State.TERMINATED) {
                    synchronized(lock) {
                        launch {
                            delay(1000)
                            tableThreads.remove(it)
                        }
                    }
                }
            }

            foodThreads.forEach {
                if (it.state == Thread.State.TERMINATED) {
                    synchronized(lock) {
                        launch {
                            delay(1000)
                            foodThreads.remove(it)
                        }
                    }
                }
            }

            delay(10)
        }
    })

    Box(
        modifier = modifier
            .padding(16.dp)
        .background(Color.hsl(49.0f, 0.91f, 0.52f) ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Text(
//                    modifier = Modifier,
//                    text = "Tables"
//                )
                TextField(
                    value = tablesAmountText,
                    onValueChange = { newValue: String ->
                        tablesAmountText = if (newValue.isBlank()) newValue else newValue.filter {
                            it.isDigit()
                        }.filterIndexed { index: Int, it: Char ->
                            index == 0 && it != '0' || index != 0
                        }
                    },
                    placeholder = {
                        Text(text = "Tables", color = Color.White) // Set the placeholder text color
                    },
                    colors = TextFieldDefaults.textFieldColors( Color.Blue)
//                    textStyle = TextStyle(color = Color.White), // Set the text color
//                    cursorColor = Color.White // Set the cursor color

                )

                Spacer(modifier = Modifier.width(16.dp))
                TextField(
                    value = waitersAmountText,
                    onValueChange = { newValue ->
                        waitersAmountText = if (newValue.isBlank()) newValue else newValue.filter {
                            it.isDigit()
                        }.filterIndexed { index, it ->
                            index == 0 && it != '0' || index != 0
                        }
                    },
                    placeholder = {
                        Text(text = "Waiters")
                    },
                    colors = TextFieldDefaults.textFieldColors( Color.Blue)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        restaurant.reserveTable(TableGuest())
                    }

                ) {
                    Text(text = "Order table")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        restaurant.orderFood(FoodGuest())
                    }

                ) {
                    Text(text = "Order food")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        restaurant.killAllThreads()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)

                ) {
                    Text(text = "Kill all threads")
                }
            }
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(0.45f)


                ) {
                    Text(
                        text = "Table reserving (${tableThreads.size})",
                        modifier= Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 8.dp),

                        style = TextStyle.Default.copy(
                            fontSize = 32.sp,
                            textAlign = TextAlign.Center

                        )
                    )
                    ThreadRowHeader(
                        modifier = Modifier.fillMaxWidth(),
                        onSortingOrderChange = { newOrder ->
                            tableThreadsSortingOrder = if (newOrder == tableThreadsSortingOrder) {
                                ThreadSortingOrder.None
                            } else {
                                newOrder
                            }
                        }
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(when (tableThreadsSortingOrder) {
                            ThreadSortingOrder.Name -> tableThreads.sortedBy { it.name }
                            ThreadSortingOrder.None -> tableThreads
                            ThreadSortingOrder.Priority -> tableThreads.sortedBy { it.priority }
                            ThreadSortingOrder.State -> tableThreads.sortedBy { it.state }
                            ThreadSortingOrder.Time -> tableThreads
                        }) { index, thread ->
                            ThreadRow(
                                modifier = Modifier.fillMaxWidth(),
                                thread = thread,
                                onClick = {

                                },
                                onStop = {
                                    thread.interrupt()
                                },
                                recompositionValue = threadListsRecompositionValue
                            )
                        }
                    }
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.05f)

                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Food ordering (${foodThreads.size})",
                        modifier= Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 8.dp),
                        style = TextStyle.Default.copy(
                            fontSize = 32.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                    ThreadRowHeader(
                        modifier = Modifier.fillMaxWidth(),
                        onSortingOrderChange = { newOrder ->
                            foodThreadsSortingOrder = if (newOrder == foodThreadsSortingOrder) {
                                ThreadSortingOrder.None
                            } else {
                                newOrder
                            }
                        }
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(when (foodThreadsSortingOrder) { // <-- Change here
                            ThreadSortingOrder.Name -> foodThreads.sortedBy { it.name }
                            ThreadSortingOrder.None -> foodThreads
                            ThreadSortingOrder.Priority -> foodThreads.sortedBy { it.priority }
                            ThreadSortingOrder.State -> foodThreads.sortedBy { it.state }
                            ThreadSortingOrder.Time -> foodThreads
                        }) { index, thread ->
                            ThreadRow(
                                modifier = Modifier.fillMaxWidth(),
                                thread = thread,
                                onClick = {

                                },
                                onStop = {
                                    thread.interrupt()
                                },
                                recompositionValue = threadListsRecompositionValue
                            )
                        }
                    }
                }
            }
        }
    }
}