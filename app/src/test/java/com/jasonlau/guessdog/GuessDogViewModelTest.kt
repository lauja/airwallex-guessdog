package com.jasonlau.guessdog

import com.jasonlau.guessdog.repository.FakeGuessDogRepositoryImpl
import com.jasonlau.guessdog.util.BreedMapTransformer
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
        guessDogViewModel = GuessDogViewModel(
            FakeGuessDogRepositoryImpl(),
            BreedMapTransformer(),
            RandomBreedChooser(),
        )
    }

    @Test
    fun `getDogData() should retrieve and transform data`() = runTest {
        guessDogViewModel.getDogData()
    }
}