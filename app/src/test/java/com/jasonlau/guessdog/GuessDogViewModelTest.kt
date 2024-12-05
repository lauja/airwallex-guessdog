package com.jasonlau.guessdog

import com.jasonlau.guessdog.data.BreedData
import com.jasonlau.guessdog.repository.FakeGuessDogRepositoryImpl
import com.jasonlau.guessdog.util.FakeBreedMapTransformerImpl
import com.jasonlau.guessdog.util.FakeRandomBreedChooserImpl
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
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
            FakeBreedMapTransformerImpl(),
            FakeRandomBreedChooserImpl(),
        )
    }

    @Test
    fun `getDogData() should retrieve and transform data`() = runTest {
        guessDogViewModel.getDogData()

        MatcherAssert.assertThat(
            guessDogViewModel.viewState.value, CoreMatchers.equalTo(
                GuessDogUiState(
                    isLoading = false,
                    data =
                    BreedData(
                        imageUrl = "https://images.dog.ceo/breeds/labradoodle/n02112706_62.jpg",
                        breedAnswers = listOf("Kombai", "Komondor", "Kuvasz", "Labradoodle"),
                        correctAnswer = "Labradoodle"
                    ),
                    numberCorrect = 0,
                    numberAnswered = 0,
                    hasError = false,
                    gameOver = false,
                )
            )
        )
    }
}