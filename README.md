# Nexora - Property Management System

Nexora is an Android application designed for managing properties, viewing property listings, and displaying property details and web information.

## Features

- **Authentication**: Secure sign-in system using Shared Preferences.
- **Home Dashboard**: Displays overview metrics such as total revenue, total properties, available units, sold units, and active staff.
- **Property Management**: Explore properties with status tags (Available / Sold), detailed specifications (bedrooms, bathrooms, area size), and marketing contact options.
- **Web Information**: Integrated WebView for property information browsing.
- **Profile & User Settings**: Account details display and safe logout flow with activity stack clearing.

## Tech Stack & Architecture

- **Language**: Kotlin
- **UI Framework**: Android XML Layouts, Material Design 3
- **Navigation**: BottomNavigationView with Fragment switching
- **Architecture**: Single-Activity multi-fragment layout (`MainActivity`) with standalone secondary activities (`AuthActivity`, `DetailActivity`, `PropertyActivity`, `WebViewActivity`, `SplashScreenActivity`)
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
