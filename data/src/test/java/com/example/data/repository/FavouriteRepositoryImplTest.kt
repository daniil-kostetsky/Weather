package com.example.data.repository

import com.example.data.local.CityDbModel
import com.example.data.local.FavouriteCitiesDao
import com.example.domain.entity.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class FavouriteRepositoryImplTest {

    @Test
    fun `favourite cities maps database models to domain cities`() = runBlocking {
        val dao = FakeFavouriteCitiesDao(
            cities = listOf(CityDbModel(id = 1, name = "Moscow", country = "Russia"))
        )
        val repository = FavouriteRepositoryImpl(dao)

        val result = repository.favouriteCities.first()

        assertEquals(listOf(City(id = 1, name = "Moscow", country = "Russia")), result)
    }

    @Test
    fun `add to favourite maps domain city to database model`() = runBlocking {
        val dao = FakeFavouriteCitiesDao(emptyList())
        val repository = FavouriteRepositoryImpl(dao)
        val city = City(id = 1, name = "Moscow", country = "Russia")

        repository.addToFavourite(city)

        assertEquals(CityDbModel(id = 1, name = "Moscow", country = "Russia"), dao.addedCity)
    }

    @Test
    fun `remove from favourite delegates city id to dao`() = runBlocking {
        val dao = FakeFavouriteCitiesDao(emptyList())
        val repository = FavouriteRepositoryImpl(dao)

        repository.removeFromFavourite(42)

        assertEquals(42, dao.removedCityId)
    }

    @Test
    fun `observe favourite state delegates city id to dao`() = runBlocking {
        val dao = FakeFavouriteCitiesDao(emptyList(), isFavourite = true)
        val repository = FavouriteRepositoryImpl(dao)

        val result = repository.observeIsFavourite(42).first()

        assertEquals(42, dao.observedCityId)
        assertEquals(true, result)
    }

    private class FakeFavouriteCitiesDao(
        cities: List<CityDbModel>,
        private val isFavourite: Boolean = false
    ) : FavouriteCitiesDao {
        private val citiesFlow = MutableStateFlow(cities)

        var addedCity: CityDbModel? = null
        var removedCityId: Int? = null
        var observedCityId: Int? = null

        override fun getFavouriteCities(): Flow<List<CityDbModel>> = citiesFlow

        override fun observeIsFavourite(cityId: Int): Flow<Boolean> {
            observedCityId = cityId
            return flowOf(isFavourite)
        }

        override suspend fun addToFavourite(cityDbModel: CityDbModel) {
            addedCity = cityDbModel
        }

        override suspend fun removeFromFavourite(cityId: Int) {
            removedCityId = cityId
        }
    }
}
