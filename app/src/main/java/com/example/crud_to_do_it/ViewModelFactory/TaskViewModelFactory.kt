package com.example.crud_to_do_it.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.crud_to_do_it.Model.TaskDao
import com.example.crud_to_do_it.ViewModel.TaskViewModel

class TaskViewModelFactory (private val taskDao: TaskDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {

            return TaskViewModel(taskDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
