## Movie Rental

## Tutorial

Spring Boot, PostgreSQL, JPA, Hibernate RESTful CRUD API

## Steps to Setup

**1. Clone the repository**

```bash
git clone https://github.com/
```

**2. Configure PostgreSQL**

First, create a database named `movie_rental`. Then, open `src/main/resources/application.properties` file and change the spring datasource username and password as per your PostgreSQL installation.

**3. Run the app**

Type the following command from the root directory of the project to run it -

```bash
mvn spring-boot:run
```



You can go to the movies page through this URL

```bash
http://localhost:8080/movierental
```


Also look the **REST_API.md** file.


Alternatively, you can package the application in the form of a JAR file and then run it like so -

```bash
mvn clean package
java -jar target/movie-rental-0.0.1-SNAPSHOT.jar
```