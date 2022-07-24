package com.marina.surfgallery.common.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marina.surfgallery.R

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value == true
            }
        }


        findViewById<BottomNavigationView>(R.id.bottom_nav_bar).setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_main -> {
                    launchFragment(R.id.homeFragment)
                    true
                }

                R.id.menu_favorite -> {
                    launchFragment(R.id.favoriteFragment)
                    true
                }

                R.id.menu_profile -> {
                    launchFragment(R.id.profileFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun launchFragment(id: Int) {
        this.findNavController(R.id.nav_host_fragment).navigate(id)
    }
}