# Requirements

    Before running the backend application, ensure that you have the following hardware and software requirements:

# Hardware Requirements: 

    1. Linux or Windows distribution system
    2. Minimum 8 GB RAM
# Software Requirements:

    1. JDK 17 or above (recommended)
    2. Maven latest version (if you want to run the application using the command line interface) or create a production JAR file.

# Setting up the Application:

* Before running the application, you need to set up the application properties file. Follow the steps below:

        1. Database URL
        2. Database username
        3. Database password
        4. Google API key for fetching the restaurant. For more details, please refer to https://developers.google.com/maps/documentation/javascript/get-api-key
    [API CREATION Link](https://developers.google.com/maps/documentation/javascript/get-api-key "Named link title") and https://developers.google.com/maps/documentation/javascript/get-api-key

        After settting up the Google API key, you need to enable the following APIs after creating the API key:
            A. Places API
            B. Javascript API
            C. Google photo API
* your application.properties file should have belows setting's.

            spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
            spring.data.rest.base-path=/api
            spring.datasource.url=URL
            spring.datasource.username=<USERNAME>
            spring.datasource.password=<PASSWORD>
            spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
            spring.jpa.show-sql=true
            #Goodge api details
            external.google.api.key=<GOOGLE_API_KEY>
            external.google.api.url=maps.googleapis.com
            external.google.api.place.text.search=/maps/api/place/textsearch/json
            external.google.api.place.nearBy.search=/maps/api/place/nearbysearch/json
            external.google.api.place.details=/maps/api/place/details/json
            external.google.api.photo=/maps/api/place/photo 
            #authentication token
            jwt.auth.app=restaurant-finder-App
            jwt.auth.secret_key=<SECRET_KEY>
            jwt.auth.expires_in=3600
            jwt.auth.refresh.token.expires_in=3600
* Running the Queries to insert the tables:
  *   code`create table restaurants (
      id int auto_increment primary key,
      name varchar(255) not null,
      address varchar(255) not null
      );`
  * code `create table users (
    id int auto_increment primary key,
    username varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    gender varchar(10) null,
    role varchar(10) null,
    mobile_Number varchar(15) null
    );`
  * code `create table reviews (
    id bigint auto_increment primary key,
    created_at datetime(6) not null,
    delivery_available varchar(255) null,
    dine_in_available varchar(255) null,
    likes int null,
    rating int not null,
    restaurant_id varchar(50) not null,
    review varchar(255) not null,
    updated_at datetime(6) not null,
    user_id bigint not null,
    constraint FKcgy7qjc1r99dp117y9en6lxye foreign key (user_id) references users (id)
    );`
  * code `create index restaurant_id on reviews (restaurant_id);`
  * code `create index user_id on reviews (user_id);`
  * code `create table review_comments (
    id int auto_increment primary key,
    user_id int not null,
    review_id int not null,
    comment text not null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    updated_at timestamp default CURRENT_TIMESTAMP null,
    constraint review_comments_ibfk_1 foreign key (user_id) references users (id),
    constraint review_comments_ibfk_2 foreign key (review_id) references reviews (id) on delete cascade
    );`
  * code `create index user_id on review_comments (user_id);`
  * code `create table review_likes (
    id int auto_increment primary key,
    user_id int not null,
    review_id int not null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    updated_at timestamp default CURRENT_TIMESTAMP null,
    constraint review_likes_ibfk_1 foreign key (user_id) references users (id),
    constraint review_likes_ibfk_2 foreign key (review_id) references reviews (id) on delete cascade
    );`
  * code `create index user_id on review_likes (user_id);`


# How to Run the Application?

* You can run the application in two ways:

        If you want to run the application in an IDE, we recommend using IntelliJ IDE.
        Using Maven command: mvn spring-boot:run (You need to be in the project directory folder.)

References : 
- [x] SPRING BOOT - https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/
- [x] SPRING SECURITY - https://docs.spring.io/spring-security/reference/6.0-SNAPSHOT/index.html
- [x] MYSQL - https://dev.mysql.com/doc/
- [x] SPRING SECURITY TUTORIAL - https://www.youtube.com/watch?v=BRl2ZHqF-wQ&list=PL82C6-O4XrHe3sDCodw31GjXbwRdCyyuY&index=10%20link
- [x] AZURE APP SERVICES - https://learn.microsoft.com/en-us/azure/app-service/quickstart-java?pivots=platform-linux-development-environment-maven&tabs=javase
- [x] AZURE MY SQL - https://learn.microsoft.com/en-us/azure/mysql/
- [x] LOMBOK - https://projectlombok.org/features/
- [x] GOOGLE PLACE DETAILS - https://developers.google.com/maps/documentation/places/web-service/details
- [x] GOOGLE PHOTOS API - https://developers.google.com/maps/documentation/places/web-service/photos
- [X] GOOGLE PLACE SEARCH - https://developers.google.com/maps/documentation/places/web-service/search
- [X] GOOGLE NEARBY SEARCH - https://developers.google.com/maps/documentation/places/web-service/search-nearby

