package com.global.test.globaltest.network

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.MockitoAnnotations

class DataServiceTest {

    private lateinit var service: DataService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        service = WebClient().dataService()
    }

    @Test
    @Ignore
    fun should_fetch_path_success() {
        service.fetchPath().test()
            .assertValue { it.next_path != null }
    }

}
