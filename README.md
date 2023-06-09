# Android app e-commerce (with Kotlin)

Based on [blocoio/android-template](https://github.com/blocoio/android-template).

## Features

- Tests
  - Unit tests
  - Instrumentation tests (with [Espresso](https://google.github.io/android-testing-support-library/docs/espresso/))
- View injection (with [Butterknife](https://jakewharton.github.io/butterknife/))
- Preconditions
- Google Design library
- Logging (with [Timber](https://github.com/JakeWharton/timber))
- Resource defaults
  - colors.xml
  - stings.xml
  - styles.xml

## Languages, libraries and tools used

- [Kotlin](https://kotlinlang.org/)
- [androidX libraries](https://developer.android.com/jetpack/androidx)
- [Android LifeCycle](https://developer.android.com/topic/libraries/architecture)
- [dagger2](https://github.com/google/dagger)
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Retrofit2](https://github.com/square/retrofit)
- [Glide](https://github.com/bumptech/glide)
- [Room](https://developer.android.com/jetpack/androidx/releases/room)
- [Timber](https://github.com/JakeWharton/timber)
- [DataBinding]

## Implementation

- In this project I'm using [MVVM Pattern](https://developer.android.com/jetpack/docs/guide)
  as an application architecture with these design patterns in mind:-

* Repository Pattern
* Singleton
* Factory
* Adapters

- Using Dagger2 for dependency injection that will make testing easier and our make code
  cleaner and more readable and handy when creating dependecies.
- Using Retrofit library to handle the APIs stuff.
- [Creating Custom ImagesLoader] with Glide to load it into ImageView with no pain of writting a lot of code when load images
- Using Coroutines for managing background threads that can simplify code by reducing the need for callbacks and convert async callbacks
  for long-running tasks, such as database or network access, into sequential code and can automatically manage the life cycle.
- Separation of concerns : The most important principle used in this project to avoid many lifecycle-related problems.
- Each component depends only on the component one level below it.
  For example, activities and fragments depend only on a view model. The repository is the only class that depends on multiple other classes; in this example, the repository depends on a persistent data model and a remote backend data source.
- Using to best of managing ViewState with less complex tools.

## ViewModel LifeCycle

The ViewModel should outlive the View on configuration changes. For instance, on rotation, the Activity gets destroyed and recreated but your ViewModel should not be affected by this. If the ViewModel was to be recreated as well, all the ongoing tasks and cached latest ViewState would be lost.
We use the Architecture Components library to instantiate our ViewModel in order to easily have its lifecycle correctly managed.
