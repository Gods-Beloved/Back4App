package com.smartherd.back4app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseInstallation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ParseInstallation.getCurrentInstallation().saveInBackground()


    }
}