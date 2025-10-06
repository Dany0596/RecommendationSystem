# Movie Recommendation System - Backend

This project implements a movie recommendation system backend using **Spring Boot**, **Hibernate**, and **PostgreSQL**.
The main purpose of this repository is to demonstrate how the recommendation logic works at the backend level. At this stage, no graphical interface is provided. The focus is on correct backend functionality and on giving an overview of how the code and its features operate.

---

## Features

* **User-based movie recommendations** using cosine similarity on ratings.
* **Movie retrieval** from the database.
* **Ratings management** for users and movies.
* **REST API endpoints** for testing and exploration.

---

## How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/recommendation-system.git
   cd recommendation-system
   ```

2. Configure your PostgreSQL database in `application.properties`:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Build and run the application:

   ```bash
   ./mvnw spring-boot:run
   ```

   The application will start on `http://localhost:8080`.

---

## Testing the API

Since no front-end interface is provided, you can test the features using a browser or tools such as **Postman** or **cURL**.

### Endpoints and Example Responses

1. **Get all movies**

   ```
   GET http://localhost:8080/movies
   ```

   **Example JSON response:**

   ```json
   [
     {
       "id": 1,
       "title": "Inception",
       "genres": ["Sci-Fi", "Thriller"]
     },
     {
       "id": 2,
       "title": "The Matrix",
       "genres": ["Action", "Sci-Fi"]
     }
   ]
   ```

2. **Get all ratings**

   ```
   GET http://localhost:8080/ratings
   ```

   **Example JSON response:**

   ```json
   [
     {
       "id": 1,
       "userId": 1,
       "movieId": 2,
       "rating": 5.0
     },
     {
       "id": 2,
       "userId": 1,
       "movieId": 1,
       "rating": 4.0
     }
   ]
   ```

3. **Get recommendations for a specific user**
   Replace `{userId}` with the target user ID.

   ```
   GET http://localhost:8080/recommendations/{userId}
   ```

   **Example JSON response:**

   ```json
   [
     {
       "movieId": 3,
       "title": "Interstellar",
       "predictedScore": 4.6
     },
     {
       "movieId": 5,
       "title": "The Prestige",
       "predictedScore": 4.3
     }
   ]
   ```

4. **Add a new rating**

   ```
   POST http://localhost:8080/ratings
   Content-Type: application/json

   {
     "userId": 1,
     "movieId": 10,
     "rating": 4.5
   }
   ```

   **Example JSON response:**

   ```json
   {
     "id": 15,
     "userId": 1,
     "movieId": 10,
     "rating": 4.5
   }
   ```

5. **Get recommendations using cosine similarity**

   ```
   GET http://localhost:8080/recommendations/cosine?userId=1&topN=5
   ```

   **Example JSON response:**

   ```json
   [
     {
       "movieId": 7,
       "title": "Memento",
       "similarityScore": 0.89
     },
     {
       "movieId": 9,
       "title": "Tenet",
       "similarityScore": 0.83
     }
   ]
   ```

---

## Notes

* The current focus is on backend correctness. The code is structured to highlight data access, service logic, and recommendation algorithms.
* A graphical interface may be added in the future, but at this stage the backend endpoints represent the intended functionality.
* This project is intended as a demonstration of how recommendation systems can be implemented using Java and Spring Boot.

---

## Technologies Used

* Java 21
* Spring Boot 3
* Hibernate ORM
* PostgreSQL
* Maven
