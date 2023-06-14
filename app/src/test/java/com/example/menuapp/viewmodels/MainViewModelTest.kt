package com.example.menuapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.menuapp.data.Image
import com.example.menuapp.data.ItemsData
import com.example.menuapp.data.ItemsInfo
import com.example.menuapp.data.Repository
import com.example.menuapp.data.ServingTime
import com.example.menuapp.data.Venue
import com.example.menuapp.data.VenueDetails
import com.example.menuapp.doWhileObserved
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response


@ExperimentalCoroutinesApi
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    private lateinit var mainViewModel: MainViewModel

    private lateinit var testCoroutineDispatcher: TestDispatcher

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        testCoroutineDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testCoroutineDispatcher)
        mainViewModel = MainViewModel(repository)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun successful() = runTest {
        val servingTimes = listOf(
            ServingTime("9:00", "15:00", listOf(1.0, 2.0, 3.0, 4.0, 5.0)),
            ServingTime("11:00", "17:00", listOf(6.0, 7.0))
        )
        val venueDetails = VenueDetails(
            "Example Venue",
            "Example description",
            "Welcome message",
            "123 Example Street",
            servingTimes,
            Image("https://example.com/thumbnail.jpg"),
            true
        )

        val venue = Venue(
            1.5,
            2.3,
            venueDetails
        )
        val response = ItemsInfo(ItemsData(listOf(venue)))

        val successfulResponse = Response.success(response)

        `when`(repository.loadData()).thenReturn(successfulResponse)
        val observer = Mockito.mock(Observer::class.java) as Observer<MainViewModelState>

        mainViewModel.state.doWhileObserved(observer) {
            mainViewModel.handleIntent(MainViewModelIntent.LoadData)
            verify(repository).loadData()
            verify(observer, times(2)).onChanged(MainViewModelState.Loading)
            verify(observer).onChanged(MainViewModelState.LoadedData(response.data.venues))
        }

    }


    @Test
    fun unsuccessful() = runTest {
        val message = "Consumer Error"
        val errorResponse = """{
            "data": {
                "message": "$message"
            }
         }"""

        val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
        val unsuccessfulResponse = Response.error<ItemsInfo>(500, errorResponseBody)

        `when`(repository.loadData()).thenReturn(unsuccessfulResponse)
        val observer = Mockito.mock(Observer::class.java) as Observer<MainViewModelState>
        mainViewModel.state.doWhileObserved(observer) {
            mainViewModel.handleIntent(MainViewModelIntent.LoadData)
            verify(repository).loadData()
            verify(observer, times(2)).onChanged(MainViewModelState.Loading)
            verify(observer).onChanged(MainViewModelState.Error(message))
        }
    }

}