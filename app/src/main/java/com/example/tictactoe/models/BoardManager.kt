package com.example.tictactoe.models

/**
 * Interface to manage the actions on the board.
 */
interface BoardManager {
    /**
     * Inform player the current player is changes, this can happen because of the valid move,
     * or at initial position.
     *
     * @param currentPlayer updated current [Player]
     */
    fun onCurrentPlayerUpdated(currentPlayer: Player)

    /**
     * Update the board as per new position selected.
     * Implementation will assume the position is valid and update the underline place with provided mark.
     *
     * @param position in game board to update.
     * @param placeholderMark new [PlaceholderMark] for the position.
     */
    fun onPositionSelected(position: Int, placeholderMark: PlaceholderMark)

    /**
     * Inform player, invalid position is selected
     *
     * @param placeholderMark [PlaceholderMark] of the player who selected invalid position.
     */
    fun onInvalidPositionSelected(placeholderMark: PlaceholderMark)

    /**
     * Inform players that match is draw nobody wins.
     */
    fun onDraw()

    /**
     * Inform players that match is over and which player won the game
     *
     * @param player [Player] who won the match.
     */
    fun onWin(player: Player)

}