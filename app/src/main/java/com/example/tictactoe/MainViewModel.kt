package com.example.tictactoe

import androidx.lifecycle.ViewModel
import com.example.tictactoe.models.PlaceholderMark

class MainViewModel : ViewModel() {

    private val _gameData: MutableList<PlaceholderMark> = getInitialGameData()

    /** Represent the current state of game board at any point. */
    val gameData: List<PlaceholderMark> = _gameData

    private fun getInitialGameData() = MutableList(9) { PlaceholderMark.EMPTY }
}