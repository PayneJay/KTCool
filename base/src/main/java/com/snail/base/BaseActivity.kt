package com.snail.base

import android.os.Build
import androidx.appcompat.app.AppCompatActivity

/**
 * 基类Activity
 */
open class BaseActivity : AppCompatActivity() {

    override fun onBackPressed() {
        //以下是处理LeakCanary上报的一个Library Leak
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }
}