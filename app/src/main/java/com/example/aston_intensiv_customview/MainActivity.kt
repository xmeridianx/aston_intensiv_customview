package com.example.aston_intensiv_customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val customView: CustomView = findViewById(R.id.customView)
        val buttonStart: Button = findViewById(R.id.buttonStart)
        val buttonReset: Button = findViewById(R.id.buttonReset)
        val textViewCustom = findViewById<TextView>(R.id.textViewCustom)
        val imageViewCustom = findViewById<ImageView>(R.id.imageViewCustom)
        val seekbar = findViewById<SeekBar>(R.id.seekbar)

        seekbar.progress = 50
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val newSize = progress.toFloat() / 60
                customView.setSize(newSize)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        buttonReset.setOnClickListener {
            textViewCustom.visibility = View.INVISIBLE
            imageViewCustom.visibility = View.INVISIBLE
        }

        buttonStart.setOnClickListener {
            customView.spin()
        }
        customView.setTargetTextView(textViewCustom)
        customView.setTargetImageView(imageViewCustom)

    }
}