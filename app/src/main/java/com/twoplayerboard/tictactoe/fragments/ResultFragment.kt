package com.twoplayerboard.tictactoe.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.twoplayerboard.tictactoe.R
import com.twoplayerboard.tictactoe.MainViewModel

/**
 * A simple [Fragment] subclass for game result screen.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultFragment : Fragment(R.layout.fragment_result) {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpInfoViews(view)
        setUpButtonActions(view)
        setUpRecyclerView(view)
    }

    private fun setUpInfoViews(view: View) {
        val imageView = view.findViewById<ImageView>(R.id.result_drawable)
        val resultTextView = view.findViewById<TextView>(R.id.result_string)
        if (viewModel.player1.score == viewModel.player2.score) {
            setImage(imageView, R.mipmap.drawgame)
            resultTextView.text = resources.getString(
                R.string.result_string_draw,
                viewModel.player1.score,
                viewModel.numberOfMatchesInCurrentSession
            )
        } else {
            val winner = if (viewModel.player1.score > viewModel.player2.score) {
                viewModel.player1
            } else {
                viewModel.player2
            }
            setImage(imageView, R.mipmap.winner)
            resultTextView.text = resources.getString(
                R.string.result_string_winner,
                winner.placeHolderMark.name,
                winner.score,
                viewModel.numberOfMatchesInCurrentSession
            )
        }
    }

    private fun setUpButtonActions(view: View) {
        with(view) {
            findViewById<Button>(R.id.exit).setOnClickListener {
                requireActivity().finishAffinity()
            }
            findViewById<Button>(R.id.rematch).setOnClickListener {
                viewModel.resetGameData()
                parentFragmentManager
                    .beginTransaction()
                    .add(R.id.home_screen_container, GameFragment.newInstance())
                    .commitNow()
            }
            findViewById<Button>(R.id.home_screen).setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun setUpRecyclerView(view: View) {
        // TODO(): show the winner of each match here.
    }

    private fun setImage(imageView: ImageView, @DrawableRes imageResId: Int) =
        imageView.setImageDrawable(ContextCompat.getDrawable(requireActivity(), imageResId))


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ResultFragment.
         */
        @JvmStatic
        fun newInstance() = ResultFragment()
    }
}
