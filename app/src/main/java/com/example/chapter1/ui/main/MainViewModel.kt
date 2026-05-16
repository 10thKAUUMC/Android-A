//package com.example.chapter1.ui.main
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.chapter1.data.model.Todo
//import com.example.chapter1.domain.repository.TodoRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class MainViewModel @Inject constructor(
//    private val repository: TodoRepository
//) : ViewModel() {
//
//    private val _uiState = MutableStateFlow(TodoUiState())
//    val uiState: StateFlow<TodoUiState> = _uiState.asStateFlow()
//
//    init {
//        viewModelScope.launch {
//            repository.getTodosStream().collect { todos ->
//                _uiState.update { it.copy(todos = todos) }
//            }
//        }
//    }
//
//    fun addTodo(title: String) {
//        if (title.isBlank()) return
//        viewModelScope.launch {
//            repository.insert(Todo(title = title))
//        }
//    }
//
//    fun toggleComplete(todo: Todo) {
//        viewModelScope.launch {
//            repository.update(todo.copy(isCompleted = !todo.isCompleted))
//        }
//    }
//
//    fun deleteTodo(todo: Todo) {
//        viewModelScope.launch {
//            repository.delete(todo)
//        }
//    }
//}
//
//data class TodoUiState(
//    val todos: List<Todo> = emptyList()
//)