package com.example.appp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val zagon = findViewById(R.id.zagon) as Button
        zagon.setOnClickListener{
            val startMeasure = Intent(this, Merjenje::class.java)
            startActivity(startMeasure)
        }
    }
}