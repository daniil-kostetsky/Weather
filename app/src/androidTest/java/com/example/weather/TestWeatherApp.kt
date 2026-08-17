package com.example.weather

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.details.DefaultDetailsComponent
import com.example.details.DetailsStoreFactory
import com.example.domain.entity.City
import com.example.domain.entity.CurrentWeather
import com.example.domain.entity.ForecastWeather
import com.example.domain.repository.FavouriteRepository
import com.example.domain.repository.SearchRepository
import com.example.domain.repository.WeatherRepository
import com.example.domain.usecase.ChangeFavouriteStateUseCase
import com.example.domain.usecase.GetCurrentWeatherUseCase
import com.example.domain.usecase.GetFavouriteCitiesUseCase
import com.example.domain.usecase.GetForecastUseCase
import com.example.domain.usecase.ObserveFavouriteStateUseCase
import com.example.domain.usecase.SearchCityUseCase
import com.example.favourite.DefaultFavouriteComponent
import com.example.favourite.FavouriteStoreFactory
import com.example.search.DefaultSearchComponent
import com.example.search.SearchStoreFactory
import com.example.weather.di.ApplicationComponent
import com.example.weather.root.DefaultRootComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import java.util.Calendar

class TestWeatherApp : WeatherApp() {
    override fun createApplicationComponent(): ApplicationComponent = TestApplicationComponent()
}

private class TestApplicationComponent : ApplicationComponent {
    private val city = City(id = 1, name = "Moscow", country = "Russia")
    private val favouriteRepository = FakeFavouriteRepository()
    private val weatherRepository = FakeWeatherRepository()
    private val searchRepository = FakeSearchRepository(city)
    private val storeFactory: StoreFactory = DefaultStoreFactory()

    override fun inject(activity: MainActivity) {
        activity.rootComponentFactory = object : DefaultRootComponent.Factory {
            override fun create(componentContext: ComponentContext): DefaultRootComponent {
                return DefaultRootComponent(
                    detailsComponentFactory = detailsComponentFactory(),
                    favouriteComponentFactory = favouriteComponentFactory(),
                    searchComponentFactory = searchComponentFactory(),
                    componentContext = componentContext
                )
            }
        }
    }

    private fun detailsComponentFactory() = object : DefaultDetailsComponent.Factory {
        override fun create(
            city: City,
            onBackClicked: () -> Unit,
            componentContext: ComponentContext
        ) = DefaultDetailsComponent(
            storeFactory = DetailsStoreFactory(
                storeFactory = storeFactory,
                getForecastUseCase = GetForecastUseCase(weatherRepository),
                changeFavouriteStateUseCase = ChangeFavouriteStateUseCase(favouriteRepository),
                observeFavouriteStateUseCase = ObserveFavouriteStateUseCase(favouriteRepository)
            ),
            city = city,
            onBackClicked = onBackClicked,
            componentContext = componentContext
        )
    }

    private fun favouriteComponentFactory() = object : DefaultFavouriteComponent.Factory {
        override fun create(
            onCityItemClicked: (City) -> Unit,
            onAddFavouriteClicked: () -> Unit,
            onSearchClicked: () -> Unit,
            componentContext: ComponentContext
        ) = DefaultFavouriteComponent(
            favouriteStoreFactory = FavouriteStoreFactory(
                storeFactory = storeFactory,
                getFavouriteCitiesUseCase = GetFavouriteCitiesUseCase(favouriteRepository),
                getCurrentWeatherUseCase = GetCurrentWeatherUseCase(weatherRepository)
            ),
            onCityItemClicked = onCityItemClicked,
            onAddFavouriteClicked = onAddFavouriteClicked,
            onSearchClicked = onSearchClicked,
            componentContext = componentContext
        )
    }

    private fun searchComponentFactory() = object : DefaultSearchComponent.Factory {
        override fun create(
            openReason: com.example.search.OpenReason,
            onBackClicked: () -> Unit,
            onCitySavedToFavourite: () -> Unit,
            onForecastForCityRequested: (City) -> Unit,
            componentContext: ComponentContext
        ) = DefaultSearchComponent(
            storeFactory = SearchStoreFactory(
                storeFactory = storeFactory,
                searchCityUseCase = SearchCityUseCase(searchRepository),
                changeFavouriteStateUseCase = ChangeFavouriteStateUseCase(favouriteRepository)
            ),
            openReason = openReason,
            onBackClicked = onBackClicked,
            onCitySavedToFavourite = onCitySavedToFavourite,
            onForecastForCityRequested = onForecastForCityRequested,
            componentContext = componentContext
        )
    }

    private class FakeSearchRepository(private val city: City) : SearchRepository {
        override suspend fun search(query: String): List<City> =
            if (query.equals(city.name, ignoreCase = true)) listOf(city) else emptyList()
    }

    private class FakeFavouriteRepository : FavouriteRepository {
        private val cities = MutableStateFlow<List<City>>(emptyList())

        override val favouriteCities: Flow<List<City>> = cities

        override fun observeIsFavourite(cityId: Int): Flow<Boolean> =
            flowOf(cities.value.any { it.id == cityId })

        override suspend fun addToFavourite(city: City) {
            cities.value = (cities.value + city).distinctBy(City::id)
        }

        override suspend fun removeFromFavourite(cityId: Int) {
            cities.value = cities.value.filterNot { it.id == cityId }
        }
    }

    private class FakeWeatherRepository : WeatherRepository {
        private val currentWeather = CurrentWeather(
            tempC = 20f,
            conditionText = "Clear",
            conditionUrl = "",
            date = Calendar.getInstance()
        )

        override suspend fun getWeather(cityId: Int): CurrentWeather = currentWeather

        override suspend fun getForecast(cityId: Int) = ForecastWeather(
            currentWeather = currentWeather,
            dayWeather = emptyList(),
            hourWeather = emptyList()
        )
    }
}
