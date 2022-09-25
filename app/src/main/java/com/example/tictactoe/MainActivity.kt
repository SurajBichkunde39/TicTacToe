package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tictactoe.fragments.HomeScreen

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.home_screen_container, HomeScreen.newInstance())
                .commitNow()
        }
    }
}
