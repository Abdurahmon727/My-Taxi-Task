package com.example.mytaxitask.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.mapbox.geojson.Point


class LocalStorage(
    context: Context
) {
    private val gson = Gson()
    private val pref: SharedPreferences =
        context.getSharedPreferences("LocaleStorage", Context.MODE_PRIVATE)

    var lastSavedLocation: Point?
        get() {
            val json = pref.getString(AppKeys.LOCATION, null)
            return gson.fromJson(json, Point::class.java)
        }
        set(value) {
            val json = gson.toJson(value)
            pref.edit().putString(AppKeys.LOCATION, json).apply()
        }
}


object AppKeys {
    const val LOCATION = "LOCATION"
}
