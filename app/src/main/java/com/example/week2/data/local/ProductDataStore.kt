//package com.example.week2.data.local
//
//import android.content.Context
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.core.stringPreferencesKey
//import androidx.datastore.preferences.preferencesDataStore
//import com.example.week2.ProductData
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import dagger.hilt.android.qualifiers.ApplicationContext
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//import javax.inject.Inject
//import javax.inject.Singleton
//
//private val Context.productDataStore by preferencesDataStore(name = "week2_products")
//
//@Singleton
//class ProductDataStore @Inject constructor(
//    @ApplicationContext private val context: Context
//) {
//    private val gson = Gson()
//    private val PRODUCT_LIST_KEY = stringPreferencesKey("product_list")
//
//    val productsFlow: Flow<List<ProductData>> = context.productDataStore.data
//        .map { preferences ->
//            val json = preferences[PRODUCT_LIST_KEY] ?: ""
//            if (json.isEmpty()) emptyList()
//            else {
//                val type = object : TypeToken<List<ProductData>>() {}.type
//                gson.fromJson(json, type)
//            }
//        }
//
//    suspend fun saveProducts(products: List<ProductData>) {
//        context.productDataStore.edit { preferences ->
//            preferences[PRODUCT_LIST_KEY] = gson.toJson(products)
//        }
//    }
//}
