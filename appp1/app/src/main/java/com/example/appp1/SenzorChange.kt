package com.example.appp1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SenzorChange : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_senzor_change)

        findViewById<TextView>(R.id.SenzorChange).apply {
            text= "natančnost se je spremenila" }
    }
}