package com.ktcool

import android.app.Application
import android.content.Context
import android.os.StrictMode
import com.ktcool.test.MyPrint
import com.squareup.leakcanary.AndroidRefWatcherBuilder
import com.squareup.leakcanary.LeakCanary

class CoolApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MyPrint.print("CoolApplication onCreate")
        /**
         * 2.0之前的用法，需要在Application中初始化（侵入型），记得在manifest中注册HeapAnalyzerService
        <service
        android:name=".internal.HeapAnalyzerService"
        android:process=":leakcanary"
        android:enabled="false"/>
         */
        setupLeakCanary()
    }

    private fun setupLeakCanary() {
        enableStrictMode()
        //判断是否是HeapAnalyzerService所在的进程
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        LeakCanary.install(this)
    }

    /**
     * 是否开启严格模式
     */
    private fun enableStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyDeath()
                .penaltyLog()
                .build()
        )
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MyPrint.print("CoolApplication attachBaseContext")
    }
}