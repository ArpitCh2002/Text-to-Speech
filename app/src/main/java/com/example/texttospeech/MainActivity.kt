package com.example.texttospeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.texttospeech.databinding.ActivityMainBinding
import org.w3c.dom.Text
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        tts = TextToSpeech(this, this)

        binding?.btnSpeak?.setOnClickListener { view ->
            if (binding?.etEnteredText?.text!!.isEmpty()) {
                Toast.makeText(this, "Enter Text to Speak", Toast.LENGTH_SHORT).show()
            }
            else {
                speakOut(binding?.etEnteredText?.text.toString())
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.ENGLISH)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The language is not Supported!")
            }
            else {
                Log.e("TTS", "Initialization Failed!")
            }
        }
    }

    override fun onDestroy() {
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }

        super.onDestroy()
    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}