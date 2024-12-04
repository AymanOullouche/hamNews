---

# HamNews

## Project Overview

HamNews is a Java desktop application that aggregates news articles from specified categories on the website "XXX" using web scraping techniques. The application features a user-friendly interface built with JavaFX, allowing users to manage their accounts, view articles, like articles, and download them for offline reading. User data and articles are stored in a MySQL database.

## Features

- **User Registration and Login**: Users can create accounts and log in to access personalized features.
- **Article Aggregation**: Scrapes news articles from specified categories and displays them in an organized format.
- **Like Articles**: Users can like articles, which are saved in their profiles.
- **Offline Reading**: Users can download articles for offline reading.
- **Account Management**: Users can manage their profiles, including changing passwords and deleting accounts.
- **Search Functionality**: Users can search for articles based on keywords or categories.
- **Multi-Category Support**: Fetches articles from multiple categories and tracks the last fetched article per category.

## Technologies Used

- **Java**: Core programming language for the application.
- **JavaFX**: For building the user interface.
- **Jsoup**: For web scraping the articles from "XXX".
- **MySQL**: For storing user data and articles.
- **Maven**: For dependency management.


### Prerequisites

- Java Development Kit (JDK) 8 or higher
- MySQL Server
- Maven (for dependency management)



## Usage

1. **User Registration**: Create a new account using the registration feature.
2. **Login**: Log in with your credentials to access personalized features.
3. **View Articles**: Browse the list of articles aggregated from the specified categories.
4. **Like Articles**: Click on the like button to save your favorite articles.
5. **Download Articles**: Use the download feature to save articles for offline reading.
6. **Search Articles**: Utilize the search functionality to find articles based on keywords.

## Data Fetching Logic

- The application fetches articles from the specified categories on "XXX".
- It stores the last fetched article ID for each category in the database to avoid duplicate entries on subsequent fetches.
- If new articles are available, they are stored in the database along with their content and publication date.

## Contributing

Contributions are welcome! If you'd like to contribute to this project, please fork the repository and submit a pull request with your changes.
