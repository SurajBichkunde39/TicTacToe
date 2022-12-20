package com.twoplayerboard.tictactoe.models

data class Player(
    var placeHolderMark: PlaceholderMark,
    var score: Int = 0,
)

enum class PlaceholderMark {
    X,
    O,
    EMPTY
}
