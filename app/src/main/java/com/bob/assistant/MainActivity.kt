package com.bob.assistant

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.widget.*
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var videoUri: Uri? = null
    private lateinit var status: TextView
    private lateinit var captionBox: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(40, 60, 40, 40)

        val title = TextView(this)
        title.text = "Bob Universal Uploader"
        title.textSize = 24f

        val pickBtn = Button(this)
        pickBtn.text = "Pick Video"

        captionBox = EditText(this)
        captionBox.hint = "Caption / hashtags"
        captionBox.minLines = 5
        captionBox.setText("🔥 New video\n\n#shorts #viral #bob")

        val copyBtn = Button(this)
        copyBtn.text = "Copy Caption"

        val shareBtn = Button(this)
        shareBtn.text = "Share To Any Platform"

        status = TextView(this)
        status.text = "No video selected"

        layout.addView(title)
        layout.addView(pickBtn)
        layout.addView(captionBox)
        layout.addView(copyBtn)
        layout.addView(shareBtn)
        layout.addView(status)

        setContentView(layout)

        pickBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "video/*"
            startActivityForResult(intent, 100)
        }

        copyBtn.setOnClickListener {
            val clip = ClipData.newPlainText("Bob Caption", captionBox.text.toString())
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(clip)
            status.text = "Caption copied"
        }

        shareBtn.setOnClickListener {
            shareVideo()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            videoUri = data?.data
            status.text = "Video selected"
        }
    }

    private fun shareVideo() {
        val uri = videoUri

        if (uri == null) {
            status.text = "Pick a video first"
            return
        }

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "video/*"
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.putExtra(Intent.EXTRA_TEXT, captionBox.text.toString())
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(Intent.createChooser(shareIntent, "Post with Bob"))
        status.text = "Share sheet opened"
    }
}
