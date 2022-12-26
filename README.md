## MetFlix

# Overview

Thank you for your interest in joining the Golden Equator tech team. You have been
shortlisted for the first phase of our hiring process. Please complete the following exercise.
Challenge Instructions (Android Developer)
1. Design and develop the required screens (Kotlin)
2. Follow standard architectural patterns (preferably MVVM)
3. Use material design components and follow UI/UX guidelines
4. Layout should be user friendly
5. Make sure your progress is properly conveyed through local git commit history
   Feel free to reach out to us if you have any questions regarding the assignment.
   Important Note
   API Key - 0e7274f05c36db12cbe71d9ab0393d47
   Pass the API Key to all the APIs by adding {api_key} parameter to each end-point
   Example
   https://api.themoviedb.org/3/movie/550?api_key=0e7274f05c36db12cbe71d9ab0393d47
   
#  Home Page
   The user is expected to land on this page every time the app is opened. Home page consists
   of 4 tabs that lists movies as listed below,

1. Now Playing -> https://api.themoviedb.org/3/movie/now_playing?page=1
2. Popular -> https://api.themoviedb.org/3/movie/popular?page=1
3. Top Rated -> https://api.themoviedb.org/3/movie/top_rated?page=1
4. Upcoming -> https://api.themoviedb.org/3/movie/upcoming?page=1

#   Genre
   Use Genre API to fetch the list of available genres and store them locally to map them to the
   movie objects before displaying the data
   https://api.themoviedb.org/3/genre/movie/list

#   Images
   Backdrop, Poster image paths can be loaded using any image loading library by prefixing the
   path with https://image.tmdb.org/t/p/original
   Path -> /ysJte1iqN8pFQ470tumnViB1wHP.jpg
   Image URL -> https://image.tmdb.org/t/p/original/ysJte1iqN8pFQ470tumnViB1wHP.jpg

#   Requirements
1. Endless scrolling to be enabled on each page, {page} param can be modified on the
   API to get results for different pages
2. Each movie card should be designed to show information such as, poster, title,
   genre, release date (dd/mmm/yyyy), vote average and vote count
3. Clicking on a movie card should redirect the user to the movie details page