package com.ktcool.test

class D : InterfaceA, InterfaceB {
    override fun method1() {
        super.method1()
    }

    override fun method2() {
        super<InterfaceA>.method2()
        super<InterfaceB>.method2()
    }

}