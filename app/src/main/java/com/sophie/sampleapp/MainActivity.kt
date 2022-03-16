package com.sophie.sampleapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sophie.customkeyboard.R

class MainActivity : AppCompatActivity() {

    private lateinit var tvKeyboard: TextView

    val permissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvKeyboard = findViewById(R.id.tvKeyboard)

        tvKeyboard.text = "QR Scan keyboard currently disabled"

        startActivityForResult(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS), 99)

        if (isInputEnabled()) {
            (getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager).showInputMethodPicker()
        } else {
            Toast.makeText(this@MainActivity, "Please enable keyboard first", Toast.LENGTH_SHORT)
                .show()
        }
        if (hasNoPermissions()) {
            requestPermission()
        }
    }

    private fun hasNoPermissions(): Boolean{
        return ContextCompat.checkSelfPermission(this,
//            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(this, permissions,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 99) {
            tvKeyboard.text = "QR Scan keyboard now enabled"
        }
    }

    private fun isInputEnabled(): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val mInputMethodProperties = imm.enabledInputMethodList

        val N = mInputMethodProperties.size
        var isInputEnabled = false

        for (i in 0 until N) {

            val imi = mInputMethodProperties[i]
            Log.d("INPUT ID", imi.id.toString())
            if (imi.id.contains(packageName ?: "")) {
                isInputEnabled = true
            }
        }

        return isInputEnabled
    }

}
