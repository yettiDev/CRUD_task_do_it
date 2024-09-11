package com.example.crud_to_do_it.Model

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.crud_to_do_it.R
import com.example.crud_to_do_it.ViewModel.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Screen(taskViewModel: TaskViewModel, navController: NavController){

    val tasks by taskViewModel.tasksList.observeAsState(emptyList())

    var dialogStateClick = remember {
        mutableStateOf(false)
    }



    var popUpClicked = remember {
        mutableStateOf(false)
    }


    var coroutine = rememberCoroutineScope()


    var currentTask by remember { mutableStateOf<Task?>(null) }

    var dialogState = remember {
        mutableStateOf(false)
    }


    val context= LocalContext.current

    var colorYetti = ContextCompat.getColor(context,R.color.blueYetti)

    Scaffold(modifier = Modifier
        ,   floatingActionButton =  {


            FloatingActionButton(modifier =  Modifier
                .offset(x = (-20).dp, y = (-40).dp)


            ,onClick = {

            dialogState.value = true

            },
                containerColor = Color(colorYetti)
            ) {

            Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
        }


        }


    ) {


        LazyColumn {
            this.items(tasks){task->

                  Box(modifier = Modifier

                      .clickable {
                          dialogStateClick.value=true
                            popUpClicked.value=true

                            currentTask = task
                      }){
                      TaskItem(task = task)



                  }


                      }




                 }
    }



    if (tasks.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tasklistvector2),
                    contentDescription = null,
                    modifier = Modifier.size(190.dp)
                )
                Text(
                    text = "Vamos agora começar a organizar suas tarefas!",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }   






    if (dialogState.value){
        CreateDialog(dialogState = dialogState, onSave = {newTask->
            coroutine.launch(Dispatchers.IO) {

                taskViewModel.insert(newTask)
            }
        })


        }










if (popUpClicked.value){
    popUpClicked(popUpClicked, taskViewModel,currentTask)

}
}




@Composable
fun TaskItem(task: Task) {
    Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
                .padding(16.dp)
            ) {
        Text(text = task.menu, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = task.details, style = MaterialTheme.typography.bodyMedium)
        if (task.hour >= 0 && task.minute >= 0) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Horário: ${task.hour}:${String.format("%02d", task.minute)}")
        }
        // Converte o valor de Long para Date
        val date = Date(task.date)
        // Formata a data em um formato legível
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(date)

        Text(text = "Data: $formattedDate")


    }
}






@Composable
fun CreateDialog(
    dialogState: MutableState<Boolean>,
    onSave: (Task) -> Unit
) {
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    var task by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }

    var selectedHour by remember { mutableStateOf(12) }
    var selectedMinute by remember { mutableStateOf(59) }

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            selectedHour = hour
            selectedMinute = minute
        }, selectedHour, selectedMinute, true
    )

    val calendar = Calendar.getInstance()
    var selectedDate by remember { mutableStateOf(calendar.timeInMillis) }

    val datePickerDialog = DatePickerDialog(
        context, { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.timeInMillis
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    if (dialogState.value) {
        Dialog(onDismissRequest = { dialogState.value = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
            ) {
                Column(
                    modifier = Modifier
                        .width(430.dp)
                        .background(Color.LightGray)
                        .padding(16.dp),
                ) {
                    Text(
                        modifier = Modifier.offset(x = 0.dp, y = -8.dp),
                        text = "Criar Tarefa"
                    )

                    OutlinedTextField(
                        shape = RoundedCornerShape(12.dp),
                        value = task,
                        onValueChange = { newText ->
                            task = newText
                        },
                        placeholder = { Text(text = "Título da tarefa") }
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedTextField(
                        shape = RoundedCornerShape(8.dp),
                        value = details,
                        onValueChange = { newText ->
                            details = newText
                        },
                        placeholder = {
                            Text(fontSize = 12.sp, text = "Gostaria de acrescentar mais detalhes?")
                        }
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    // Organizando os botões
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(onClick = { datePickerDialog.show() }) {
                            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            val formattedDate = dateFormat.format(Date(selectedDate))
                            Text(formattedDate)
                        }

                        Button(onClick = { timePickerDialog.show() }) {
                            Text("$selectedHour:${String.format("%02d", selectedMinute)}")
                        }

                        Button(
                            onClick = {
                                if (task.isEmpty()) {
                                    Toast.makeText(context, "Por favor, insira um título", Toast.LENGTH_LONG).show()
                                } else {
                                    coroutine.launch(Dispatchers.IO) {
                                        val newTask = Task(
                                            0, task, details, hour = selectedHour,
                                            minute = selectedMinute, date = selectedDate
                                        )
                                        onSave(newTask)
                                        dialogState.value = false
                                        task = ""
                                        details = ""
                                    }
                                }
                            }
                        ) {
                            Text(text = "Ok")
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun EditDialog(
    dialogState2: MutableState<Boolean>,
    task1: Task,
    onSave: (Task) -> Unit
) {
    val context = LocalContext.current
    var task by remember { mutableStateOf(task1.menu) }
    var details by remember { mutableStateOf(task1.details) }


    var selectedHour by remember { mutableStateOf(12) }
    var selectedMinute by remember { mutableStateOf(59) }

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            selectedHour = hour
            selectedMinute = minute
        }, selectedHour, selectedMinute, true
    )

    val calendar = Calendar.getInstance()
    var selectedDate by remember { mutableStateOf(calendar.timeInMillis) }

    val datePickerDialog = DatePickerDialog(
        context, { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.timeInMillis
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )


    if (dialogState2.value) {
        Dialog(onDismissRequest = { dialogState2.value = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
            ) {
                Column(
                    modifier = Modifier
                        .width(400.dp)
                        .height(230.dp)
                        .background(Color.LightGray)
                        .padding(16.dp),
                ) {
                    Text(modifier = Modifier.offset(x = 0.dp, y = -8.dp), text = "Editar Tarefa")

                    OutlinedTextField(
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(),
                        value = task,
                        onValueChange = { task = it },
                        placeholder = { Text(text = "Título da tarefa") }
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedTextField(
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(cursorColor = Color.Red),
                        value = details,
                        onValueChange = { details = it },
                        placeholder = { Text(fontSize = 12.sp, text = "Gostaria de acrescentar mais detalhes?") }
                    )








                    Box {



                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .offset(y = 9.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.Bottom
                        ) {


                            Spacer(modifier = Modifier.height(15.dp))

                            // Organizando os botões
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(onClick = { datePickerDialog.show() }) {
                                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                    val formattedDate = dateFormat.format(Date(selectedDate))
                                    Text(formattedDate)
                                }

                                Button(onClick = { timePickerDialog.show() }) {
                                    Text("$selectedHour:${String.format("%02d", selectedMinute)}")
                                }



                            Button(
                                onClick = {
                                    if (task.isEmpty()) {
                                        Toast.makeText(context, "Por favor, insira um título", Toast.LENGTH_LONG).show()
                                    } else {
                                        val updatedTask = task1.copy(menu = task, details = details, hour = selectedHour, minute =  selectedMinute, date = selectedDate )
                                        onSave(updatedTask)
                                        dialogState2.value=false
                                    }
                                }
                            ) {
                                Text("Ok")
                            }
                        }
                    }
                }
            }
        }
    }
}
}


@Composable
fun popUpClicked(popupMenu: MutableState<Boolean>, taskViewModel: TaskViewModel, task: Task?) {

    val context= LocalContext.current
    var currentTask by remember { mutableStateOf<Task?>(null) }
    val coroutine = rememberCoroutineScope()
    var editBooelan = remember {
        mutableStateOf(false)
    }
    if (popupMenu.value) {

        Dialog(onDismissRequest = {popupMenu.value=false}) {
            Box(modifier = Modifier

                ) {
                Row (
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = 9.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically


                ){


                    var deleteBoolean=false
                    Button(onClick = {
                        editBooelan.value=true
                        if (task != null) {
                            currentTask = task
                        }
                    }) {
                        Text(text = "Editar")
                    }
                            Spacer(modifier = Modifier.width(10.dp))


                    Button(onClick = {

                        taskViewModel.delete(task)
                        popupMenu.value=false}

                    ) {
                        Text(text = "Excluir")

                    }


                    Spacer(modifier = Modifier.width(10.dp))


                    Button(onClick = { popupMenu.value=false}) {
                        Text(text = "Sair")
                    }



                    if (editBooelan.value){


                        currentTask?.let { task ->
                            EditDialog(dialogState2 = editBooelan, task1 = task, onSave = { updatedTask ->
                                coroutine.launch(Dispatchers.IO) {
                                    Log.e("Result", "DEU CERTO ANTES ")
                                    taskViewModel.update(updatedTask)
                                    Log.e("Result", "DEU CERTO DEPOIS DE ATT ")


                                }
                                popupMenu.value=false
                            })

                        }




                    }


                }

            }
        }




    }
}








