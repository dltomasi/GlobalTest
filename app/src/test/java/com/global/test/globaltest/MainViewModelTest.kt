
package com.global.test.globaltest

import com.global.test.globaltest.model.CodeData
import com.global.test.globaltest.model.PathData
import com.global.test.globaltest.repositories.DataRepository
import com.global.test.globaltest.ui.MainViewModel
import io.reactivex.Observable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var viewModel: MainViewModel

    @Mock private lateinit var repository: DataRepository

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(repository)
    }

    @Test fun fetchCode_success() {
        // arrange
        `when`(repository.fetchPath()).thenReturn(Observable.just(PathData("path")))
        `when`(repository.fetchCode("path")).thenReturn(Observable.just(CodeData("path", "code")))

        // act
        viewModel.fetchCode()

        // assert
        assertEquals("code", viewModel.code)
    }

    @Test fun fetchPath_error() {
        // arrange
        `when`(repository.fetchPath()).thenReturn(Observable.error(Throwable("error1")))
        `when`(repository.fetchCode("path")).thenReturn(Observable.just(CodeData("path", "code")))

        // act
        viewModel.fetchCode()

        // assert
        assertNull(viewModel.code)
        assertEquals("error1", viewModel.error.value)
    }

    @Test fun fetchCode_error() {
        // arrange
        `when`(repository.fetchPath()).thenReturn(Observable.just(PathData("path")))
        `when`(repository.fetchCode("path")).thenReturn(Observable.error(Throwable("error2")))

        // act
        viewModel.fetchCode()

        // assert
        assertNull(viewModel.code)
        assertEquals("error2", viewModel.error.value)
    }
}