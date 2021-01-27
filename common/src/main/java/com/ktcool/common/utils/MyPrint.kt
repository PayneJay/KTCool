package com.ktcool.common.utils

import android.util.Log

class MyPrint {
    companion object {
        fun print(msg: Any?) {
            LogUtil.i("Leather Log : ${msg.toString()}")
        }
    }
}