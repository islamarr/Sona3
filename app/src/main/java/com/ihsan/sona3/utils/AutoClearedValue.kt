/*
 * Last modified 8/7/21 9:16 PM
 */

package com.ihsan.sona3.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 ************* Created by Ahmed Shehatah*********
 * ~~~~~~This Code is For Testing~~~~~~~~~~
 */
class AutoClearedValue<T : Any>(private val activity: AppCompatActivity) :
    ReadWriteProperty<AppCompatActivity, T> {


    private var _tmpValue: T? = null
    private lateinit var observer: DefaultLifecycleObserver

    init {

        observer = object : DefaultLifecycleObserver {

            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)

                _tmpValue = null
                activity.lifecycle.removeObserver(observer)
            }

        }

        activity.lifecycle.addObserver(observer)

    }


    override fun setValue(thisRef: AppCompatActivity, property: KProperty<*>, value: T) {

        _tmpValue = value
    }

    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {


        return _tmpValue
            ?: throw IllegalStateException("should never call auto-cleared-value get when it might not be available")

    }


}

fun <T : Any> AppCompatActivity.autoCleared() = AutoClearedValue<T>(this)