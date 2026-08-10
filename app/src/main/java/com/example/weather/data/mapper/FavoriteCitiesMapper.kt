package com.example.weather.data.mapper

import com.example.weather.data.local.CityDbModel
import com.example.weather.domain.entity.City
import kotlinx.coroutines.flow.Flow

fun City.toDbModel(): CityDbModel = CityDbModel(id, name, country)

fun CityDbModel.toEntity(): City = City(id, name, country)

fun List<CityDbModel>.toEntities(): List<City> = map { it.toEntity() }