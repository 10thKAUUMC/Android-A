//package com.example.chapter1.data.local
//
//import androidx.room.*
//import com.example.chapter1.data.model.Todo
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface TodoDao {
//
//    @Query("SELECT * FROM todos ORDER BY createdAt DESC")
//    fun getAllTodosFlow(): Flow<List<Todo>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(todo: Todo)
//
//    @Update
//    suspend fun update(todo: Todo)
//
//    @Delete
//    suspend fun delete(todo: Todo)
//}