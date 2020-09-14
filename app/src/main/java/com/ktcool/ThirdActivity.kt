package com.ktcool

import com.ktcool.annotation.Router
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ktcool.R

@Router(path = "/app/thirdActivity")
class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}