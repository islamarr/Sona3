/*
 * Last modified 8/7/21 1:17 PM
 */

package com.ihsan.sona3.utils.rx

import androidx.lifecycle.LifecycleOwner
import io.reactivex.disposables.Disposable


/**
 *  ~~~~~~~~~~~ This Code For Testing  ~~~~~~~~~~~~~~~
 *   ****** Created By Ahmed Shehatah *********
 *              *_*
 */
interface AutoDispose {

    infix fun Disposable.disposeWith(lifecycleOwner: LifecycleOwner)
}