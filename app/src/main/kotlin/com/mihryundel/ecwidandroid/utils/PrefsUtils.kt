@file:JvmName("PrefsUtils")

package com.mihryundel.ecwidandroid.utils

import android.content.Context
import com.mihryundel.ecwidandroid.EcwidAndroidApplication

private val prefs by lazy {
    EcwidAndroidApplication.context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
}

fun getProductsSortMethodName(defaultValue: String): String = prefs.getString("sort_method", defaultValue)

fun setProductsSortMethod(sortMethod: String) {
    prefs.edit().putString("sort_method", sortMethod).apply()
}
