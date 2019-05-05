package com.dntatme.arkanoid

import android.content.Context
import android.graphics.Rect
import android.media.AudioManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private lateinit var gameView: GameView

class GameFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_game, container, false)
        return gameView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.volumeControlStream = AudioManager.STREAM_MUSIC
    }

    override fun onPause() {
        super.onPause()

        gameView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        gameView.onDestroy()
    }
}