package com.app.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.app.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isRunning = false
    private var timerSeconds = 0

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            timerSeconds++
            val hours = timerSeconds / 3600
            val minutes = (timerSeconds % 3600) / 60
            val seconds = timerSeconds % 60

            val time = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            binding.timerText.text = time

            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Button click listeners
        binding.startbtn.setOnClickListener {
            startTimer()
        }

        binding.stopbtn.setOnClickListener {
            stopTimer()
        }

        binding.resetbtn.setOnClickListener {
            resetTimer()
        }

        // Initial button states
        binding.startbtn.isEnabled = true
        binding.stopbtn.isEnabled = false
        binding.resetbtn.isEnabled = false
    }

    private fun startTimer() {
        if (!isRunning) {
            isRunning = true
            handler.post(runnable)

            binding.startbtn.isEnabled = false
            binding.stopbtn.isEnabled = true
            binding.resetbtn.isEnabled = true
        }
    }

    private fun stopTimer() {
        if (isRunning) {
            isRunning = false
            handler.removeCallbacks(runnable)

            binding.startbtn.isEnabled = true
            binding.startbtn.text = "Resume"
            binding.stopbtn.isEnabled = false
        }
    }

    private fun resetTimer() {
        stopTimer()
        timerSeconds = 0
        binding.timerText.text = "00:00:00"

        binding.startbtn.text = "Start"
        binding.startbtn.isEnabled = true
        binding.resetbtn.isEnabled = false
        binding.stopbtn.isEnabled = false
    }
}
