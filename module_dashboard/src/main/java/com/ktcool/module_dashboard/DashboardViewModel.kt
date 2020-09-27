package com.ktcool.module_dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun requestServer() {
//        HttpPoxy.getInstance().get(
//            "https://easy-mock.com/mock/5f18f3a02da6de5e62cedb35/example/query",
//            null,
//            object : ICallback<TestBean?> {
//                override fun onFailure(msg: String) {
//                    MyPrint.Companion.print("onFailure : $msg")
//                }
//
//                override fun onSuccess(response: TestBean?) {
//                    TODO("Not yet implemented")
//                }
//            },
//            TestBean::class.java!
//        )
    }
}

