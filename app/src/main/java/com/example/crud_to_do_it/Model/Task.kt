package com.example.crud_to_do_it.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val menu: String,
    val details: String,
    val hour: Int,
    val minute: Int,
    val date:Long

)
