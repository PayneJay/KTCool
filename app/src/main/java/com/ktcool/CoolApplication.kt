package com.ktcool

import android.app.Application
import android.content.Context
import com.ktcool.test.MyPrint

class CoolApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MyPrint.print("CoolApplication onCreate")
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MyPrint.print("CoolApplication attachBaseContext")
    }
}