package com.example.chapter1

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
// 1. 확장 프로퍼티로 DataStore 생성 (이름 지정)
val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsManager(private val context: Context) {
    private val gson = Gson()

    companion object {
        val SHOE_LIST_KEY = stringPreferencesKey("shoe_list")
    }

    // 리스트 저장 함수
    suspend fun saveHomeItems(items: List<HomeItem>) {
        val jsonString = gson.toJson(items) // 리스트를 JSON 문자열로 변환
        context.dataStore.edit { preferences ->
            preferences[SHOE_LIST_KEY] = jsonString
        }
    }

    // 리스트 읽기 함수 (Flow)
    val homeItemsFlow: Flow<List<HomeItem>> = context.dataStore.data
        .map { preferences ->
            val jsonString = preferences[SHOE_LIST_KEY] ?: ""
            if (jsonString.isEmpty()) {
                emptyList() // 데이터가 없으면 빈 리스트 반환
            } else {
                val type = object : TypeToken<List<HomeItem>>() {}.type
                gson.fromJson(jsonString, type) // JSON을 다시 리스트 객체로 변환
            }
        }
}