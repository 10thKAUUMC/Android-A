package com.example.chapter1.domain.repository

import com.example.chapter1.data.model.Todo  // ← 추가
import kotlinx.coroutines.flow.Flow           // ← 추가

interface TodoRepository {
    fun getTodosStream(): Flow<List<Todo>>
    suspend fun insert(todo: Todo)
    suspend fun update(todo: Todo)
    suspend fun delete(todo: Todo)
}