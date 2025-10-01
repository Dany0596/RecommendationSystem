# 🎬 Recommendation System

A simple movie recommendation system built with **Spring Boot** and **PostgreSQL**.

## Technologies

* Java 21
* Spring Boot 3
* PostgreSQL
* Spring Data JPA (Hibernate)

## Setup Instructions

1. Clone the project

   ```bash
   git clone https://github.com/your-username/RecommendationSystem.git
   cd RecommendationSystem
   ```

2. Create the database in PostgreSQL:

   ```sql
   CREATE DATABASE movielens;
   ```

3. Configure the `src/main/resources/application.properties` file with your PostgreSQL credentials:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/movielens
   spring.datasource.username=postgres
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

4. Run the application:

   ```bash
   mvn spring-boot:run
   ```

5. Test the API endpoints:

   * `GET http://localhost:8080/movie` → retrieves all movies
   * `POST http://localhost:8080/movie` → adds a new movie

## 📂 Project Structure

```
RecommendationSystem/
│── src/
│   ├── main/
│   │   ├── java/com/recommendation_system/...   # source code
│   │   └── resources/
│   │       ├── application.properties           # Spring Boot configuration
│   │       ├── schema.sql (optional)            # auto-executed schema
│   │       └── data.sql (optional)              # auto-executed seed data
│   └── test/                                    # JUnit tests
│
│── scripts/
│   ├── schema.sql                               # manual DB creation
│   └── data.sql                                 # sample dataset
│
│── README.md                                    # project documentation
│── pom.xml / build.gradle                       # dependencies
│── .gitignore                                   # ignore rules
```

## Database Scripts

* If you want Spring Boot to **automatically create and populate the database**:

  * Place `schema.sql` and/or `data.sql` in `src/main/resources/`
* If you prefer **manual setup**:

  * Keep the SQL scripts in the `scripts/` folder
  * Execute them manually in pgAdmin or via `psql`

## Example

You can enhance your portfolio by adding:

* API request examples (via Postman or cURL)
* Database schema screenshots (from pgAdmin)
* A short description of how the recommendation logic works

---

 If you like this project, feel free to fork it and experiment!
