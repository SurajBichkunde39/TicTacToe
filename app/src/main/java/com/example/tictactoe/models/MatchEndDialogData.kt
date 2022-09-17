package com.example.tictactoe.models

data class MatchEndDialogData(
    val isWin: Boolean,
    val text: String,
    val playerPlaceholderMark: PlaceholderMark
)
