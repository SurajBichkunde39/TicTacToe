package com.example.tictactoe

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.fragments.HomeScreen

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO(https://github.com/SurajBichkunde39/TicTacToe/issues/35): This is bad user experience. Update ASAP.
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.home_screen_container, HomeScreen.newInstance())
                .commitNow()
        }
    }
}
