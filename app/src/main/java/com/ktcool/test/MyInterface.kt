package com.ktcool.test

import com.ktcool.common.utils.MyPrint

interface MyInterface {
    val prop: Int
    val propStr: String
        get() = "foo"

    fun method1()

    fun method2(): String {
        MyPrint.print("call method2 $propStr")
        return "hello world"
    }
}