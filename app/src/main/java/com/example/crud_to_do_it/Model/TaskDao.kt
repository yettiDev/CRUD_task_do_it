package com.example.crud_to_do_it.Model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao{

    @Query("SELECT * FROM tasks")
     fun getAllTasks(): LiveData<List<Task>>

    @Insert
    fun insert(task: Task)

    @Delete
     fun delete(task: Task)

     @Query("DELETE  FROM tasks")
     fun deleteAll()

    @Update
    fun update(task: Task)

}