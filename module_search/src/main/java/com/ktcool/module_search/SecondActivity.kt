package com.ktcool.module_search

import android.os.Bundle
import android.widget.Toast
import com.ktcool.annotation.Router
import com.ktcool.common.constant.RouterMap
import com.snail.base.BaseActivity

@Router(path = RouterMap.SECOND_ACTIVITY)
class SecondActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_layout)

        val stringExtra = intent.getStringExtra("String")
        val intExtra = intent.getIntExtra("Integer", 0)
        val booleanExtra = intent.getBooleanExtra("Boolean", false)
        Toast.makeText(
            this,
            stringExtra!! + "\n" + intExtra + "\n" + booleanExtra,
            Toast.LENGTH_SHORT
        ).show()
    }
}