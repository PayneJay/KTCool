package com.ktcool.test

import com.ktcool.common.utils.MyPrint

class C : InterfaceA {

    var name: String = "null"
        get() = "Peter"

    override fun method1() {
        MyPrint.print("C method1")
        val list = mutableListOf("A", "B", "C", "D")
        list.swap(0, 2)
        list.forEach { MyPrint.print(it) }
    }

    /**
     * 拓展函数
     */
    private fun MutableList<String>.swap(index1: Int, index2: Int) {
        val temp = this[index1]
        this[index1] = this[index2]
        this[index2] = temp
    }
}