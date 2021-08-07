/*
 * Last modified 8/7/21 1:18 PM
 */

package com.ihsan.sona3.utils.rx

import androidx.lifecycle.*
import io.reactivex.disposables.Disposable

/**
 *  ~~~~~~~~~~~ This Code For Testing  ~~~~~~~~~~~~~~~
 *   ****** Created By Ahmed Shehatah *********
 *              *_*
 */
class MyDisposable : AutoDispose {

    private lateinit var observer: LifecycleObserver
    override fun Disposable.disposeWith(lifecycleOwner: LifecycleOwner) {

        observer = object : LifecycleObserver {


            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onStop() {

                this@disposeWith.dispose()
                lifecycleOwner.lifecycle.removeObserver(observer)


            }


        }

        lifecycleOwner.lifecycle.addObserver(observer)

    }


}