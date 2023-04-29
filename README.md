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
            spring.jpa.generate-ddl=true
            spring.jpa.hibernate.ddl-auto = create 
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



# How to Run the Application?

* You can run the application in two ways:

        If you want to run the application in an IDE, we recommend using IntelliJ IDE.
        Using Maven command: mvn spring-boot:run (You need to be in the project directory folder.)

Markup : 
- [ ] An uncompleted task
- [x] A completed task