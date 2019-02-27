package com.global.test.globaltest.network

import com.global.test.globaltest.network.DataService
import com.global.test.globaltest.network.WebClient
import org.junit.Before
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
    fun should_fetch_path_success() {
        service.fetchPath().test()
            .assertValue { it.next_path != null }
    }

}
