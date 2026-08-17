package com.example.search

import com.arkivanov.mvikotlin.core.rx.observer
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.domain.entity.City
import com.example.domain.repository.FavouriteRepository
import com.example.domain.repository.SearchRepository
import com.example.domain.usecase.ChangeFavouriteStateUseCase
import com.example.domain.usecase.SearchCityUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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

@OptIn(ExperimentalCoroutinesApi::class)
class SearchStoreFactoryTest {

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
    fun `search publishes loaded cities to state`() = runTest {
        val cities = listOf(testCity)
        val store = createStore(searchRepository = FakeSearchRepository(cities))

        store.accept(SearchStore.Intent.ChangeSearchQuery("Moscow"))
        store.accept(SearchStore.Intent.ClickSearch)
        advanceUntilIdle()

        assertEquals("Moscow", store.state.searchQuery)
        assertEquals(SearchStore.State.SearchState.SuccessLoaded(cities), store.state.searchState)
        store.dispose()
    }

    @Test
    fun `empty search result changes state to empty result`() = runTest {
        val store = createStore(searchRepository = FakeSearchRepository(emptyList()))

        store.accept(SearchStore.Intent.ClickSearch)
        advanceUntilIdle()

        assertEquals(SearchStore.State.SearchState.EmptyResult, store.state.searchState)
        store.dispose()
    }

    @Test
    fun `click city in add favourite mode saves city and publishes label`() = runTest {
        val favouriteRepository = FakeFavouriteRepository()
        val store = createStore(
            openReason = OpenReason.AddToFavourite,
            favouriteRepository = favouriteRepository
        )
        val labels = mutableListOf<SearchStore.Label>()
        val disposable = store.labels(observer(onNext = labels::add))

        store.accept(SearchStore.Intent.ClickCity(testCity))
        advanceUntilIdle()

        assertEquals(listOf(testCity), favouriteRepository.addedCities)
        assertEquals(listOf(SearchStore.Label.SavedToFavourite), labels)
        disposable.dispose()
        store.dispose()
    }

    @Test
    fun `click city in regular search mode publishes open forecast label`() = runTest {
        val store = createStore(openReason = OpenReason.RegularSearch)
        val labels = mutableListOf<SearchStore.Label>()
        val disposable = store.labels(observer(onNext = labels::add))

        store.accept(SearchStore.Intent.ClickCity(testCity))

        assertEquals(listOf(SearchStore.Label.OpenForecast(testCity)), labels)
        disposable.dispose()
        store.dispose()
    }

    private fun createStore(
        openReason: OpenReason = OpenReason.RegularSearch,
        searchRepository: SearchRepository = FakeSearchRepository(emptyList()),
        favouriteRepository: FavouriteRepository = FakeFavouriteRepository()
    ): SearchStore = SearchStoreFactory(
        storeFactory = DefaultStoreFactory(),
        searchCityUseCase = SearchCityUseCase(searchRepository),
        changeFavouriteStateUseCase = ChangeFavouriteStateUseCase(favouriteRepository)
    ).create(openReason)

    private class FakeSearchRepository(
        private val result: List<City>
    ) : SearchRepository {
        override suspend fun search(query: String): List<City> = result
    }

    private class FakeFavouriteRepository : FavouriteRepository {
        val addedCities = mutableListOf<City>()

        override val favouriteCities: Flow<List<City>> = flowOf(emptyList())

        override fun observeIsFavourite(cityId: Int): Flow<Boolean> = flowOf(false)

        override suspend fun addToFavourite(city: City) {
            addedCities += city
        }

        override suspend fun removeFromFavourite(cityId: Int) = Unit
    }

    private companion object {
        val testCity = City(id = 1, name = "Moscow", country = "Russia")
    }
}
