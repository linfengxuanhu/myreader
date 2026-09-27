package com.example.reader

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        val btnOpen = findViewById<Button>(R.id.btnOpen)

        btnOpen.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "text/plain"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                readText(uri)
            }
        }
    }

    private fun readText(uri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val text = inputStream?.bufferedReader()?.use { it.readText() }
            textView.text = text ?: "读取失败"
        } catch (e: Exception) {
            textView.text = "错误: ${e.message}"
        }
    }
}
