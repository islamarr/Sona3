/*
 * Last modified 7/7/21 12:41 AM
 */

/*
 * Last modified 7/6/21 10:05 PM
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
//    private var binding: SplashFragmentBinding = SplashFragmentBinding.inflate(LayoutInflater.from(context))

    init {

        if (attrs != null) {

            val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.CustomTextViewWithTypeFace)
            val fontName =
                typedArray.getString(R.styleable.CustomTextViewWithTypeFace_typeface)
            try {
                if (fontName != null) {
                    val typeface = Typeface.createFromAsset(context.assets, "fonts/$fontName.ttf")
                    setTypeface(typeface)
                }
            } catch (ex: Exception) {
                context.toast(ex.message.toString())
            }
            typedArray.recycle()
        }
    }
}