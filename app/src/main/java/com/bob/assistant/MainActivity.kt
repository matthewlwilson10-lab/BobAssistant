package com.bob.assistant

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val text = TextView(this)
        text.text = "Bob Assistant is Alive"

        setContentView(text)
    }
}package com.bob.assistant

class MainActivity {
}
