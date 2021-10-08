/*
 * Last modified 6/15/21 4:26 PM
 */

package com.ihsan.sona3.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import java.io.ByteArrayOutputStream

/**
 * Created by (Ameen Essa) on 6/15/2021
 * Copyright (c) 2021 . All rights reserved.
 * @Ameen.MobileDev@gmail.com
 */

enum class UserRoleEnum {
    Editor,
    Researcher,
    Reviewer,
    Supervisor
}

enum class SharedKeyEnum {
    TOKEN,
    FIRST_LOGIN
}

fun convertToStringBase64(context: Context, selectedfile: Uri): String {
    val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, selectedfile)

    val bitmapOut = Bitmap.createScaledBitmap(bitmap, 320, 480, false)

    val outputStream = ByteArrayOutputStream()
    bitmapOut.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    val byteArray = outputStream.toByteArray()

    //Use your Base64 String as you wish
    val encodedString = Base64.encodeToString(byteArray, Base64.DEFAULT)

    val encodedWithdata = "data:image/png;base64,$encodedString"

    return encodedString
}

fun getToken(): String {
    val token = Sona3Preferences().getString(SharedKeyEnum.TOKEN.toString())
    return "Token $token"
}