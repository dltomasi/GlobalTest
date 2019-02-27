
package com.global.test.globaltest.ui

import com.global.test.globaltest.RxImmediateSchedulerRule
import com.global.test.globaltest.model.CodeData
import com.global.test.globaltest.model.PathData
import com.global.test.globaltest.repositories.DataRepository
import com.global.test.globaltest.repositories.LocalRepository
import com.global.test.globaltest.ui.MainViewModel
import com.nhaarman.mockito_kotlin.capture
import io.reactivex.Observable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.`when`

class MainViewModelTest {

    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

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
        assertEquals("code", viewModel.code)
    }

    @Test fun fetchPath_error_should_set_error() {
        // arrange
        `when`(repository.fetchPath()).thenReturn(Observable.error(Throwable("path_error")))

        // act
        viewModel.fetchCode()

        // assert
        assertNull(viewModel.code)
        assertEquals("path_error", viewModel.error.value)
    }

    @Test fun fetchCode_error_should_set_error() {
        // arrange
        `when`(repository.fetchPath()).thenReturn(Observable.just(PathData("path")))
        `when`(repository.fetchCode("path")).thenReturn(Observable.error(Throwable("code_error")))

        // act
        viewModel.fetchCode()

        // assert
        assertNull(viewModel.code)
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
}