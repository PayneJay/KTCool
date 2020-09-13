package com.ktcool.common.router

import android.app.Application
import android.content.Intent
import android.os.Bundle
import java.util.*

class ERouter private constructor() {
    //存放路由地址额映射表
    private val routerMap: MutableMap<String, Class<*>> = HashMap()
    private var context: Application? = null

    /**
     * 路由初始化
     */
    fun init(context: Application?) {
        this.context = context

        context?.let {
            val classes =
                ClassUtils.getFileNameByPackageName(it, PACKAGE_NAME)

            if (classes.isNotEmpty()) {
                for (classStr in classes) {
                    val classConstructor = Class.forName(classStr).getConstructor()
                    val newInstance = classConstructor.newInstance()
                    if (newInstance is IRouter) {
                        newInstance.loadInto()
                    }
                }
            }
        }
    }

    /**
     * 添加路由地址到映射表
     *
     * @param key   路由地址
     * @param value 对应的类
     */
    fun addRouter(key: String?, value: Class<*>) {
        if (!routerMap.containsKey(key) && !key.isNullOrEmpty()) {
            routerMap[key] = value
        }
    }

    /**
     * Activity跳转
     */
    fun navigation(key: String, bundle: Bundle?) {
        if (routerMap.containsKey(key)) {
            val aClass = routerMap[key]!!
            if (context != null) {
                val intent = Intent(context, aClass)
                if (bundle != null) {
                    intent.putExtra(key, bundle)
                }
                context!!.startActivity(intent)
            }
        }
    }

    companion object {
        private const val PACKAGE_NAME: String = "com.ktcool"

        @Volatile
        var instance: ERouter? = null
            get() {
                if (field == null) {
                    synchronized(ERouter::class.java) {
                        if (field == null) {
                            field = ERouter()
                        }
                    }
                }
                return field
            }
            private set
    }
}