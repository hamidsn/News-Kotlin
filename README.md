# Task

News app to show articles and allows the user to save the news for reading later.

    ● API: https://newsapi.org/ to fetch data

    ● Headlines: screen to display a list of headlines based on the user-selected sources
        -Tapping on a row should open the headline URL to read the full article within the app.
        -When viewing an article the user should be able to save it for reading later.

    ● Sources: screen to display the list of available sources for articles, the user should be able to select multiple sources and the selection should persist user sessions. List of sources come from an API.

    ● Saved: screen to display a list of previously saved headlines, tapping a row should open the article for reading, the same way as the Headlines screen.

    ● User should be able to delete previously saved articles

    ● Use a json payload.

    ● Previously saved articles should persist app launches

    ● Unit test your code.

## Installation

Please clone the code and compile it in [Android Studio](https://developer.android.com/studio).

## Requirements

You need your API Key.

## Technologies & Methodologies which used:

- Jetpack Compose
- Coroutines
- Hilt
- Flow
- Coil
- Clean Architecture
- MVVM Pattern
- LiveData
- Espresso
- Room
- Mockk and JUnit
- Accessibility support
- [retrofit](https://square.github.io/retrofit/)
- Required data models are created with the help of [Json to Kotlin convertor](https://json2kt.com/)

## Steps and Technical challenges

1. Making decision on necessary dependencies: Retrofit/API calls/Room
2. Creating News models
3. Creating Dao and Entity for Room
4. Creating functions to add/delete/query data (viewModel)
5. Creating repositories for database and news api call
6. Creating use case for api call to encapsulate logic
7. Creating viewModel based on injected dependencies
8. Load saved news from DB and pass them to composables by flow (viewModel)
9. WebView with back button
10. Creating api calls and updating UI state based on the response (viewModel)
11. Error handling (viewModel)
12. Tabs on Compose
13. Creating navigation graphs for composables
14. Creating composable UI to show lists
15. Creating a actions for composable UI to pass listeners to viewmodel
16. Creating extension function in viewModel to show readable data to user
17. Unit test by creating fake dependencies, injecting them into the viewModel and call some
18. Most of unit tests are in Android folder because of their dependency to Android APIs
19. Manage state with Unidirectional Data Flow: UI state is an immutable snapshot of the details needed for the UI to render might change over time due to user interaction or other events. ViewModel type is the recommended implementation for the management of screen-level UI state with access to the data layer. Furthermore, it survives configuration changes automatically. ViewModel classes define the logic to be applied to events in the app and produce updated state as a result.
20. We use Flow to send data from domain layer to UI layer. There are two actions in our
    repository, `read/add/delete articles and sources` and `call api for articles`. They flow data to vieMModel. ViewModel converts that data to UI states. Follow flowing data, state will be updated and Composable items are observing states. When something is changed in the state, Ui will be automatically updated.


## Feedback

If you have any feedback, please reach out to h_sedghy@yahoo.com

