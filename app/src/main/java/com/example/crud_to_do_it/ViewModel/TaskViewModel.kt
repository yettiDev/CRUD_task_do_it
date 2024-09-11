package com.example.crud_to_do_it.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crud_to_do_it.Model.Task
import com.example.crud_to_do_it.Model.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDao: TaskDao):ViewModel() {



private val _Task = MutableStateFlow("")





 var task = _Task.asStateFlow()


 private val _Details = MutableStateFlow("")
 var details = _Details.asStateFlow()


 val tasksList: LiveData<List<Task>> = taskDao.getAllTasks()



 init {
  viewModelScope.launch(Dispatchers.IO) {
   tasksList
  }
 }






 fun insert(task: Task) {

  viewModelScope.launch(Dispatchers.IO) {
   taskDao.insert(task)
  }






  }


 fun deleteAll() {

  viewModelScope.launch(Dispatchers.IO) {
   taskDao.deleteAll()
  }







}


 fun update(task: Task) {

  viewModelScope.launch(Dispatchers.IO) {
   taskDao.update(task)
  }


}


 fun delete(task: Task?) {

  viewModelScope.launch(Dispatchers.IO) {
   if (task != null) {
    taskDao.delete(task)
   }
  }


 }


}




