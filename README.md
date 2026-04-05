# my-android-messenger
just a study project


# 📱 Messenger App (Jetpack Compose)

A simple messenger application built with **Kotlin** and **Jetpack Compose**.
This project was created as part of an intensive Android learning process with focus on **modern Android architecture** and **UI development**.

---

## 🚀 Features

* 📋 Contacts list
* 🔍 Search contacts
* 💬 Chat screen with messages
* ➕ Send messages
* 🤖 Fake auto-replies
* 🗑 Delete/edit messages
* ⚙️ Message options screen
* 🔄 Reactive UI (state updates instantly)

---

## 📸 Screenshots

> <img width="200" height="600" alt="image" src="https://github.com/user-attachments/assets/790111e7-33e2-4853-a45b-9ec1b1f60170" />
> <img width="200" height="600" alt="image" src="https://github.com/user-attachments/assets/b607c004-407d-4c5f-93b3-d76b16f6d7eb" />
> <img width="200" height="600" alt="image" src="https://github.com/user-attachments/assets/3fffbf24-b4e3-4499-836d-4d55c95f3e4c" />

---

## 🏗 Architecture

The app follows a simplified **MVVM (Model-View-ViewModel)** architecture:

* **UI (Jetpack Compose)**
  Stateless composables that receive data and callbacks

* **ViewModel**
  Handles state, business logic, and UI updates

* **State Management**
  Uses `mutableStateOf` and `mutableStateListOf` for reactive UI

---

## 🔄 State Management

* Single source of truth (SSOT) via `UiState`
* State hoisting (UI does not own state)
* Immutable updates using `copy()`

Example:

```kotlin
uiState = uiState.copy(
    contacts = updatedList
)
```

---

## 🧠 Key Concepts Practiced

* Jetpack Compose basics (LazyColumn, TextField, Buttons)
* Navigation with arguments
* State management in Compose
* ViewModel lifecycle
* Coroutines (`viewModelScope`, `launch`, `delay`)
* Clean UI separation (UI vs logic)
* Event-driven architecture

---

## 🛠 Tech Stack

* **Kotlin**
* **Jetpack Compose**
* **Android ViewModel**
* **Coroutines**
* **Navigation Compose**

---

## 🔧 Future Improvements

* ☁️ Backend integration (Firebase / REST API)
* 🤖 AI integration
* 💾 Local storage (Room)
* 🔔 Push notifications
* 🧱 Dependency Injection (Hilt)

---

## 💡 Learning Goals

This project was built to:

* Understand modern Android development
* Practice clean architecture principles
* Learn state management in Compose
* Prepare for Android developer interviews

