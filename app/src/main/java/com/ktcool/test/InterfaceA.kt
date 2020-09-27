package com.ktcool.test

import com.ktcool.common.utils.MyPrint

interface InterfaceA {
    fun method1()
    fun method2() {
        MyPrint.print("A method2")
    }
}