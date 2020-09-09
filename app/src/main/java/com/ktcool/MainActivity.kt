package com.ktcool

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var liveData: MutableLiveData<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        liveData = MutableLiveData()
        liveData.observe(this, {
            Log.d("leather", "value = $it, 当前LifeCycle的状态为 ${lifecycle.currentState}")
        })

        liveData.value = "onCreate"

//        startActivity(Intent(this, SecondActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        liveData.value = "onStart"
    }

    override fun onRestart() {
        super.onRestart()
        liveData.value = "onRestart"
    }

    override fun onResume() {
        super.onResume()
        liveData.value = "onResume"
    }

    override fun onPause() {
        super.onPause()
        liveData.value = "onPause"
    }

    override fun onStop() {
        super.onStop()
        liveData.value = "onStop"
    }

    override fun onDestroy() {
        super.onDestroy()
        liveData.value = "onDestroy"
    }
}