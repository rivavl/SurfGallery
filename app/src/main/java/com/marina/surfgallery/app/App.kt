package com.marina.surfgallery.app

import android.app.Application
import com.marina.surfgallery.di.DaggerAppComponent

class App : Application() {

    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }
}