package com.example.myapplication

import android.graphics.drawable.Icon
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            Black()
//            Blue()
//            Ex71()
//            Ex72()
//            Ex73()
            WaterTracker()


        }
    }
}
@Composable
fun WaterTracker() {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        var waterCount by remember { mutableIntStateOf(0) }
        var goal by remember { mutableIntStateOf(1500) }
        var consecutiveDays by remember { mutableIntStateOf(0) }
        var showResetDialog by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Водный трекер",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Стаканов выпито: $waterCount",
            )

            Text(
                text = "Объем: ${waterCount * 250} мл / $goal мл",
            )
            LinearProgressIndicator(
                progress = { (waterCount * 250).toFloat() / goal },
                modifier = Modifier.fillMaxWidth(),
                color = Color.Blue
            )
            Button(
                onClick = { waterCount++ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Выпить стакан воды (250 мл)")
            }
            Button(
                onClick = {
                    showResetDialog = true
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green
                )
            ) {
                Text("Завершить день")
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Дней подряд с нормой 1500+ мл",
                        color = Color.Gray
                    )
                    Text(
                        text = "$consecutiveDays",
                        style = MaterialTheme.typography.headlineLarge,
                        color = if (consecutiveDays > 0) Color.Green else Color.Red
                    )

                    if (consecutiveDays > 0) {
                        Text(
                            text = "Так держать!",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.Green
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Цель:")
                OutlinedTextField(
                    value = goal.toString(),
                    onValueChange = {
                        goal = it.toIntOrNull() ?: 1
                    },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Text("мл")
            }
        }
        if (showResetDialog) {
            AlertDialog(
                onDismissRequest = { showResetDialog = false },
                title = { Text("Завершение дня") },
                text = {
                    val totalWater = waterCount * 250
                    Text("Вы выпили $totalWater мл воды сегодня. " +
                            "Вы уверены, что хотите завершить день?")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val totalWater = waterCount * 250

                            if (totalWater >= 1500) {
                                consecutiveDays++
                            } else {
                                consecutiveDays = 0
                            }

                            waterCount = 0
                            showResetDialog = false
                        }
                    ) {
                        Text("Да, завершить")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showResetDialog = false }
                    ) {
                        Text("Отмена")
                    }
                }
            )
        }
    }
}

@Composable
fun Ex73() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp)
            .height(120.dp)
            .background(Color.Gray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = "Лукашин Евгений Андреевич",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "+7 495 495 95 95",
                )
                Text(
                    text = "г. Москва, 3-я улица Строителей, д. 25, кв. 12",
                )
            }
            Image(

                painter = painterResource(id = R.drawable.background),
                contentDescription = "Аватарка",
                modifier = Modifier
                    .size(80.dp).weight(1f)
            )
        }
    }
}

@Composable
fun Ex72(){
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(start = 5.dp)
            .padding(end = 5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.Gray)
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "имя",
                    fontSize = dimensionResource(R.dimen.text_size).value.sp
                )
                Text(
                    text = "фамилия",
                    fontSize = dimensionResource(R.dimen.text_size).value.sp

                )
                Text(
                    text = "...",
                    fontSize = dimensionResource(R.dimen.text_size).value.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.Gray)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "имя",
                    fontSize = dimensionResource(R.dimen.text_size).value.sp
                )
                Text(
                    text = "фамилия",
                    fontSize = dimensionResource(R.dimen.text_size).value.sp

                )
                Text(
                    text = "...",
                    fontSize = dimensionResource(R.dimen.text_size).value.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.Gray)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "имя",
                    fontSize = dimensionResource(R.dimen.text_size).value.sp
                )
                Text(
                    text = "фамилия",
                    fontSize = dimensionResource(R.dimen.text_size).value.sp

                )
                Text(
                    text = "...",
                    fontSize = dimensionResource(R.dimen.text_size).value.sp
                )
            }
        }
    }
}
@Composable
fun Ex71(){
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 100.dp)
    ) {
        Text(
            text = "имя",
            fontSize = 24.sp
        )
        Text(
            text = "фамилия",
            fontSize = 24.sp

        )
        Text(
            text = "...",
            fontSize = 24.sp
        )
    }
}

@Composable
fun Blue() {
    Box(
        modifier = Modifier
            .padding(top = 100.dp)
            .size(240.dp, 120.dp)
            .background(Color.Blue),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.circle),
            contentDescription = "Circle",
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.tint(Color.Magenta),
            contentScale = ContentScale.FillBounds
        )
    }
}


@Composable
fun Black() {
    Box(
        modifier = Modifier
            .padding(top = 100.dp)
            .size(240.dp, 120.dp)
            .background(Color.Black),
        contentAlignment = Alignment.TopEnd
    ) {
        Image(
            painter = painterResource(id = R.drawable.circle),
            contentDescription = "Circle",
        )
    }
}
