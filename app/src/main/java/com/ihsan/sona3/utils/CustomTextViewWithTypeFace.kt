/*
 * Last modified 8/7/21 3:13 PM
 */

/*
 * Last modified 7/15/21 5:24 PM
 */


/**
 * Created by Ahmed Shehatah
 *
 *
 */
package com.ihsan.sona3.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.ihsan.sona3.R


class CustomTextViewWithTypeFace @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {

        if (attrs != null) {

            val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.CustomTextViewWithTypeFace)
            val fontName =
                typedArray.getString(R.styleable.CustomTextViewWithTypeFace_typeface)
            try {
                if (fontName != null) {
                    val typeface = Typeface.createFromAsset(context.assets, "fonts/$fontName")
                    setTypeface(typeface)
                }
            } catch (ex: Exception) {
                context.toast(ex.message.toString())
            }
            typedArray.recycle()
        }
    }
}