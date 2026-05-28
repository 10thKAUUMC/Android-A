package com.example.umc3.data

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateListOf
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object WishlistRepository {

    private const val PREFS_NAME = "wishlist_prefs"
    private const val KEY_WISHLIST = "wishlist_items"

    val items = mutableStateListOf<Product>()
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_WISHLIST, null)
        if (json != null) {
            val saved = Json.decodeFromString<List<Product>>(json)
            items.addAll(saved)
        }
    }

    private fun save() {
        prefs.edit().putString(KEY_WISHLIST, Json.encodeToString(items.toList())).apply()
    }

    fun isWished(product: Product): Boolean =
        items.any { it.title == product.title }

    fun toggle(product: Product) {
        val index = items.indexOfFirst { it.title == product.title }
        if (index >= 0) {
            items.removeAt(index)
        } else {
            items.add(product)
        }
        save()
    }
}
