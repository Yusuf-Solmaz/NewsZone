<h1 align="center">
NewsZone 
</h1>

<p align="center">
  <img src="https://img.shields.io/badge/-Kotlin-7c6fe1?style=flat&logo=kotlin&logoColor=white">
  <img src="https://img.shields.io/badge/Jetpack_Compose-4285f4?style=flat&logo=jetpackcompose&logoColor=white">
</p>

<div align="center">
  
  [![Contributors][contributors-shield]][contributors-url]
  [![Forks][forks-shield]][forks-url]
  [![Stargazers][stars-shield]][stars-url]
  [![Issues][issues-shield]][issues-url]

</div>

  NewsZone is a news reading application that allows users to follow daily news, search for news, save news, customize the homepage, and many other features.


<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#key-features">Key Features</a>
    </li>
    <li><a href="#screenshots">Screenshots</a></li>
    <li><a href="#demo">Demo</a></li>
    <li><a href="#open-source-libraries">Open-Source Libraries</a></li>
    <li><a href="#architecture">Architecture</a></li>
    <li><a href="#api-and-services">API and Services</a></li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#configuration-steps">Configuration Steps</a></li>
      </ul>
    </li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#licence">Licence</a></li>
  </ol>
</details>

<!-- KEY FEATURES -->
## Key Features
* Determine Category: NewsZone helps you set the category of news to be displayed on the homepage. You can change this category at any time.
* Navigate the News: Get breaking news and read news in the category of your choice. 
* Check News Details: Browse the details of the news you clicked on. Read as you wish.
* Get the News Summary: NewsZone works with Gemini to allow you to get a summary of the news without reading the full story.
* Save and Delete News: Save news stories you like or want to read later, or delete them if you no longer need them.
* Share the News: You can go to the web page of the news you want or share the news with your friends.
* Search for News: Search news by popularity, publication time, relevance, title, description, content and date.
* Get the Latest News with Notification: NewsZone ensures you get the latest news every hour with a notification.


<!-- Screenshots -->
## Screenshots
| Splash          | Onboarding                      | Determine Category                    | Home         | News Detail        | News Summary        | Search  
|-----------------------------------|-----------------------------------|-----------------------------------|-----------------------------------|-----------------------------------|-----------------------------------|-----------------------------------|
| <img src="https://github.com/user-attachments/assets/47b2a159-3340-4544-8f58-b052e85a65b7" width="180"/>| <img src="https://github.com/user-attachments/assets/85ad90b4-cf9e-47c9-aa5c-7da42d1cddee" width="180"/>|<img src="https://github.com/user-attachments/assets/de08802b-9eb5-40c7-921d-23688b66ab40" width="180"/>| <img src="https://github.com/user-attachments/assets/99091ba4-8124-48cf-8e17-a40b9a9e28a3" width="180"/>| <img src="https://github.com/user-attachments/assets/def2701d-a9d0-4987-8283-a2ae4d1973b3" width="180"/>| <img src="https://github.com/user-attachments/assets/6c98d06f-20f7-41c6-96f3-de1ae81ba1bb" width="180"/>| <img src="https://github.com/user-attachments/assets/f81cb867-ba95-4c50-b108-a8fb062d9984" width="180"/> 

| Options                  | Players                   | Add Player Dialog             | Create Competition        | Competition Detail    | Saved Competition | Delete Saved Competition
|-----------------------------------|-----------------------------------|-----------------------------------|-----------------------------------|-----------------------------------|-----------------------------------|-----------------------------------|
|<img src="/screenshots/Screenshot_options_page.png" width="220"/> | <img src="/screenshots/Screenshot_players_page.png" width="220"/> | <img src="/screenshots/Screenshot_add_player_page.png" width="260"/> | <img src="/screenshots/Screenshot_create_competition_page.png" width="150"/> | <img src="/screenshots/Screenshot_competition_detail_page.png" width="120"/> | <img src="/screenshots/Screenshot_saved_competition_page.png" width="130"/>| <img src="/screenshots/Screenshot_delete_dialog.png" width="120"/>

<!-- Demo -->
## Demo



https://github.com/user-attachments/assets/617e756c-7909-444a-855b-13bff5ca1f2b



<!-- Open-Source Libraries -->
## Open-Source Libraries
* Minimum SDK level 26
* [Dependency Injection (Hilt) (2.51.1)](https://developer.android.com/training/dependency-injection/hilt-android) - Used for dependency injection, simplifying the management of application components.
* [Jetpack Compose (1.9.1)](https://developer.android.com/develop/ui/compose) - A modern toolkit for building native UI in Android.
* [Navigation (1.2.0)](https://developer.android.com/develop/ui/compose/navigation) - Handles in-app navigation in a type-safe manner.
* [Compose Lifecycle (2.8.4)](https://developer.android.com/develop/ui/compose/lifecycle) - Manages lifecycle-aware components in Jetpack Compose.
* [Coroutines](https://developer.android.com/kotlin/coroutines?hl=tr) - Provides a simple way to manage background threads, making asynchronous programming easier and more efficient.
* [Flow](https://developer.android.com/kotlin/flow) - A reactive streams API in Kotlin used for managing data streams asynchronously.
* [DataStore (1.1.1)](https://developer.android.com/topic/libraries/architecture/datastore) - Handles data storage and persistence, replacing SharedPreferences for more complex data structures.
* [MVVM](https://developer.android.com/topic/libraries/architecture/viewmodel#implement) - A design pattern used to separate concerns, making the application more modular, testable, and maintainable.
  * [Lifecycle (2.8.4)](https://developer.android.com/topic/libraries/architecture/lifecycle) - Manages Android lifecycle-aware components.
  * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores
      UI-related data that isn't destroyed on UI changes.
  * [UseCases](https://developer.android.com/topic/architecture/domain-layer) - Located domain
      layer that sits between the UI layer and the data layer.
  * [Repository](https://developer.android.com/topic/architecture/data-layer) - Located in the data
      layer that contains application data and business logic.
* [Retrofit (2.11.0)](https://square.github.io/retrofit/) A type-safe HTTP client for Android and Java
* [OkHttp (5.0.0-alpha.14)](https://square.github.io/okhttp/) An HTTP client that efficiently makes network requests
* [Gson (2.11.0)](https://mvnrepository.com/artifact/com.google.code.gson/gson) - A library for serializing and deserializing JSON data.
* [Firebase](https://firebase.google.com/) - A suite of tools used for backend services including authentication, Firestore database, storage, crash reporting, analytics, and performance monitoring.
    * [Firebase Authentication (23.0.0)](https://firebase.google.com/docs/auth) Firebase Authentication
      provides backend services, easy-to-use SDKs, and ready-made UI libraries to authenticate users
      to your app.
    * [Firebase Firestore (25.0.0)](https://firebase.google.com/docs/firestore) Cloud Firestore is a
      flexible, scalable database for mobile, web, and server development from Firebase and Google
      Cloud.
    * [Firebase Storage (21.0.0)](https://firebase.google.com/docs/storage?hl=en)
    * [Firebase Crashlytics (19.0.3)](https://firebase.google.com/docs/crashlytics) Firebase Crashlytics is a
      lightweight, real-time crash reporter that helps you track, prioritize, and fix stability
      issues that erode your app quality.
    * [Firebase Performance (21.0.1)](https://firebase.google.com/docs/perf-mon?hl=en)
    * [Firebase Analytics (22.0.2)](https://firebase.google.com/docs/analytics) Firebase Analytics is a free
      app measurement solution that provides insight on app usage and user engagement.
* [Location (21.3.0)](https://developers.google.com/android/guides/setup?hl=en) - Used for obtaining device location data.
* [SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences) Store
  private primitive data in key-value pairs. Standard Android library with no specific version (comes with the Android SDK).
* [Lottie Animation (6.3.0)](https://lottiefiles.com/blog/working-with-lottie-animations/getting-started-with-lottie-animations-in-android-app) - Used to display animations in the app.
* [Coil (2.6.0)](https://coil-kt.github.io/coil/compose/) - An image loading library for Android backed by Kotlin Coroutines.
  
<!-- Architecture -->
## Architecture
This Android app uses the MVVM (Model-View-ViewModel) pattern and Clean Architecture principles, organized into four main modules for better scalability and maintainability.

MVVM

- Model: Manages data and business logic, separate from the UI.
- View: Displays the data and interacts with the user.
- ViewModel: Connects the View and Model, handling UI-related logic and state management.

Clean Architecture & Multi Module
- App Module: The core module that integrates all other modules and provides the main entry point of the application.
- Data Module: Handles data sources, such as APIs and databases, and provides data to the Domain Layer.
- Domain Module: Contains the core business logic and use cases, which are independent of external frameworks.
- Feature Module: Encapsulates the app's features, allowing for modular development and testing of individual functionalities.
  
![image](https://github.com/user-attachments/assets/eb3bf886-2376-4cb6-9234-ece71d036a68)

<!-- API and Services -->
## API and Services
The application integrates with the following APIs and services:
* OpenWeather API: Provides weather data and forecasts. More information can be found [here](https://openweathermap.org/api).
* Restful API: Utilized for data communication and integration to get weather data.
* Retrofit: A type-safe HTTP client for Android, used for interacting with RESTful APIs.
* OkHttp: An HTTP client used for making network requests and handling responses efficiently.
* Firebase: Used for various backend services, including:
  * Authentication: User sign-in and management.
  * Firestore Database: Real-time database for storing and syncing app data.
  * Storage: File storage and retrieval.
  * Crashlytics: Real-time crash reporting and diagnostics.
  * Performance Monitoring: Monitoring and optimizing app performance.
  * Analytics: Data collection and insights on app usage.
* Google Services: Used for location services, allowing the app to access and manage location data. [More detail](https://developers.google.com/android/reference/com/google/android/gms/common/package-summary)

<!-- GETTING STARTED -->
## Getting Started
  * If You Want to Run the App on Android Studio:

1. Clone this repository to your preferred directory using the following command:

```
git clone https://github.com/Yusuf-Solmaz/TeamMaker
```
2. Open the cloned project in Android Studio.

    ### Configuration Steps

1. Create `secrets.properties` file in the project directory.

2. Add the following line with your API key to `secrets.properties`:
```
API_KEY=YOUR_API_KEY_HERE
```
3. Build and run the app on an emulator or a physical device.

----------------------------------------------------------------

* Or you can download the APK of the application [here](https://drive.google.com/file/d/1HBI3oGsVa8FhTK1ZWQVRXkq3W8uqYREe/view?usp=sharing)
  
<!-- Contact Section -->
## Contact

<table style="border-collapse: collapse; width: 100%;">
  <tr>
    <td style="padding-right: 10px;">Bengisu Şahin - <a href="mailto:bengisusaahin@gmail.com">bengisusaahin@gmail.com</a></td>
    <td>
      <a href="https://www.linkedin.com/in/bengisu-sahin/" target="_blank">
        <img src="https://img.shields.io/badge/linkedin-%231E77B5.svg?&style=for-the-badge&logo=linkedin&logoColor=white" alt="linkedin" style="vertical-align: middle;" />
      </a>
    </td>
  </tr>
  <tr>
    <td style="padding-right: 10px;">Talha Çalışır - <a href="mailto:talha5797@gmail.com">talha5797@gmail.com</a></td>
    <td>
      <a href="https://www.linkedin.com/in/talhatlc/" target="_blank">
        <img src="https://img.shields.io/badge/linkedin-%231E77B5.svg?&style=for-the-badge&logo=linkedin&logoColor=white" alt="linkedin" style="vertical-align: middle;" />
      </a>
    </td>
  </tr>
  <tr>
    <td style="padding-right: 10px;">Yusuf Mücahit Solmaz - <a href="mailto:yusufmucahitsolmaz@gmail.com">yusufmucahitsolmaz@gmail.com</a></td>
    <td>
      <a href="https://www.linkedin.com/in/yusuf-mucahit-solmaz/" target="_blank">
        <img src="https://img.shields.io/badge/linkedin-%231E77B5.svg?&style=for-the-badge&logo=linkedin&logoColor=white" alt="linkedin" style="vertical-align: middle;" />
      </a>
    </td>
  </tr>
</table>

<!-- LICENCE -->
## Licence
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/Yusuf-Solmaz/TeamMaker.svg?style=for-the-badge
[contributors-url]: https://github.com/Yusuf-Solmaz/TeamMaker/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/Yusuf-Solmaz/TeamMaker.svg?style=for-the-badge
[forks-url]: https://github.com/Yusuf-Solmaz/TeamMaker/network/members
[stars-shield]: https://img.shields.io/github/stars/Yusuf-Solmaz/TeamMaker.svg?style=for-the-badge
[stars-url]: https://github.com/Yusuf-Solmaz/TeamMaker/stargazers
[issues-shield]: https://img.shields.io/github/issues/Yusuf-Solmaz/TeamMaker.svg?style=for-the-badge
[issues-url]: https://github.com/Yusuf-Solmaz/TeamMaker/issues
[license-shield]: https://img.shields.io/github/license/Yusuf-Solmaz/TeamMaker.svg?style=for-the-badge
[license-url]: https://github.com/Yusuf-Solmaz/TeamMaker/blob/master/LICENSE.txt

<!-- [linkedin-shield]: https://img.shields.io/badge/linkedin-%231E77B5.svg?&style=for-the-badge&logo=linkedin&logoColor=white alt=linkedin style="margin-bottom: 5px;"
[linkedin-url]: https://www.linkedin.com/in/bengisu-sahin/
[linkedinT-url]: https://www.linkedin.com/in/talhatlc/
[linkedinY-url]: https://www.linkedin.com/in/yusuf-mucahit-solmaz/--!>
