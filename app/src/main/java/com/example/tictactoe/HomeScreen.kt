package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.annotation.ColorRes
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import com.example.tictactoe.models.PlaceholderMark
import com.example.tictactoe.models.Player

/**
 * A [Fragment] subclass for home screen.
 * Use the [HomeScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeScreen :
    Fragment(R.layout.fragment_home_screen),
    AdapterView.OnItemSelectedListener {

    private lateinit var player1XButton: Button
    private lateinit var player1OButton: Button
    private lateinit var player2XButton: Button
    private lateinit var player2OButton: Button
    private lateinit var startGameButton: Button

    // TODO(): Move this to viewModel later
    private val player1 = Player(PlaceholderMark.X)
    private val player2 = Player(PlaceholderMark.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            val spinner: Spinner = findViewById(R.id.number_of_matches_selector)
            setUpSpinner(spinner)

            player1XButton = findViewById(R.id.player_1_x_button)
            player1OButton = findViewById(R.id.player_1_o_button)
            player2XButton = findViewById(R.id.player_2_x_button)
            player2OButton = findViewById(R.id.player_2_o_button)
            startGameButton = findViewById(R.id.start_game_button)
            setUpButtonActions()
        }
    }

    private fun setUpSpinner(spinner: Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.number_of_matches_in_session,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun setUpButtonActions() {
        player1XButton.setOnClickListener {
            onPlaceHolderSelected(
                playerNumber = 1,
                selectedPlaceHolder = PlaceholderMark.X
            )
        }
        player1OButton.setOnClickListener {
            onPlaceHolderSelected(
                playerNumber = 1,
                selectedPlaceHolder = PlaceholderMark.O
            )
        }
        player2XButton.setOnClickListener {
            onPlaceHolderSelected(
                playerNumber = 2,
                selectedPlaceHolder = PlaceholderMark.X
            )
        }
        player2OButton.setOnClickListener {
            onPlaceHolderSelected(
                playerNumber = 2,
                selectedPlaceHolder = PlaceholderMark.O
            )
        }
        startGameButton.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.home_screen_container, GameFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun onPlaceHolderSelected(playerNumber: Int, selectedPlaceHolder: PlaceholderMark) {
        if (updateInSelection(playerNumber, selectedPlaceHolder)) {
            return
        }
        when (selectedPlaceHolder) {
            PlaceholderMark.X -> {
                when (playerNumber) {
                    1 -> {
                        updateBackgroundTint(player1XButton, R.color.placeholder_selected)
                        updateBackgroundTint(player1OButton, R.color.placeholder_other)

                        updateBackgroundTint(player2XButton, R.color.placeholder_other)
                        updateBackgroundTint(player2OButton, R.color.placeholder_selected)

                        player1.placeHolderMark = PlaceholderMark.X
                        player2.placeHolderMark = PlaceholderMark.O
                    }
                    2 -> {
                        updateBackgroundTint(player1XButton, R.color.placeholder_other)
                        updateBackgroundTint(player1OButton, R.color.placeholder_selected)

                        updateBackgroundTint(player2XButton, R.color.placeholder_selected)
                        updateBackgroundTint(player2OButton, R.color.placeholder_other)

                        player1.placeHolderMark = PlaceholderMark.O
                        player2.placeHolderMark = PlaceholderMark.X
                    }
                    else -> {
                        // something went wrong
                    }
                }
            }
            PlaceholderMark.O -> {
                when (playerNumber) {
                    1 -> {
                        updateBackgroundTint(player1XButton, R.color.placeholder_other)
                        updateBackgroundTint(player1OButton, R.color.placeholder_selected)

                        updateBackgroundTint(player2XButton, R.color.placeholder_selected)
                        updateBackgroundTint(player2OButton, R.color.placeholder_other)

                        player1.placeHolderMark = PlaceholderMark.O
                        player2.placeHolderMark = PlaceholderMark.X
                    }
                    2 -> {
                        updateBackgroundTint(player1XButton, R.color.placeholder_selected)
                        updateBackgroundTint(player1OButton, R.color.placeholder_other)

                        updateBackgroundTint(player2XButton, R.color.placeholder_other)
                        updateBackgroundTint(player2OButton, R.color.placeholder_selected)

                        player1.placeHolderMark = PlaceholderMark.X
                        player2.placeHolderMark = PlaceholderMark.O
                    }
                    else -> {
                        // something went wrong
                    }
                }
            }
            else -> {
                // can not choose empty
            }
        }
    }

    private fun updateBackgroundTint(button: Button, @ColorRes colorId: Int) {
        // TODO(): Use the correct way to update the background tint.
        var buttonDrawable = button.background
        buttonDrawable = DrawableCompat.wrap(buttonDrawable)
        DrawableCompat.setTint(buttonDrawable, resources.getColor(colorId))
        button.background = buttonDrawable
    }

    private fun updateInSelection(
        playerNumber: Int,
        selectedPlaceHolder: PlaceholderMark
    ): Boolean {
        return (playerNumber == 1 && selectedPlaceHolder == player1.placeHolderMark)
                || (playerNumber == 2 && selectedPlaceHolder == player2.placeHolderMark)
    }

    // start: AdapterView.OnItemSelectedListener
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
    // end

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomeScreen.
         */
        @JvmStatic
        fun newInstance() = HomeScreen()
    }
}
