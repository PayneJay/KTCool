package com.ktcool

import android.app.Application
import android.content.Context
import com.ktcool.common.router.ERouter
import com.ktcool.test.MyPrint

class CoolApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MyPrint.print("CoolApplication onCreate")
        ERouter.instance?.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MyPrint.print("CoolApplication attachBaseContext")
    }
}