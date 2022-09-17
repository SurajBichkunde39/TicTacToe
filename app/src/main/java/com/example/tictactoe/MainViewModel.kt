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
    private var currentMatchSession = 1

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
            checkWinOrDraw()
        } else {
            boardManager.onInvalidPositionSelected(currentPlayer.placeHolderMark)
        }
    }

    /** Rests the game board data.*/
    fun resetGameData() {
        for (i in 0..8) {
            _gameData[i] = PlaceholderMark.EMPTY
        }
    }

    /** If game is over, informs the board manager.*/
    fun checkForGameOver() {
        if (currentMatchSession >= numberOfMatchesInCurrentSession) {
            val winner = if (player1.score > player2.score) {
                player1
            } else {
                player2
            }
            boardManager.onGameOver(winner)
        }
        currentMatchSession += 1
    }

    private fun checkWinOrDraw() {
        when {
            isWin() -> {
                currentPlayer.score += 1
                boardManager.onWin(currentPlayer)
            }
            isGameOver() -> boardManager.onDraw()
            else -> switchCurrentPlayer()
        }
    }

    private fun isGameOver(): Boolean {
        val emptyPosition = _gameData.firstOrNull { it == PlaceholderMark.EMPTY }
        return emptyPosition == null
    }

    private fun isWin(): Boolean {
        return rowWiseWin() || columnWiseWin() || diagonalWin()
    }

    private fun rowWiseWin(): Boolean {
        for (i in 0..8 step 3) {
            var isWin = true
            for (j in i..(i + 2)) {
                if (_gameData[j] != currentPlayer.placeHolderMark) {
                    isWin = false
                    break
                }
            }
            if (isWin)
                return true
        }
        return false
    }

    private fun columnWiseWin(): Boolean {
        for (i in 0..2) {
            var ind = 0
            var isWin = true
            for (j in 0..2) {
                if (_gameData[ind + i] != currentPlayer.placeHolderMark) {
                    isWin = false
                    break
                }
                ind += 3
            }
            if (isWin)
                return true
        }
        return false
    }

    private fun diagonalWin(): Boolean {
        var isWin = true
        for (i in 0..2) {
            if (_gameData[i * 4] != currentPlayer.placeHolderMark) {
                isWin = false
                break
            }
        }
        if (isWin)
            return true

        isWin = true
        for (i in 2..6 step 2) {
            if (_gameData[i] != currentPlayer.placeHolderMark) {
                isWin = false
                break
            }
        }
        return isWin
    }

    private fun isPositionValid(position: Int) =
        position < _gameData.size && _gameData[position] == PlaceholderMark.EMPTY

    private fun switchCurrentPlayer() {
        currentPlayer = if (currentPlayer == player1) {
            boardManager.onCurrentPlayerUpdated(currentPlayerNumber = 2)
            player2
        } else {
            boardManager.onCurrentPlayerUpdated(currentPlayerNumber = 1)
            player1
        }
    }

    private fun getInitialGameData() = MutableList(9) { PlaceholderMark.EMPTY }
}
