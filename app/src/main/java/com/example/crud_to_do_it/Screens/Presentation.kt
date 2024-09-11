package com.example.crud_to_do_it.Screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.crud_to_do_it.R
import kotlinx.coroutines.delay

@SuppressLint("ResourceAsColor")
@Composable
fun Presentation(navController: NavController) {
    val context = LocalContext.current

    val visibleState = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)
        visibleState.value = true
    }

    Box(modifier = Modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {



            AnimatedVisibility(
                visible = visibleState.value,
                enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(animationSpec = tween(1000), initialOffsetY = { -40 }),
                exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(animationSpec = tween(500), targetOffsetY = { 40 })
            ) {
                Text(
                    modifier = Modifier

                        .fillMaxWidth()
                        .padding(20.dp),
                    text = "Utilize o MyTask da YettiCorps e transforme a maneira como você organiza sua vida. Simplifique, priorize e conquiste mais todos os dias!",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textAlign = TextAlign.Center,
                        color = Color.Black

                    )
                )
            }

            AnimatedVisibility(
                visible = visibleState.value,
                enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(animationSpec = tween(1000), initialOffsetY = { 40 }),
                exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(animationSpec = tween(500), targetOffsetY = { -40 })
            ) {
                Image(
                    modifier = Modifier.size(300.dp),
                    painter = painterResource(id = R.drawable.tasklistvector),
                    contentDescription = null
                )
            }

            AnimatedVisibility(
                visible = visibleState.value,
                enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(animationSpec = tween(1000), initialOffsetY = { 80 }),
                exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(animationSpec = tween(500), targetOffsetY = { 80 })
            ) {
                Button(
                    modifier = Modifier.padding(top = 20.dp),
                    
                    onClick = {
                        navController.navigate("Task_Screen")
                    }

                ) {
                    Text(text = "Vamos lá!")
                }
            }
        }


        }

    }

