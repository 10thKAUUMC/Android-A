//package com.example.week2.di
//
//import com.example.week2.data.repository.ProductRepositoryImpl
//import com.example.week2.data.repository.UserRepositoryImpl
//import com.example.week2.domain.repository.ProductRepository
//import com.example.week2.domain.repository.UserRepository
//import dagger.Binds
//import dagger.Module
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//abstract class RepositoryModule {
//
//    @Binds
//    @Singleton
//    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository
//
//    @Binds
//    @Singleton
//    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
//}
