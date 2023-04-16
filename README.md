# Resturant_BackEnd

CREATE TABLE restaurants (
                             id INT NOT NULL AUTO_INCREMENT,
                             name VARCHAR(255) NOT NULL,
                             address VARCHAR(255) NOT NULL,
                             PRIMARY KEY (id)
);

CREATE TABLE users (
                       id INT NOT NULL AUTO_INCREMENT,
                       username VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       PRIMARY KEY (id)
);

CREATE TABLE reviews (
                         id INT NOT NULL AUTO_INCREMENT,
                         user_id INT NOT NULL,
                         restaurant_id INT NOT NULL,
                         rating INT NOT NULL,
                         comment TEXT,
                         likes INT DEFAULT 0,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         PRIMARY KEY (id),
                         FOREIGN KEY (user_id) REFERENCES users(id),
                         FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

CREATE TABLE review_likes (
                              id INT NOT NULL AUTO_INCREMENT,
                              user_id INT NOT NULL,
                              review_id INT NOT NULL,
                              PRIMARY KEY (id),
                              FOREIGN KEY (user_id) REFERENCES users(id),
                              FOREIGN KEY (review_id) REFERENCES reviews(id),
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE review_comments (
                                 id INT NOT NULL AUTO_INCREMENT,
                                 user_id INT NOT NULL,
                                 review_id INT NOT NULL,
                                 comment TEXT NOT NULL,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 PRIMARY KEY (id),
                                 FOREIGN KEY (user_id) REFERENCES users(id),
                                 FOREIGN KEY (review_id) REFERENCES reviews(id)
);


