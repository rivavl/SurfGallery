package com.marina.surfgallery.common

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marina.surfgallery.R
import com.marina.surfgallery.core.presentation.fragment.FavoriteFragment
import com.marina.surfgallery.core.presentation.fragment.HomeFragment
import com.marina.surfgallery.profile.presentation.ProfileFragment

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value == true
            }
        }
        setContentView(R.layout.activity_main)

//        launchFragment(LoginFragment())
        findViewById<BottomNavigationView>(R.id.bottom_nav_bar).setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_main -> {
                    launchFragment(HomeFragment())
                    true
                }

                R.id.menu_favorite -> {
                    launchFragment(FavoriteFragment())
                    true
                }

                R.id.menu_profile -> {
                    launchFragment(ProfileFragment())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}