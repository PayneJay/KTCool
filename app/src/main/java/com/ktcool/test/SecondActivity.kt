package com.ktcool.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    private val C.length: Int
        get() {
            return name.length
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val c = C()
        c.method1()
        c.method2()
        MyPrint.print(c.name + " : " + c.length)

        val d = D()
        d.method1()
        d.method2()
    }
}