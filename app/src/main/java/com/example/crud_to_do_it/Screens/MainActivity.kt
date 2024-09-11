package com.example.crud_to_do_it.Screens

import com.example.crud_to_do_it.ViewModelFactory.TaskViewModelFactory
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.crud_to_do_it.Model.AppDatabase
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crud_to_do_it.Model.Screen
import com.example.crud_to_do_it.R
import com.example.crud_to_do_it.ViewModel.TaskViewModel

class MainActivity : ComponentActivity() {
    lateinit var taskViewModel: TaskViewModel

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val taskDao = AppDatabase.getDatabase(application).taskDao()
        val factory = TaskViewModelFactory(taskDao)
        taskViewModel = ViewModelProvider(this, factory).get(TaskViewModel::class.java)

        setContent {
            val navController = rememberNavController()
            var showSplash by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                delay(3000)
                showSplash = false
            }

            if (showSplash) {
                SplashScreen()
            } else {
                AppNavHost(navController = navController, task = taskViewModel)
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.yeicon),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
    }
}

@Composable
fun AppNavHost(task: TaskViewModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = "Presentation_Screen") {
        composable("Presentation_Screen") { Presentation(navController = navController) }
        composable("Task_Screen") { Screen(taskViewModel = task, navController = navController) }
    }
}
