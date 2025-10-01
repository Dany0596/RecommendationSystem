-- ===================================================
-- TABLE: movies
-- ===================================================
CREATE TABLE movies (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genres VARCHAR(255)
);

-- Sample data
INSERT INTO movies (title, genres) VALUES
('The Shawshank Redemption', 'Drama|Crime'),
('The Godfather', 'Crime|Drama'),
('The Dark Knight', 'Action|Crime|Drama'),
('Pulp Fiction', 'Crime|Drama'),
('Forrest Gump', 'Drama|Romance');

-- ===================================================
-- TABLE: users
-- ===================================================
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL
);

-- Sample data
INSERT INTO users (first_name, last_name, email) VALUES
('Alice', 'Smith', 'alice@example.com'),
('Bob', 'Johnson', 'bob@example.com'),
('Charlie', 'Brown', 'charlie@example.com');

-- ===================================================
-- TABLE: ratings
-- ===================================================
CREATE TABLE ratings (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id),
    movie_id INT NOT NULL REFERENCES movies(id),
    rating NUMERIC(2,1) NOT NULL,
    timestamp BIGINT
);

-- Sample data
INSERT INTO ratings (user_id, movie_id, rating, timestamp) VALUES
(1, 1, 5.0, 1620000000),
(1, 2, 4.5, 1620003600),
(2, 1, 4.0, 1620010000),
(2, 3, 5.0, 1620020000),
(3, 4, 3.5, 1620030000);
