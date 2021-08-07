/*
 * Last modified 8/7/21 1:15 PM
 */

package com.ihsan.sona3.utils.rx

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 *  ~~~~~~~~~~~ This Code For Testing  ~~~~~~~~~~~~~~~
 *   ****** Created By Ahmed Shehatah *********
 *              *_*
 */
class MySchedulers @Inject constructor() {


    fun io(): Scheduler = Schedulers.io()

    fun computation(): Scheduler = Schedulers.computation()

    fun ui(): Scheduler = io.reactivex.android.schedulers.AndroidSchedulers.mainThread()


}