@file:JvmName("DateUtils")

package com.mihryundel.ecwidandroid.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: Date?): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    return dateFormat.format(date)
}