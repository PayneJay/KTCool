package com.ktcool

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ktcool.annotation.Router

@Router(path = "/app/secondKT")
class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

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