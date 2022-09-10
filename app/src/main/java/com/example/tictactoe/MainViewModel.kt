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

    /**
     * Find the correct action that should be performed on the selected position.
     * Forwards the same to [BoardManager]
     *
     * @param position selected by current [Player] on board
     */
    fun onPositionSelected(position: Int) {
        if (isPositionValid(position)) {
            _gameData[position] = currentPlayer.placeHolderMark
            boardManager.onPositionSelected(position, currentPlayer.placeHolderMark)
            checkTermination()
        } else {
            boardManager.onInvalidPositionSelected(currentPlayer.placeHolderMark)
        }
    }

    private fun checkTermination() {
        if (isGameOver()) {
            checkWinOrDraw()
        } else {
            switchCurrentPlayer()
        }
    }

    private fun isGameOver(): Boolean {
        val emptyPosition = _gameData.firstOrNull { it == PlaceholderMark.EMPTY }
        return emptyPosition == null
    }

    private fun checkWinOrDraw() {
        if(isWin()){
            boardManager.onWin(currentPlayer)
        } else {
            boardManager.onDraw()
        }
    }

    private fun isWin(): Boolean {
        // TODO(): Add logic to check if last move won the game.
        return false
    }

    private fun isPositionValid(position: Int) =
        position < _gameData.size && _gameData[position] == PlaceholderMark.EMPTY

    private fun switchCurrentPlayer() {
        currentPlayer = if (currentPlayer == player1) {
            player2
        } else {
            player1
        }
    }

    private fun getInitialGameData() = MutableList(9) { PlaceholderMark.EMPTY }
}
