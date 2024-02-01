package dev.jam.lifepuzzle20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class Instructions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions)

        val btnBack: ImageView = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {

            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
