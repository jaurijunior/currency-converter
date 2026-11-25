# 💱 Currency Converter

A modern Android currency conversion application built with **Kotlin**, **Jetpack Compose**, **Clean Architecture**, and **Hilt**. The app fetches real-time exchange rates using **Ktor Client** and displays conversion results seamlessly with a clean Material 3 UI.

---

## ✨ Features

- 🔄 **Real-Time Currency Conversion**: Instant exchange rate calculation between supported currencies (USD, EUR, BRL, etc.).
- 🎨 **Modern UI**: Clean Material 3 design with Jetpack Compose, edge-to-edge layout support, and smooth state updates.
- 📱 **Reactive State Management**: Uses Kotlin `StateFlow` and Unidirectional Data Flow (UDF) / MVI pattern to handle form events and UI state.
- 🏗️ **Clean Architecture**: Decoupled Data, Domain, and UI layers for maintainability and testability.

---

## 🛠️ Tech Stack & Architecture

- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Architecture**: Clean Architecture + MVVM/MVI with Unidirectional Data Flow
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/)
- **Networking**: [Ktor Client](https://ktor.io/) (`ktor-client-okhttp`, `ktor-client-content-negotiation`, `ktor-serialization-kotlinx-json`)
- **JSON Serialization**: `kotlinx.serialization`
- **Asynchronous & Reactive**: Kotlin Coroutines & `StateFlow`
- **Build System**: Gradle Version Catalogs (`libs.versions.toml`) with Kotlin DSL

---

## 🏛️ Project Structure

```text
com.example.currencyconverter
├── data
│   ├── di               # Hilt modules (NetworkModule, DataModel)
│   ├── network          # Ktor client & API models (ExchangeRate API)
│   └── repository       # CurrencyRepository implementation
├── domain
│   ├── model            # Core domain entities
│   └── repository       # CurrencyRepository contract
└── ui
    ├── components       # Reusable Compose UI components (CurrencyField, CurrencySelector)
    ├── feature
    │   └── converter    # Screen, ViewModel, and Form States/Events
    └── theme            # Material 3 colors, typography, and theme configuration
