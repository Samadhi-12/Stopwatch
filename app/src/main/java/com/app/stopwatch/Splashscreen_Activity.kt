package com.app.stopwatch

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class Splashscreen_Activity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_splashscreen)

        playSoundEffect()

        // Navigate to main screen after delay (3 seconds)
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToMain()
        }, 3000)  // Delay in milliseconds (3 seconds)
    }

    private fun playSoundEffect() {
        // Create and start the media player
        mediaPlayer = MediaPlayer.create(this, R.raw.sound)
        mediaPlayer.start()

        // Release resources when sound completes
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release media player to avoid memory leaks
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}