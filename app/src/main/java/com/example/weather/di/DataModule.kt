package com.example.weather.di

import android.content.Context
import com.example.data.local.FavouriteCitiesDao
import com.example.data.local.FavouriteDatabase
import com.example.data.network.api.ApiFactory
import com.example.data.network.api.ApiService
import com.example.data.repository.FavouriteRepositoryImpl
import com.example.data.repository.SearchRepositoryImpl
import com.example.data.repository.WeatherRepositoryImpl
import com.example.domain.repository.FavouriteRepository
import com.example.domain.repository.SearchRepository
import com.example.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @[ApplicationScope Binds]
    fun bindFavouriteRepository(impl: FavouriteRepositoryImpl): FavouriteRepository

    @[ApplicationScope Binds]
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @[ApplicationScope Binds]
    fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    companion object {

        @[ApplicationScope Provides]
        fun provideApiService(): ApiService = ApiFactory.apiService

        @[ApplicationScope Provides]
        fun provideFavouriteDatabase(context: Context): FavouriteDatabase {
            return FavouriteDatabase.getInstance(context)
        }

        @[ApplicationScope Provides]
        fun provideFavouriteCitiesDao(database: FavouriteDatabase): FavouriteCitiesDao {
            return database.favouriteCitiesDao()
        }
    }
}