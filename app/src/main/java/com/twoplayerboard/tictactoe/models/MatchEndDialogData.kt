package com.twoplayerboard.tictactoe.models

data class MatchEndDialogData(
    val isWin: Boolean,
    val text: String,
    val playerPlaceholderMark: PlaceholderMark
)
