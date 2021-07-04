package com.ihsan.sona3.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.ihsan.sona3.MyApp

class Sona3Parameters {
    var sona3Settings: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    @SuppressLint("CommitPrefEdits")
    private fun openConnection() {
        sona3Settings = MyApp.appContext.getSharedPreferences(
            APP_PREFERENCES,
            Context.MODE_PRIVATE
        )
        editor = sona3Settings!!.edit()
    }

    private fun closeConnection() {
        editor = null
        sona3Settings = null
    }

    fun setInt(key: String?, value: Int) {
        openConnection()
        editor!!.putInt(key, value)
        editor!!.commit()
        closeConnection()
    }

    fun setLoong(key: String?, value: Long) {
        openConnection()
        editor!!.putLong(key, value)
        editor!!.commit()
        closeConnection()
    }

    fun setFloat(key: String?, value: Float) {
        openConnection()
        editor!!.putFloat(key, value)
        editor!!.commit()
        closeConnection()
    }

    fun setDouble(key: String?, value: Double) {
        openConnection()
        editor!!.putLong(key, java.lang.Double.doubleToRawLongBits(value))
        editor!!.commit()
        closeConnection()
    }

    fun setString(key: String?, value: String?) {
        openConnection()
        editor!!.putString(key, value)
        editor!!.commit()
        closeConnection()
    }

    fun removeValue(key: String?) {
        openConnection()
        editor!!.remove(key)
        editor!!.apply()
        closeConnection()
    }

    fun setBoolean(key: String?, value: Boolean?) {
        openConnection()
        editor!!.putBoolean(key, value!!)
        editor!!.commit()
        closeConnection()
    }

    fun getBoolean(key: String?, defValue: Boolean): Boolean {
        var result = defValue
        openConnection()
        if (sona3Settings!!.contains(key)) {
            result = sona3Settings!!.getBoolean(key, defValue)
        }
        closeConnection()
        return result
    }

    fun getInt(key: String?): Int {
        var result = 0
        openConnection()
        if (sona3Settings?.contains(key) == true) {
            result = sona3Settings!!.getInt(key, 0)
        }
        closeConnection()
        return result
    }

    fun getInt(key: String?, defValue: Int): Int {
        var result = defValue
        openConnection()
        if (sona3Settings?.contains(key) == true) {
            result = sona3Settings!!.getInt(key, defValue)
        }
        closeConnection()
        return result
    }

    fun getFloat(key: String?): Float {
        var result = 0f
        openConnection()
        if (sona3Settings!!.contains(key)) {
            result = sona3Settings!!.getFloat(key, 0f)
        }
        closeConnection()
        return result
    }

    fun getDouble(key: String?): Double {
        var result = 0.0
        openConnection()
        if (sona3Settings!!.contains(key)) {
            result = java.lang.Double.longBitsToDouble(sona3Settings!!.getLong(key, java.lang.Double.doubleToRawLongBits(0.0)))
        }
        closeConnection()
        return result
    }

    fun getString(key: String?): String? {
        var result: String? = ""
        openConnection()
        if (sona3Settings?.contains(key) == true) {
            result = sona3Settings?.getString(key, "")
        }
        closeConnection()
        return result
    }

    fun getString(key: String?, strdefault: String?): String? {
        var result = strdefault
        openConnection()
        if (sona3Settings?.contains(key) == true) {
            result = sona3Settings?.getString(key, strdefault)
        }
        closeConnection()
        return result
    }

    fun getLoong(key: String?, defValue: Long): Long {
        var result = defValue
        openConnection()
        if (sona3Settings!!.contains(key)) {
            result = sona3Settings!!.getLong(key, defValue)
        } else {
            setLoong(key, defValue)
        }
        closeConnection()
        return result
    }

    companion object {
        const val APP_PREFERENCES = "Sona3ProPrefs"
    }
}