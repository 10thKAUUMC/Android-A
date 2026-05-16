//package com.example.chapter1.data.repository
//
//import com.example.chapter1.data.local.TodoDao
//import com.example.chapter1.data.model.Todo
//import com.example.chapter1.domain.repository.TodoRepository
//import kotlinx.coroutines.flow.Flow
//import javax.inject.Inject
//
//class TodoRepositoryImpl @Inject constructor(  // ← Hilt가 TodoDao를 자동으로 주입
//    private val todoDao: TodoDao               // 반드시 @Inject가 붙어야 합니다!
//) : TodoRepository {
//    override fun getTodosStream(): Flow<List<Todo>> = todoDao.getAllTodosFlow()
//    override suspend fun insert(todo: Todo) = todoDao.insert(todo)
//    override suspend fun update(todo: Todo) = todoDao.update(todo)
//    override suspend fun delete(todo: Todo) = todoDao.delete(todo)
//}