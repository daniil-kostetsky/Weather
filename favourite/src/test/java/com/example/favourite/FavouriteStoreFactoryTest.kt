package com.example.favourite

import com.arkivanov.mvikotlin.core.rx.observer
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.domain.entity.City
import com.example.domain.entity.CurrentWeather
import com.example.domain.repository.FavouriteRepository
import com.example.domain.repository.WeatherRepository
import com.example.domain.usecase.GetCurrentWeatherUseCase
import com.example.domain.usecase.GetFavouriteCitiesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Calendar

@OptIn(ExperimentalCoroutinesApi::class)
class FavouriteStoreFactoryTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `create loads favourite cities and their weather`() = runTest {
        val weather = testWeather()
        val store = createStore(
            favouriteRepository = FakeFavouriteRepository(listOf(testCity)),
            weatherRepository = FakeWeatherRepository(weather = weather)
        )

        advanceUntilIdle()

        assertEquals(
            listOf(
                FavouriteStore.State.CityItem(
                    city = testCity,
                    weatherState = FavouriteStore.State.WeatherState.Loaded(
                        tempC = weather.tempC,
                        iconUrl = weather.conditionUrl
                    )
                )
            ),
            store.state.cityItems
        )
        store.dispose()
    }

    @Test
    fun `weather error changes only corresponding city state to error`() = runTest {
        val store = createStore(
            favouriteRepository = FakeFavouriteRepository(listOf(testCity)),
            weatherRepository = FakeWeatherRepository(error = IllegalStateException())
        )

        advanceUntilIdle()

        assertEquals(
            FavouriteStore.State.WeatherState.Error,
            store.state.cityItems.single().weatherState
        )
        store.dispose()
    }

    @Test
    fun `click city publishes selected city label`() = runTest {
        val store = createStore()
        val labels = mutableListOf<FavouriteStore.Label>()
        val disposable = store.labels(observer(onNext = labels::add))

        store.accept(FavouriteStore.Intent.CityItemClicked(testCity))

        assertEquals(listOf(FavouriteStore.Label.CityItemClicked(testCity)), labels)
        disposable.dispose()
        store.dispose()
    }

    private fun createStore(
        favouriteRepository: FavouriteRepository = FakeFavouriteRepository(emptyList()),
        weatherRepository: WeatherRepository = FakeWeatherRepository(weather = testWeather())
    ): FavouriteStore = FavouriteStoreFactory(
        storeFactory = DefaultStoreFactory(),
        getFavouriteCitiesUseCase = GetFavouriteCitiesUseCase(favouriteRepository),
        getCurrentWeatherUseCase = GetCurrentWeatherUseCase(weatherRepository)
    ).create()

    private class FakeFavouriteRepository(cities: List<City>) : FavouriteRepository {
        private val citiesFlow = MutableStateFlow(cities)

        override val favouriteCities: Flow<List<City>> = citiesFlow

        override fun observeIsFavourite(cityId: Int): Flow<Boolean> = flowOf(false)

        override suspend fun addToFavourite(city: City) = Unit

        override suspend fun removeFromFavourite(cityId: Int) = Unit
    }

    private class FakeWeatherRepository(
        private val weather: CurrentWeather? = null,
        private val error: Exception? = null
    ) : WeatherRepository {
        override suspend fun getWeather(cityId: Int): CurrentWeather {
            error?.let { throw it }
            return requireNotNull(weather)
        }

        override suspend fun getForecast(cityId: Int) = error("Forecast is not used by FavouriteStore")
    }

    private companion object {
        val testCity = City(id = 1, name = "Moscow", country = "Russia")

        fun testWeather() = CurrentWeather(
            tempC = 20f,
            conditionText = "Clear",
            conditionUrl = "//cdn.example.com/clear.png",
            date = Calendar.getInstance()
        )
    }
}
