
package com.global.test.globaltest.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.global.test.globaltest.RxImmediateSchedulerRule
import com.global.test.globaltest.model.CodeData
import com.global.test.globaltest.model.PathData
import com.global.test.globaltest.network.WebClient
import com.global.test.globaltest.repositories.DataRepository
import com.global.test.globaltest.repositories.LocalRepository
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class MainViewModelTest {

    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Mock private lateinit var repository: DataRepository
    @Mock private lateinit var localRepository: LocalRepository

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(repository, localRepository)
    }

    @Test fun fetchCode_success_should_update_code() {
        // arrange
        `when`(repository.fetchPath()).thenReturn(Observable.just(PathData("path")))
        `when`(repository.fetchCode("path")).thenReturn(Observable.just(CodeData("path", "code")))

        // act
        viewModel.fetchCode()

        // assert
        assertEquals("code", viewModel.code.value)
    }

    @Test fun fetchPath_error_should_set_error() {
        // arrange
        `when`(repository.fetchPath()).thenReturn(Observable.error(Throwable("path_error")))

        // act
        viewModel.fetchCode()

        // assert
        assertNull(viewModel.code.value)
        assertEquals("path_error", viewModel.error.value)
    }

    @Test fun fetchCode_error_should_set_error() {
        // arrange
        `when`(repository.fetchPath()).thenReturn(Observable.just(PathData("path")))
        `when`(repository.fetchCode("path")).thenReturn(Observable.error(Throwable("code_error")))

        // act
        viewModel.fetchCode()

        // assert
        assertNull(viewModel.code.value)
        assertEquals("code_error", viewModel.error.value)
    }

    @Test fun fetchCode_success_should_save_data_locally() {
        // arrange
        `when`(repository.fetchPath()).thenReturn(Observable.just(PathData("path")))
        `when`(repository.fetchCode("path")).thenReturn(Observable.just(CodeData("path", "code")))

        // act
        viewModel.fetchCode()

        // assert
        Mockito.verify<LocalRepository>(localRepository).saveData(1, "code")
    }

    @Test fun on_init_should_load_local_data() {
        // assert
        Mockito.verify<LocalRepository>(localRepository).getCode()
        Mockito.verify<LocalRepository>(localRepository).getTimes()
    }

    @Test fun should_format_path() {
        // arrange
        val nextPath = WebClient.host + "path"
        `when`(repository.fetchPath()).thenReturn(Observable.just(PathData(nextPath)))
        `when`(repository.fetchCode("path")).thenReturn(Observable.just(CodeData("path", "code")))

        // act
        viewModel.fetchCode()

        // assert
        assertEquals("code", viewModel.code.value)
    }

}