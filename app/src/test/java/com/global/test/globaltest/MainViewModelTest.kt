
package com.global.test.globaltest

import com.global.test.globaltest.model.CodeData
import com.global.test.globaltest.model.PathData
import com.global.test.globaltest.repositories.DataRepository
import com.global.test.globaltest.ui.MainViewModel
import io.reactivex.Observable
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MainViewModelTest {

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
        assertTrue(viewModel.code.equals("code"))
    }

}