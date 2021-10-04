package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val tag = "Info"
    var secondsElapsed: Int = 0
    lateinit var prefs: SharedPreferences
    private val dataSeconds = "Seconds"
    private val dataApp = "ContinueWatch"


    var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences(dataApp, MODE_PRIVATE)

        if (prefs.contains(dataSeconds)) {
            // Get seconds from prefs
            secondsElapsed = prefs.getInt(dataSeconds, 0)
        }
        backgroundThread.start()
        Log.i(tag, "onCreate")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        // Restore state members from saved instance
        savedInstanceState?.run {
            secondsElapsed = getInt(dataSeconds)
        }
        Log.i(tag, "onRestoreInstanceState")
    }

    override fun onSaveInstanceState(outState: Bundle) {


        // Save the user's current seconds state
        outState.run {
            putInt(dataSeconds, secondsElapsed)
        }
        Log.i(tag, "onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        if (prefs.contains(dataSeconds)) {
            secondsElapsed = prefs.getInt(dataSeconds, 0);
        }
        Log.i(tag, "onStart")
    }

    override fun onStop() {
        super.onStop()
        val editor = prefs.edit()
        editor.putInt(dataSeconds, secondsElapsed)
        editor.apply()
    }

}

