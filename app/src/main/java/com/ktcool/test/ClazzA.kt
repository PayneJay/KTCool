package com.ktcool.test

import com.ktcool.common.utils.MyPrint

data class ClazzA(override var prop: Int = 20) : MyInterface {
    override fun method1() {
        val list = listOf("a", "abc", "def")

        for (value in list) {
            MyPrint.print(value)
        }

        for (value in 1..10) {
            MyPrint.print(value)
        }

        for (value in 1..10 step 3) {
            MyPrint.print(value)
        }

        for (value in 15 downTo 1 step 5) {
            MyPrint.print(value)
        }

        for ((index, value) in list.withIndex()) {
            MyPrint.print("index $index: value $value")
        }

        for (value in list.asReversed()) {
            MyPrint.print(value)
        }

        for (i in list.indices) {
            MyPrint.print("$i : ${list[i]}")
        }

        for (i in list.asReversed().indices) {
            MyPrint.print("$i : ${list[i]}")
        }
    }
}