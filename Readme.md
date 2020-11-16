## Movie Rental

## Tutorial

Spring Boot, PostgreSQL, Dojo, JPA, JWT, Hibernate, RESTful CRUD API

## Steps to Setup

**1. Clone the repository**

```
git clone https://github.com/salvaje1385/movie-rental.git
```

**2. Configure PostgreSQL**

First, create a database named `movie_rental`. Then, open `src/main/resources/application.properties` file and change the spring datasource username and password as per your PostgreSQL installation.

**3. Run the app**

Type the following command from the root directory of the project to run it -

```
mvn spring-boot:run
```
  
**4. Create the user roles into the database:**
  
  This is not necessary if you restored the database.
  

```
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
```
  
  
  Find a database snapshot you can restore in the projects's root folder:
  
```
movie_rental_db.sql
```
  
You can go to the movies page through this URL

```
http://localhost:8080/movierental
```


Also look the **REST_API.md** file.


Alternatively, you can package the application in the form of a JAR file and then run it like so -

```
mvn clean package
java -jar target/movie-rental-0.0.1-SNAPSHOT.jar
```