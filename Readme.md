  
  <br/>
  
## Movie Rental
<br/>
  
## Tools

Spring Boot, Java, JWT, JPA, Hibernate, PostgreSQL, RESTful CRUD API. Frontend with Javascript and Dojo.
  <br/>
  

## Steps to Setup
<br/>
  
**1. Clone the repository**

```
git clone https://github.com/salvaje1385/movie-rental.git
```
<br/>
  
**2. Configure PostgreSQL**

First, create a database named `movie_rental`. Then, open `src/main/resources/application.properties` file and change the spring datasource username and password as per your PostgreSQL installation.
  <br/><br/>
  
**3. Run the app**

Type the following command from the root directory of the project to run it.

```
mvn spring-boot:run
```
<br/>
  
**4. Create the user roles into the database:**
  <br/>
  
  This is not necessary if you restored the database.
  <br/>
  

```
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
```
<br/>
  
  Find a database snapshot you can restore in the projects's root folder:
  
```
movie_rental_db.sql
```
<br/>
  
You can go to the movies page through this URL.

```
http://localhost:8080/movierental
```
<br/>
  
Also look the **REST_API.md** file.

<br/>
  
Alternatively, you can package the application in the form of a JAR file and then run it like so.

```
mvn clean package
java -jar target/movie-rental-0.0.1-SNAPSHOT.jar
```
<br/><br/>
  