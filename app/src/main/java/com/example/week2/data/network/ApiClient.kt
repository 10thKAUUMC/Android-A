//package com.example.week2.data.network
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//object ApiClient {
//    private const val BASE_URL = "https://reqres.in/"
//
//    private val logging = HttpLoggingInterceptor().apply {
//        level = HttpLoggingInterceptor.Level.BODY
//    }
//
//    private val client = OkHttpClient.Builder()
//        .addInterceptor { chain ->
//            val request = chain.request().newBuilder()
//                .addHeader("x-api-key", "pro_f4af42ee282824b8c9e3eb1bcbe884c6eb87fb1649ba40244f82a65b7fea64ab")
//                .addHeader("X-Reqres-Env", "prod")
//                .build()
//            chain.proceed(request)
//        }
//        .addInterceptor(logging)
//        .build()
//
//    val retrofit: Retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .client(client)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    val service: MyPageService = retrofit.create(MyPageService::class.java)
//}