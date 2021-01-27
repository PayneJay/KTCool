package com.snail.base

import android.app.Application

/**
 * Application基类
 */
open class BaseApplication : Application() {
    companion object {
        lateinit var sApplication: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        sApplication = this;
    }
}