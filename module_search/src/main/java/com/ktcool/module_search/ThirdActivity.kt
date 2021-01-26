package com.ktcool.module_search

import android.os.Bundle
import com.ktcool.annotation.Router
import com.ktcool.common.constant.RouterMap
import com.snail.base.BaseActivity

@Router(path = RouterMap.THIRD_ACTIVITY)
class ThirdActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_layout)
    }
}