//package com.example.chapter1.di
//
//import android.content.Context
//import androidx.room.Room
//import com.example.chapter1.data.local.TodoDao
//import com.example.chapter1.data.local.TodoDatabase
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object DatabaseModule {
//
//    @Provides
//    @Singleton
//    fun provideTodoDatabase(@ApplicationContext context: Context): TodoDatabase {
//        return Room.databaseBuilder(context, TodoDatabase::class.java, "todo_db").build()
//    }
//
//    @Provides
//    fun provideTodoDao(database: TodoDatabase): TodoDao = database.todoDao()
//}