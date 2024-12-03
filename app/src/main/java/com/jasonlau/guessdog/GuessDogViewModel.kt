package com.jasonlau.guessdog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasonlau.guessdog.data.BreedData
import com.jasonlau.guessdog.data.Status
import com.jasonlau.guessdog.repository.GuessDogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GuessDogViewModel @Inject constructor(
    private val guessDogRepository: GuessDogRepository
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
                val allBreeds = transformBreedsResponseToDisplayMap(allBreedsResponse.breeds)
                mutableViewState.update {
                    GuessDogContract.ViewState.Content(
                        BreedData(
                            imageUrl = randomBreedImage.imageUrl,
                            breedKeyToLabeMap = allBreeds
                        )
                    )
                }
            }
        }
    }

    /**
     * Transforms the response from all breeds to a key-value
     * map of breed to display label
     * eg.
     * 		"appenzeller": [],
     * 		"australian": ["kelpie", "shepherd"],
     * 		"bakharwal": ["indian"],
     *
     *      "appenzeller": "Appenzeller",
     *      "kelpie-australian": "Kelpie Australian"
     *      "shepherd-australian": "Shepherd Australian"
     *      "bakharwal": "Indian Bakharwal"
     */
    private fun transformBreedsResponseToDisplayMap(allBreedsMap: Map<String, Array<String>>): Map<String, String> {
        val keyToDisplayMap = mutableMapOf<String, String>()

        allBreedsMap.entries.forEach { entry ->
            val breed = entry.key
            if (entry.value.isEmpty()) {
                keyToDisplayMap[breed] = breed.capitalise()
            } else {
                entry.value.forEach { subBreed ->
                    keyToDisplayMap["$breed-$subBreed"] = "${subBreed.capitalise()} ${breed.capitalise()}"
                }
            }
        }

        return keyToDisplayMap
    }

    private fun String.capitalise(): String =
        this.replaceFirstChar { firstChar -> if (firstChar.isLowerCase()) firstChar.titlecase(Locale.getDefault()) else firstChar.toString() }
}