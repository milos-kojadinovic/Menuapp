package com.example.menuapp.viewmodels


import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.menuapp.data.Data
import com.example.menuapp.data.LoginRequest
import com.example.menuapp.data.LoginResponse
import com.example.menuapp.data.Repository
import com.example.menuapp.data.Token
import com.example.menuapp.doWhileObserved
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class LoginViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var testCoroutineDispatcher: TestDispatcher

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        testCoroutineDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testCoroutineDispatcher)
        loginViewModel = LoginViewModel(repository)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun loginSuccessful() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "password"
        val token = "sampleToken"
        val loginRequest = LoginRequest(email = email, password = password)
        val successfulResponse = Response.success(LoginResponse(Data(Token(token))))
        `when`(repository.login(loginRequest)).thenReturn(successfulResponse)
        val observer = mock(Observer::class.java) as Observer<LoginViewModelState>
        loginViewModel.state.doWhileObserved(observer) {
            loginViewModel.handleIntent(LoginViewModelIntent.TryLogin(email, password))

            verify(repository).login(loginRequest)
            verify(observer).onChanged(LoginViewModelState.Loading)
            verify(observer).onChanged(LoginViewModelState.UserLoggedIn(token))
        }

    }

    @Test
    fun loginUnsuccessful() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "password"
        val loginRequest = LoginRequest(email = email, password = password)
        val message = "Consumer Error"
        val errorResponse = """{
            "data": {
                "message": "$message"
            }
         }"""

        val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
        val unsuccessfulResponse = Response.error<LoginResponse>(500, errorResponseBody)

        `when`(repository.login(loginRequest)).thenReturn(unsuccessfulResponse)
        val observer = mock(Observer::class.java) as Observer<LoginViewModelState>
        loginViewModel.state.doWhileObserved(observer) {
            loginViewModel.handleIntent(LoginViewModelIntent.TryLogin(email, password))
            verify(repository).login(loginRequest)
            verify(observer).onChanged(LoginViewModelState.Loading)
            verify(observer).onChanged(LoginViewModelState.Error(message))
        }
    }

}

