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
        val SHOPPING_LIST_KEY = stringPreferencesKey("shopping_list") // Shopping용 추가
        val WISHLIST_KEY = stringPreferencesKey("wishlist") // 위시리스트용 키 추가
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

    /** --- 쇼핑 데이터 관련 (이 부분이 추가되어야 에러가 사라집니다) --- **/
    val shoppingItemsFlow: Flow<List<HomeItem>> = context.dataStore.data
        .map { preferences ->
            val jsonString = preferences[SHOPPING_LIST_KEY] ?: ""
            if (jsonString.isEmpty()) emptyList()
            else {
                val type = object : TypeToken<List<HomeItem>>() {}.type
                gson.fromJson(jsonString, type)
            }
        }

    suspend fun saveShoppingItems(items: List<HomeItem>) {
        context.dataStore.edit { preferences ->
            preferences[SHOPPING_LIST_KEY] = gson.toJson(items)
        }
    }

    /** 3. Wishlist 데이터 관련 **/
    val wishListFlow: Flow<List<HomeItem>> = context.dataStore.data
        .map { preferences ->
            val jsonString = preferences[WISHLIST_KEY] ?: ""
            if (jsonString.isEmpty()) emptyList()
            else {
                val type = object : TypeToken<List<HomeItem>>() {}.type
                gson.fromJson(jsonString, type)
            }
        }

    suspend fun saveWishListItems(items: List<HomeItem>) {
        context.dataStore.edit { preferences ->
            preferences[WISHLIST_KEY] = gson.toJson(items)
        }
    }
}