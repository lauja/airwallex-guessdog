package com.jasonlau.guessdog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasonlau.guessdog.data.BreedData
import com.jasonlau.guessdog.data.Status
import com.jasonlau.guessdog.repository.GuessDogRepository
import com.jasonlau.guessdog.util.BreedMapTransformer
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
    private val mutableViewState: MutableStateFlow<GuessDogContract.ViewState> = MutableStateFlow(
        GuessDogContract.ViewState.Loading,
    )

    val viewState: StateFlow<GuessDogContract.ViewState> = mutableViewState.asStateFlow()

    fun getDogData() {
        viewModelScope.launch {
            val allBreedsResponse = guessDogRepository.getAllBreeds()
            val randomBreedImage = guessDogRepository.getRandomBreedImage()

            if (allBreedsResponse.status != Status.SUCCESS || randomBreedImage.status != Status.SUCCESS) {
                mutableViewState.update {
                    GuessDogContract.ViewState.Error
                }
            } else {
                val allBreeds = breedMapTransformer.transformBreedsResponseToDisplayMap(allBreedsResponse.breeds)
                mutableViewState.update {
                    val correctAnswerKey = getBreedFromRandomImageUrl(randomBreedImage.imageUrl)
                    // something wrong with extracting the breed name from the URL,
                    // or breed does not exist in the all breeds map
                    if (correctAnswerKey == null || allBreeds[correctAnswerKey] == null) {
                        GuessDogContract.ViewState.Error
                    } else {
                        GuessDogContract.ViewState.Content(
                            BreedData(
                                imageUrl = randomBreedImage.imageUrl,
                                breedAnswers = randomBreedChooser.chooseRandomBreedLabels(
                                    numberOfNamesToChoose = NUMBER_OF_POSSIBLE_ANSWERS - 1, // excludes correct answer
                                    correctAnswerKey = correctAnswerKey,
                                    breedMap = allBreeds
                                ),
                                correctAnswer = allBreeds[correctAnswerKey]!!
                            )
                        )
                    }
                }
            }
        }
    }

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
        mutableViewState.value.contentStateOrNull?.let { content ->
            mutableViewState.update {
                GuessDogContract.ViewState.Content(
                    data = content.data,
                    numberCorrect = content.numberCorrect + if (selected === correctAnswer) 1 else 0,
                    numberAnswered = content.numberAnswered + 1
                )
            }
        }
    }

    companion object {
        private const val NUMBER_OF_POSSIBLE_ANSWERS = 4
    }
}