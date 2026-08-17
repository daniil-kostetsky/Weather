package com.example.weather

import android.app.Application
import com.example.weather.di.ApplicationComponent
import com.example.weather.di.DaggerApplicationComponent

open class WeatherApp : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = createApplicationComponent()
    }

    protected open fun createApplicationComponent(): ApplicationComponent =
        DaggerApplicationComponent.factory().create(this)
}
