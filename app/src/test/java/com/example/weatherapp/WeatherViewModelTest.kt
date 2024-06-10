package com.example.weatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.data.model.Main
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.ui.viewmodel.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var repository: WeatherRepository
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(WeatherRepository::class.java)
        viewModel = WeatherViewModel(repository)
    }

    @Test
    fun testFetchWeather() = runTest {
        val mockResponse = WeatherResponse(Main(temp = 22.2))
        `when`(repository.getWeather("London", "6816bce8a645859d22e4cd2172b562ac")).thenReturn(Result.success(mockResponse))

        viewModel.getWeather("London", "6816bce8a645859d22e4cd2172b562ac")
        advanceUntilIdle()
        assertEquals(mockResponse, viewModel.weatherState.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

}
//
//private fun Any.thenReturn(mockResponse: WeatherResponse) {
//}
