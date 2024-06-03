package com.yoenas.navigation

import android.content.Context
import android.content.Intent

fun Context.intentTo(className: String, key: String? = "", value: String? = "") {
    val clazz = Class.forName(className)
    val intent = Intent(this, clazz)
    intent.putExtra(key, value)
    startActivity(intent)
}