<h1 align="center">
NewsZone 
</h1>

<p align="center">
  <img src="https://img.shields.io/badge/-Kotlin-7c6fe1?style=flat&logo=kotlin&logoColor=white">
  <img src="https://img.shields.io/badge/Jetpack_Compose-4285f4?style=flat&logo=jetpackcompose&logoColor=white">
  <img alt="API" src="https://img.shields.io/badge/API-26%2B-orange.svg?style=flat"/>
</p>


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
    <li><a href="#api">API</a></li>
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
* Navigate Offline: NewsZone allows you to read news even when you are not connected to the internet.


<!-- Screenshots -->
## Screenshots
| Splash  | Onboarding  | Determine Category  
|----|---|-----|
| <img src="https://github.com/user-attachments/assets/31204518-8935-4849-a043-2def43df94ac" width="250" height="500"/>| <img src="https://github.com/user-attachments/assets/85ad90b4-cf9e-47c9-aa5c-7da42d1cddee" width="250" height="500"/>|<img src="https://github.com/user-attachments/assets/de08802b-9eb5-40c7-921d-23688b66ab40" width="250" height="500"/>

| Home  | News Detail   | News Summary
|--|-------------|--|
| <img src="https://github.com/user-attachments/assets/99091ba4-8124-48cf-8e17-a40b9a9e28a3" width="250" height="500"/>| <img src="https://github.com/user-attachments/assets/def2701d-a9d0-4987-8283-a2ae4d1973b3" width="250" height="500"/>| <img src="https://github.com/user-attachments/assets/6c98d06f-20f7-41c6-96f3-de1ae81ba1bb" width="250" height="500"/>

| Search  | Search Options | Saved News
|--|-------------|--|
| <img src="https://github.com/user-attachments/assets/f81cb867-ba95-4c50-b108-a8fb062d9984" width="250" height="500"/> | <img src="https://github.com/user-attachments/assets/82189ed5-dd69-428a-981c-ce7a82f9d416" width="250" height="500"/> | <img src="https://github.com/user-attachments/assets/066f87bb-cac1-4149-8b93-c087822326e3" width="250" height="500"/> 



| Settings | Light Mode | Language Exchange | Notification
|--|-------------|--|----|
| <img src="https://github.com/user-attachments/assets/cbf83f3b-7d65-4cf0-a38b-b5da1276a092" width="250" height="500"/> | <img src="https://github.com/user-attachments/assets/14d08b10-1601-4bf3-a641-d13693081e93" width="250" height="500"/> | <img src="https://github.com/user-attachments/assets/b68db404-e07c-4d12-9254-d2aaca60bf59" width="250" height="500"/> | <img src="https://github.com/user-attachments/assets/6744efc2-2997-4e72-9cdd-cbbd5cbd4365" width="200" height="100"/> 

<!-- Demo -->
## Demo

<div align="center">
  https://github.com/user-attachments/assets/809e3f54-9f0c-4cf6-9724-9453ce0d2ab0
</div>


<!-- Open-Source Libraries -->
## Open-Source Libraries

* [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) - Used for managing and scheduling deferrable, asynchronous tasks that are expected to run even if the app is closed or the device restarts.
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Used for dependency injection, simplifying the management of application components.
* [Jetpack Compose](https://developer.android.com/develop/ui/compose) - A modern toolkit for building native UI in Android.
* [Navigation](https://developer.android.com/develop/ui/compose/navigation) - Handles in-app navigation in a type-safe manner.
* [Coroutines](https://developer.android.com/kotlin/coroutines?hl=tr) - Provides a simple way to manage background threads, making asynchronous programming easier and more efficient.
* [Flow](https://developer.android.com/kotlin/flow) - A reactive streams API in Kotlin used for managing data streams asynchronously.
* [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Handles data storage and persistence, replacing SharedPreferences for more complex data structures.
* [MVVM](https://developer.android.com/topic/libraries/architecture/viewmodel#implement) - A design pattern used to separate concerns, making the application more modular, testable, and maintainable.
  * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - Manages Android lifecycle-aware components.
  * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  * [UseCases](https://developer.android.com/topic/architecture/domain-layer) - Located domain layer that sits between the UI layer and the data layer.
  * [Repository](https://developer.android.com/topic/architecture/data-layer) - Located in the data layer that contains application data and business logic.
* [Retrofit](https://square.github.io/retrofit/) A type-safe HTTP client for Android and Java
* [OkHttp](https://square.github.io/okhttp/) An HTTP client that efficiently makes network requests
* [Gson](https://mvnrepository.com/artifact/com.google.code.gson/gson) - A library for serializing and deserializing JSON data.
* [Lottie Animation](https://lottiefiles.com/blog/working-with-lottie-animations/getting-started-with-lottie-animations-in-android-app) - Used to display animations in the app.
* [Coil](https://coil-kt.github.io/coil/compose/) - An image-loading library for Android backed by Kotlin Coroutines.
* [Gemini](https://ai.google.dev/gemini-api/docs?hl=en) - A powerful AI model developed by Google, designed to assist with natural language understanding, generation, and multimodal tasks, providing advanced capabilities for building intelligent applications.
  
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
- Presentation (Feature) Module: Encapsulates the app's features, allowing for modular development and testing of individual functionalities.
  
![image](https://github.com/user-attachments/assets/eb3bf886-2376-4cb6-9234-ece71d036a68)

<!-- API and Services -->
## API
The application integrates with the following APIs:
* News API: Provides news data. More information can be found [here](https://newsapi.org/).
* Gemini API: Provides a summary of the news. More information can be found [here](https://ai.google.dev/gemini-api/docs?hl=en).

<!-- GETTING STARTED -->
## Getting Started
  * If You Want to Run the App on Android Studio:

1. Clone this repository to your preferred directory using the following command:

```
git clone https://github.com/Yusuf-Solmaz/NewsZone
```
2. Open the cloned project in Android Studio.

    ### Configuration Steps

1. Create `api.properties` file in the project directory.

2. Add the following line with your API keys (for news and Gemini API ) and  to `api.properties`:
```
API_KEY=YOUR_API_KEY_HERE
GEMINI_API_KEY=YOUR_GEMINI_API_KEY_HERE
```
3. Build and run the app on an emulator or a physical device.

----------------------------------------------------------------
  
<!-- Contact Section -->
## Contact

<table style="border-collapse: collapse; width: 100%;">
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
