package com.global.test.globaltest.repositories

import android.content.SharedPreferences
import com.global.test.globaltest.network.DataService
import com.global.test.globaltest.network.WebClient
import com.global.test.globaltest.repositories.LocalRepository
import com.global.test.globaltest.repositories.SharedPreferencesRepository
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class SharedPreferencesRepositoryTest {

    private lateinit var repository: SharedPreferencesRepository
    @Mock lateinit var sharedPreferences : SharedPreferences
    @Mock lateinit var editor : SharedPreferences.Editor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = SharedPreferencesRepository(sharedPreferences)
    }

    @Test
    fun should_fetch_path_success() {
        // arrange
        `when`(sharedPreferences.edit()).thenReturn(editor)
        `when`(editor.putInt(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())).thenReturn(editor)
        `when`(editor.putString(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(editor)

        // act
        repository.saveData(1, "code")

        // assert
        Mockito.verify<SharedPreferences.Editor>(editor).putInt(repository.TIMES_KEY, 1)
        Mockito.verify<SharedPreferences.Editor>(editor).putString(repository.CODE_KEY, "code")
    }

    @Test
    fun get_times_success() {
        // act
        repository.getTimes()

        // assert
        Mockito.verify<SharedPreferences>(sharedPreferences).getInt(repository.TIMES_KEY, 0)
    }

    @Test
    fun get_code_success() {
        // act
        repository.getCode()

        // assert
        Mockito.verify<SharedPreferences>(sharedPreferences).getString(repository.CODE_KEY, null)
    }

}
