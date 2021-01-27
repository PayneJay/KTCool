package com.ktcool

import android.content.Context
import com.ktcool.common.network.HttpPoxy
import com.ktcool.common.network.OKHttpMode
import com.ktcool.common.router.MyRouter
import com.ktcool.common.utils.MyPrint
import com.snail.base.BaseApplication


class CoolApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        MyPrint.print("CoolApplication onCreate")
        MyRouter.getInstance().init(this)
        HttpPoxy.getInstance().init(OKHttpMode.getInstance())
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MyPrint.print("CoolApplication attachBaseContext")
    }
}