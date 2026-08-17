package com.example.details

import com.arkivanov.mvikotlin.core.rx.observer
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.domain.entity.City
import com.example.domain.entity.CurrentWeather
import com.example.domain.entity.ForecastWeather
import com.example.domain.repository.FavouriteRepository
import com.example.domain.repository.WeatherRepository
import com.example.domain.usecase.ChangeFavouriteStateUseCase
import com.example.domain.usecase.GetForecastUseCase
import com.example.domain.usecase.ObserveFavouriteStateUseCase
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Calendar

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsStoreFactoryTest {

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
    fun `create loads forecast and favourite status`() = runTest {
        val forecast = testForecast()
        val favouriteRepository = FakeFavouriteRepository(isFavourite = true)
        val store = createStore(
            weatherRepository = FakeWeatherRepository(forecast = forecast),
            favouriteRepository = favouriteRepository
        )

        advanceUntilIdle()

        assertTrue(store.state.isFavourite)
        assertEquals(
            DetailsStore.State.ForecastState.Loaded(forecast),
            store.state.forecastState
        )
        store.dispose()
    }

    @Test
    fun `click change favourite status adds a city when it is not favourite`() = runTest {
        val favouriteRepository = FakeFavouriteRepository(isFavourite = false)
        val store = createStore(favouriteRepository = favouriteRepository)

        advanceUntilIdle()
        store.accept(DetailsStore.Intent.ClickChangeFavouriteStatus)
        advanceUntilIdle()

        assertEquals(listOf(testCity), favouriteRepository.addedCities)
        assertTrue(favouriteRepository.removedCityIds.isEmpty())
        store.dispose()
    }

    @Test
    fun `click change favourite status removes a city when it is favourite`() = runTest {
        val favouriteRepository = FakeFavouriteRepository(isFavourite = true)
        val store = createStore(favouriteRepository = favouriteRepository)

        advanceUntilIdle()
        store.accept(DetailsStore.Intent.ClickChangeFavouriteStatus)
        advanceUntilIdle()

        assertEquals(listOf(testCity.id), favouriteRepository.removedCityIds)
        assertTrue(favouriteRepository.addedCities.isEmpty())
        store.dispose()
    }

    @Test
    fun `click back publishes click back label`() = runTest {
        val store = createStore()
        val labels = mutableListOf<DetailsStore.Label>()
        val disposable = store.labels(observer(onNext = labels::add))

        store.accept(DetailsStore.Intent.ClickBack)

        assertEquals(listOf(DetailsStore.Label.ClickBack), labels)
        disposable.dispose()
        store.dispose()
    }

    private fun createStore(
        weatherRepository: WeatherRepository = FakeWeatherRepository(forecast = testForecast()),
        favouriteRepository: FavouriteRepository = FakeFavouriteRepository(isFavourite = false)
    ): DetailsStore {
        return DetailsStoreFactory(
            storeFactory = DefaultStoreFactory(),
            getForecastUseCase = GetForecastUseCase(weatherRepository),
            changeFavouriteStateUseCase = ChangeFavouriteStateUseCase(favouriteRepository),
            observeFavouriteStateUseCase = ObserveFavouriteStateUseCase(favouriteRepository)
        ).create(testCity)
    }

    private class FakeWeatherRepository(
        private val forecast: ForecastWeather
    ) : WeatherRepository {
        override suspend fun getWeather(cityId: Int): CurrentWeather = forecast.currentWeather

        override suspend fun getForecast(cityId: Int): ForecastWeather = forecast
    }

    private class FakeFavouriteRepository(isFavourite: Boolean) : FavouriteRepository {
        private val favouriteState = MutableStateFlow(isFavourite)

        val addedCities = mutableListOf<City>()
        val removedCityIds = mutableListOf<Int>()

        override val favouriteCities: Flow<List<City>> = flowOf(emptyList())

        override fun observeIsFavourite(cityId: Int): Flow<Boolean> = favouriteState

        override suspend fun addToFavourite(city: City) {
            addedCities += city
            favouriteState.value = true
        }

        override suspend fun removeFromFavourite(cityId: Int) {
            removedCityIds += cityId
            favouriteState.value = false
        }
    }

    private companion object {
        val testCity = City(id = 1, name = "Moscow", country = "Russia")

        fun testForecast(): ForecastWeather {
            val currentWeather = CurrentWeather(
                tempC = 20f,
                conditionText = "Clear",
                conditionUrl = "//cdn.example.com/clear.png",
                date = Calendar.getInstance()
            )
            return ForecastWeather(
                currentWeather = currentWeather,
                dayWeather = emptyList(),
                hourWeather = emptyList()
            )
        }
    }
}
