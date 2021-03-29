package com.ihsan.sona3.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class TypeConvertersObject {

    companion object {

        @TypeConverter
        @JvmStatic
        fun stringListToStoredModel(myObjects: List<String>): String {
            val gson = Gson()
            return gson.toJson(myObjects)
        }


    }
}