package com.example.tictactoe

import androidx.lifecycle.ViewModel
import com.example.tictactoe.models.BoardManager
import com.example.tictactoe.models.PlaceholderMark
import com.example.tictactoe.models.Player

class MainViewModel : ViewModel() {

    private val _gameData: MutableList<PlaceholderMark> = getInitialGameData()
    /** Represent the current state of game board at any point. */
    val gameData: List<PlaceholderMark> = _gameData

    val player1 = Player(PlaceholderMark.X)
    val player2 = Player(PlaceholderMark.O)
    var currentPlayer = player1
        private set

    private var numberOfMatchesInCurrentSession = 0
    private var currentMatchNumber =  0

    private lateinit var boardManager: BoardManager

    /**
     * sets the board manager host.
     */
    fun setBoardManager(host: BoardManager) {
        boardManager = host
    }

    /**
     * Updates the placeholder marks for player.
     * @param placeholderMark1 is placeholder mark for fist player.
     * @param placeholderMark2 is placeholder mark for second player.
     */
    fun updatePlayers(placeholderMark1: PlaceholderMark, placeholderMark2: PlaceholderMark) {
        player1.placeHolderMark = placeholderMark1
        player2.placeHolderMark = placeholderMark2
    }

    /**
     * sets the number of matches in current game session.
     * @param matches is number of matches to set.
     */
    fun setNumberOfMatchesInCurrentSession(matches: Int) {
        numberOfMatchesInCurrentSession = matches
    }

    private fun isPositionValid(position: Int) = position < _gameData.size && _gameData[position] == PlaceholderMark.EMPTY

    private fun getInitialGameData() = MutableList(9) { PlaceholderMark.EMPTY }
}
