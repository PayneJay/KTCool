package com.ktcool.module_search

import com.ktcool.annotation.Router
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ktcool.common.constant.RouterMap

@Router(path = RouterMap.THIRD_ACTIVITY)
class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}