package dev.jam.lifepuzzle20

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.jam.lifepuzzle20.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    private lateinit var mp: MediaPlayer
    private lateinit var win: MediaPlayer
    private lateinit var bgsound: MediaPlayer
    private var playmusic = 0
    private var playsound = 0
    private lateinit var music_off: ImageView
    private lateinit var music_on: ImageView
    private var firstRun = false
    private val PREFS_NAME = "FirstRun"
    private lateinit var pref: SharedPreferences

    private lateinit var settingsButton: ImageView
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = getPreferences(MODE_PRIVATE)

        settingsButton = findViewById(R.id.settings)

        bgsound = MediaPlayer.create(this, R.raw.bg_music)
        bgsound.isLooping = true
        win = MediaPlayer.create(this, R.raw.win)

        pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        firstRun = pref.getBoolean("firstRun", true)

        if (firstRun) {
            playmusic = 1
            with(pref.edit()) {
                putBoolean("firstRun", false)
                apply()
            }
        } else {
            playmusic = pref.getInt("music", 1)
            checkmusic()
        }

        Log.d("MUSIC", playmusic.toString())

        val initialVolume = pref.getInt("volume", 100)
        setVolume(initialVolume)

        binding.btnStart.setOnClickListener {
            startActivity(Intent(this@StartActivity, MainActivity::class.java))
        }

        binding.btnIns.setOnClickListener {
            startActivity(Intent(this@StartActivity, Instructions::class.java))
        }

        binding.Policy.setOnClickListener {
            startActivity(Intent(this@StartActivity, Policy::class.java))
        }

        settingsButton.setOnClickListener {
            if (playsound == 1) {
                mp.start()
            }
            showSettingsDialog()
        }
    }

    private fun showSettingsDialog() {
        val dialog = Dialog(this, R.style.WinDialog)
        dialog.window?.setContentView(R.layout.settings)
        dialog.window?.setGravity(Gravity.CENTER_HORIZONTAL)
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        val tempMusicOn = dialog.findViewById<ImageView>(R.id.music_on)
        tempMusicOn?.setOnClickListener {
            playmusic = 0
            checkmusic()
            tempMusicOn.visibility = View.INVISIBLE
            music_off.visibility = View.VISIBLE
            with(pref.edit()) {
                putInt("music", playmusic)
                apply()
            }
        }
        music_on = tempMusicOn

        val tempMusicOff = dialog.findViewById<ImageView>(R.id.music_off)
        tempMusicOff?.setOnClickListener {
            playmusic = 1
            bgsound.start()
            recreate()
            dialog.show()
            music_on.visibility = View.VISIBLE
            tempMusicOff.visibility = View.INVISIBLE
            with(pref.edit()) {
                putInt("music", playmusic)
                apply()
            }
        }
        music_off = tempMusicOff

        val exitButton = dialog.findViewById<ImageView>(R.id.exit)
        exitButton?.setOnClickListener {
            finishAffinity()
        }

        val closeButton = dialog.findViewById<ImageView>(R.id.close)
        closeButton?.setOnClickListener {
            dialog.dismiss()
        }

        checkmusicdraw()
        dialog.show()
    }

    override fun onPause() {
        super.onPause()
        bgsound.pause()
    }

    override fun onResume() {
        super.onResume()
        checkmusic()
    }

    private fun checkmusic() {
        if (playmusic == 1) {
            bgsound.start()
        } else {
            bgsound.pause()
        }
    }

    private fun checkmusicdraw() {
        if (playmusic == 1) {
            music_on.visibility = View.VISIBLE
            music_off.visibility = View.INVISIBLE
        } else {
            music_on.visibility = View.INVISIBLE
            music_off.visibility = View.VISIBLE
        }
    }

    private fun setVolume(volume: Int) {
        val volumeLevel = volume / 100.0f
        bgsound.setVolume(volumeLevel, volumeLevel)
    }
}
