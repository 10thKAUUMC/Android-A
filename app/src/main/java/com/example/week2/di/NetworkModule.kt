package com.example.week2.di

import com.example.week2.data.network.ApiClient
import com.example.week2.data.network.MyPageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMyPageService(): MyPageService = ApiClient.service
}
