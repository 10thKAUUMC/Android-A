package com.example.week2

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// 파일 최상단에 DataStore 싱글톤 생성 (메모리 누수 방지)
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "nike_prefs")

class ProductManager(private val context: Context) {
    private val gson = Gson()
    private val PRODUCTS_KEY = stringPreferencesKey("products_list")

    // 1. 상품 리스트 저장 (객체 리스트 -> JSON 문자열 -> DataStore)
    suspend fun saveProducts(products: List<ProductData>) {
        val jsonString = gson.toJson(products)
        context.dataStore.edit { preferences ->
            preferences[PRODUCTS_KEY] = jsonString
        }
    }

    // 2. 상품 리스트 불러오기 (DataStore -> JSON 문자열 -> 객체 리스트)
    fun getProducts(): Flow<List<ProductData>> {
        return context.dataStore.data.map { preferences ->
            val jsonString = preferences[PRODUCTS_KEY] ?: ""
            if (jsonString.isEmpty()) {
                emptyList() // 저장된 게 없으면 빈 리스트 반환
            } else {
                val type = object : TypeToken<List<ProductData>>() {}.type
                gson.fromJson(jsonString, type)
            }
        }
    }
}