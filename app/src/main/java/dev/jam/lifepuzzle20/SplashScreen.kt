package dev.jam.lifepuzzle20

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception

class SplashScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val videoView = findViewById<VideoView>(R.id.videoView)
        videoView.setVideoURI(Uri.parse("android.resource://$packageName/${R.raw.recallsplash}"))
        videoView.start()

        val intent = Intent(this, StartActivity::class.java)
        startIntent(intent)
    }

    private fun startIntent(intent: Intent) {
        try {
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
