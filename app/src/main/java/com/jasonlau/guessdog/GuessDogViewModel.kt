package com.jasonlau.guessdog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasonlau.guessdog.data.BreedData
import com.jasonlau.guessdog.data.RandomBreedImageResponse
import com.jasonlau.guessdog.data.Status
import com.jasonlau.guessdog.repository.GuessDogRepository
import com.jasonlau.guessdog.util.BreedMapTransformer
import com.jasonlau.guessdog.util.RandomBreedChooser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuessDogViewModel @Inject constructor(
    private val guessDogRepository: GuessDogRepository,
    private val breedMapTransformer: BreedMapTransformer,
    private val randomBreedChooser: RandomBreedChooser,
) : ViewModel() {
    private val mutableViewState: MutableStateFlow<GuessDogUiState> = MutableStateFlow(
        GuessDogUiState(),
    )

    val viewState: StateFlow<GuessDogUiState> = mutableViewState.asStateFlow()

    fun getDogData(
        refreshScores: Boolean = true
    ) {
        viewModelScope.launch {
            mutableViewState.update {
                mutableViewState.value.copy(
                    isLoading = true,
                    hasError = false,
                )
            }

            val allBreedsResponse = guessDogRepository.getAllBreeds()
            val randomBreedImage = guessDogRepository.getRandomBreedImage()

            if (allBreedsResponse.status != Status.SUCCESS || randomBreedImage.status != Status.SUCCESS) {
                mutableViewState.update {
                    mutableViewState.value.copy(
                        isLoading = false,
                        hasError = true,
                    )
                }
            } else {
                val allBreeds = breedMapTransformer.transformBreedsResponseToDisplayMap(allBreedsResponse.breeds)
                mutableViewState.update {
                    val correctAnswerKey = getBreedFromRandomImageUrl(randomBreedImage.imageUrl)
                    // something wrong with extracting the breed name from the URL,
                    // or breed does not exist in the all breeds map
                    if (correctAnswerKey == null || allBreeds[correctAnswerKey] == null) {
                        mutableViewState.value.copy(
                            isLoading = false,
                            hasError = true,
                        )
                    } else {
                        getBreedContent(randomBreedImage, allBreeds, correctAnswerKey, refreshScores)
                    }
                }
            }
        }
    }

    private fun getBreedContent(
        randomBreedImage: RandomBreedImageResponse,
        allBreeds: Map<String, String>,
        correctAnswerKey: String,
        refreshScores: Boolean
    ) =
        mutableViewState.value.copy(
            isLoading = false,
            data =
                BreedData(
                    imageUrl = randomBreedImage.imageUrl,
                    breedAnswers = randomBreedChooser.chooseRandomBreedLabels(
                        numberOfNamesToChoose = NUMBER_OF_POSSIBLE_ANSWERS - 1, // excludes correct answer
                        correctAnswerKey = correctAnswerKey,
                        breedMap = allBreeds
                    ),
                    correctAnswer = allBreeds[correctAnswerKey]!!
                ),
            numberCorrect = if (refreshScores) 0 else mutableViewState.value.numberCorrect,
            numberAnswered = if (refreshScores) 0 else mutableViewState.value.numberAnswered,
            hasError = false,
            gameOver = if (refreshScores) false else mutableViewState.value.gameOver,
        )

    /**
     * Note: doesn't seem to work for danish-swedish-farmdog!
     * https://images.dog.ceo/breeds/danish-swedish-farmdog/ebba_004.jpg
     */
    private fun getBreedFromRandomImageUrl(imageUrl: String): String? {
        return try {
            // eg. https://images.dog.ceo/breeds/terrier-yorkshire/n02094433_3526.jpg
            val urlSplit = imageUrl.split("/")
            urlSplit[urlSplit.size - 2] // eg. 2nd path segment from the end
        } catch (ex: IndexOutOfBoundsException) {
            null
        }
    }

    fun onAnswerSelected(selected: String, correctAnswer: String) {
        mutableViewState.update {
            val numberAnswered = mutableViewState.value.numberAnswered + 1
            mutableViewState.value.copy(
                numberCorrect = mutableViewState.value.numberCorrect + if (selected == correctAnswer) 1 else 0,
                numberAnswered = numberAnswered,
                gameOver = numberAnswered == NUMBER_OF_QUESTIONS
            )
        }
    }

    companion object {
        private const val NUMBER_OF_POSSIBLE_ANSWERS = 4
        private const val NUMBER_OF_QUESTIONS = 10
    }
}