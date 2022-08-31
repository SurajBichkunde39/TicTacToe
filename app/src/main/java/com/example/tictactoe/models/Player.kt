package com.example.tictactoe.models

data class Player(
    var placeHolderMark: PlaceholderMark,
    val score: Int = 0,
)

enum class PlaceholderMark {
    X,
    O
}
