# GuessDog

https://github.com/user-attachments/assets/14ab15d3-f131-4d72-b0c2-4937cd4cc913


The player is asked to guess the dog for the image provided, given the 4 random choices on the screen. After the user selects their preference, the correct answer will be highlighted in green. If the user answered incorrectly, the selected answer will be highlighted in red.

The player will have 10 goes and will be shown a message dialog to inform them that they achieved a perfect score, or be told to practice further.

### Technologies used
* Compose
* Hilt
* Retrofit
* Coil
* Paparazzi

To re-record the Paparazzi screenshots, run:
```
./gradlew app:recordPaparazziDebug
```

Units tests on the viewModel and util classes can be run with:
```
./gradlew app:testDebugUnitTest
```

### Strategy
1. Get a random dog image by going to: https://dog.ceo/api/breeds/image/random
2. The response will contain an image URL eg.
```
{
	"message": "https://images.dog.ceo/breeds/bouvier/n02106382_4291.jpg",
	"status": "success"
}
```
The breed can be obtained by fetching the 2nd path segment from the end, in this case **bouvier**

3. Get all the breed names from: https://dog.ceo/api/breeds/list/all and transform them in to one big map which includes the sub breeds.
eg. 
```
"appenzeller": [],
"australian": ["kelpie", "shepherd"],
"bakharwal": ["indian"],
```

becomes
```
"appenzeller": "Appenzeller",
"australian-kelpie": "Kelpie Australian"
"australian-shepherd": "Shepherd Australian"
"bakharwal-indian": "Indian Bakharwal"
```

4. Use the transformed map to select another 3 answers in addition to the one in the image. Shuffle the answers so that it is not in the same position for each question.

NOTE: while testing, I came across some issues:
- `danish-swedish-farmdog` doesn't seem to exist for https://images.dog.ceo/breeds/danish-swedish-farmdog/ebba_004.jpg, it seems to be just `danish swedish` in the breed list
- some names don't have spaces, so they look a bit out of place, such as `Germanshepherd`
