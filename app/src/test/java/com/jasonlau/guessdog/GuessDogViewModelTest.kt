package com.jasonlau.guessdog

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GuessDogViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var guessDogViewModel: GuessDogViewModel

    @Before
    fun setup() {
        guessDogViewModel = GuessDogViewModel(FakeGuessDogRepositoryImpl())
    }

    @Test
    fun `getDogData() should retrieve and transform data`() = runTest {
        guessDogViewModel.getDogData()
    }
}