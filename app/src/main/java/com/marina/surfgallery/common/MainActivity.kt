package com.marina.surfgallery.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marina.surfgallery.auth.presentation.LoginFragment
import com.marina.surfgallery.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, LoginFragment())
            .commit()
    }
}