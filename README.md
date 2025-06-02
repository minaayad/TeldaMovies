# 🎬 Movie Explorer App

Movie Explorer is a modern Android application built with **Jetpack Compose**, **MVVM architecture**, **Hilt for dependency injection**, **Retrofit for networking**, and **Room for local database storage**. It allows users to browse popular movies, search for movies, view detailed information, and manage a watchlist.

## 📱 Screenshots

<div align="center" style="display: flex; gap: 10px; flex-wrap: wrap;">
  <img src="https://github.com/user-attachments/assets/acf62a55-233c-412a-a25b-7ecd68258d56" alt="Screenshot 1" width="250"/>
  <img src="https://github.com/user-attachments/assets/c55a7e84-8457-41c9-970a-c2533488397f" alt="Screenshot 2" width="250"/>
  <img src="https://github.com/user-attachments/assets/c0a293b3-8c50-46e8-8d1a-069d0e5ce7ab" alt="Screenshot 3" width="250"/>
</div>


## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Architecture**: MVVM
- **Dependency Injection**: Hilt
- **Networking**: Retrofit
- **Image Loading**: Coil
- **Database**: Room
- **API**: [The Movie Database (TMDB)](https://www.themoviedb.org/)

## 🚀 Features

- Browse popular movies
- Search for any movie
- View detailed movie information including:
  - Title, Overview, Tagline, Poster
  - Revenue, Release Date, Status
- See similar movies (max 5)
- View grouped cast members of similar movies (Top 5 Actors and Top 5 Directors by popularity)
- Add/remove movies from a persistent watchlist (stored locally with Room)

## 🧩 Architecture Overview

- **ViewModel**: Handles UI state, calls repository
- **Repository**: Mediates between ViewModel and API/local DB
- **Retrofit API**: Fetches data from TMDB
- **Room**: Stores watchlist locally
- **Composable UI**: Built using Jetpack Compose

## 📝 Setup Instructions

1. Clone the repo:
   ```bash
   git clone https://github.com/minaayad/TeldaMovies.git
