//package com.example.chapter1.data.model
//
//
//import androidx.room.Entity        // ← 추가
//import androidx.room.PrimaryKey    // ← 추가
//
//// data/model/Todo.kt
//@Entity(tableName = "todos")
//data class Todo(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int = 0,
//    val title: String,
//    val isCompleted: Boolean = false,
//    val createdAt: Long = System.currentTimeMillis()
//)