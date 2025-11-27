# 🌤️ Plan radar Task (Weather App)

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

A modern Android application built with **Kotlin** and **Jetpack Compose**, following a **modular MVI
architecture** for scalability, maintainability, and clean separation of concerns.
<p>
      <img src="res/1.png" alt="Shimmer Effect" width="200"/>
      <img src="res/2.png" alt="Shimmer Effect" width="200"/>
      <img src="res/3.png" alt="Shimmer Effect" width="200"/>
<br/>
     <img src="res/4.png" alt="Shimmer Effect" width="200"/>
      <img src="res/5.png" alt="Shimmer Effect" width="200"/>
     <img src="res/6.png" alt="Shimmer Effect" width="200"/>
</p>
#### 🔒 Security Check
     <img src="res/7.png" alt="Shimmer Effect" width="200"/>

---

## 🧩 Tech Stack

- **Kotlin** – Primary language
- **Jetpack Compose** – Declarative and reactive UI
- **Material 3** – Modern design system
- **Hilt** – Dependency Injection
- **Retrofit + OkHttp + Gson** – Networking and JSON parsing
- **Kotlin Coroutines & Flow** – Asynchronous and reactive data handling
- **Room (Database Module)** – Local caching with **offline-first** support
- **Coil** – Image loading
- **JUnit4, MockK, Turbine** – Testing stack

---

## 🧠 Architecture

- Based on **MVI (Model–View–Intent)** for reactive state management.
- Uses a **multi-module architecture**, where each feature and core functionality is separated into
  independent modules for scalability and faster builds.
- Includes a dedicated **`database` module** that provides local data caching through **Room**, supporting an
  **offline-first** experience.
- Follows **clean architecture principles**, separating **UI**, **domain**, and **data** layers clearly.

<p align="start">
  <img src="res/architecture.png" alt="Architecture For Characters App"/>
</p>

---

## ⚙️ Highlights

- [x] Reactive UI built with **Compose + Flow**
- [x] Scalable **feature-based modular structure**
- [x] **Offline-first** approach using Room and DataStore
- [x] Type-safe networking with **Retrofit + Gson**
- [x] Dependency management with **Hilt**
- [x] Clean, maintainable, and well-documented codebase
- [x]  Security Check for emulator or root detect
- [x]   Built-in **CI/CD** pipeline for continuous integration and testing

