# REST API
  
  
  
# Sign Up

**You can use the "user" or "admin" roles.**

```
http://localhost:8080/api/auth/signup
POST

{
  "username": "aperez",
  "email": "aperez@test.com",
  "password": "123456",
  "role": [
	"user"
  ]
}
```

# Signin

```
http://localhost:8080/api/auth/signin
POST

{
  "username": "aperez",
  "password": "123456"
}
```

In the response you're going to get the accessToken and the tokenType, 
that you're going to use to authenticate each service call:
  
```
"accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNjA1NTA0MjI1LCJleHAiOjE2MDU1OTA2MjV9",  
"tokenType": "Bearer"
```
  
## IMPORTANT!  
  
  **Add the returned "tokenType" and "accessToken" into every service call's header to authenticate the User.**  
  
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNjA1NTA0MjI1LCJleHAiOjE2MDU1OTA2MjV9
```
  
  
  **If you restored the database you can use the following user credentials.**  
  
```
    For user:
    {
        "username": "user",
        "password": "123456"
    }

    For admin:
    {
        "username": "admin",
        "password": "adminpass"
    }
```
  
# 1. Movies

1.1 Get:  

All the Movie REST calls are paginated.  

1.1.1 Get all Movies:
  
  Note that the GET "/movies" service can be accessed by logged in or logged out users.  
  
```
http://localhost:8080/movies
GET
```
  
  1.1.2 Get all Movies which title contains "ave" ignoring case:
  
```
http://localhost:8080/movies?title=ave
GET
```
  
1.1.3 Get all Movies which title contains "ave" ignoring case, and are available:
  
  
```
http://localhost:8080/movies?title=ave&available=true
GET
```

1.1.4 Get all Movies which title contains "ave" ignoring case, and aren't available:
  
  **Only admin users can see unavailable movies. Regular users and logged out users can only see available movies.**  
  
```
http://localhost:8080/movies?title=ave&available=false
GET
```

1.1.5 Get all available Movies:

```
http://localhost:8080/movies?available=true
GET
```

1.1.6 Get all unavailable Movies:

```
http://localhost:8080/movies?available=false
GET
```

1.1.7 Sort Movies by likes (descending), and then by title (descending):

```
http://localhost:8080/movies?sort=likes,desc&sort=title,desc
GET
```

1.1.8 Get all Movies which title contains "a" ignoring case, and sort by likes, descending:

```
http://localhost:8080/movies?title=a&sort=likes,desc
GET
```

1.1.9 Get all Movies in the second page (pages are 0 indexed), having 10 as the page size:

```
http://localhost:8080/movies?page=1&size=10
GET
```

1.2 Create:

**Only admin users can create, update or delete movies.**

```
http://localhost:8080/movies
POST

{
  "title": "Batman",
  "image": "https://www.images.com/f5f4d5",
  "description": "It's about Batman",
  "stock": 30,
  "rentalPrice": 8.122,
  "salePrice": 20.592,
  "available": true
}
```

1.3 Update:

```
http://localhost:8080/movies/3701
PUT

{
  "id": 3701,
  "title": "Batman 2",
  "image": "https://www.images.com/f5f4d5",
  "description": "It's about Batman 2",
  "stock": 30,
  "rentalPrice": 8.122,
  "salePrice": 20.592,
  "available": true
}
```

1.4 Delete:

```
http://localhost:8080/movies/3701
DELETE
```




# 2. Users
  
  **Only admin users can get, update or delete Users.**
  
  Users are created in the Sing Up service.
  
2.1 Get:

```
http://localhost:8080/users
GET
```

2.3 Update:

You can't update the username and password fields.

```
http://localhost:8080/users/1000
PUT
{
  "email": "hperez@test.com",
  "phone": "155611511",
  "address": "at home"
}
```

2.4 Delete:

```
http://localhost:8080/users/1000
DELETE
```




# 3 Likes

3.1 Like a Movie:

```
http://localhost:8080/users/likeMovie
POST

{
  "userId": 6,
  "movieId": 3251,
  "like": true
}
```

3.2 Unlike a Movie:

```
http://localhost:8080/users/likeMovie
POST

{
  "userId": 6,
  "movieId": 3251,
  "like": false
}
```


# 4. Rentals
  
  
4.1 Get:
  
  Regular users will get only the rentals created by themselves. Admin users get all the rentals.
  
```
http://localhost:8080/rentals
GET
```

4.2.1 Rent a Movie:

```
http://localhost:8080/rentals
POST

{
  "userId": 14,
  "movieId": 1051,
  "dueDate": 1605326462890
}
```

4.2.2 Return a rented Movie:
  
  **Only admin users can update or delete a rental.**
  
```
http://localhost:8080/rentals/18
PUT

{
  "userId": 14,
  "movieId": 1051,
  "dueDate": 1605326462890,
  "returnDate": 1605008548703
}
```

4.2.3 Add a penalty:

```
http://localhost:8080/rentals/18
PUT

{
  "userId": 14,
  "movieId": 1051,
  "dueDate": 1605326462890,
  "returnDate": 1605008548703,
  "penalty": 5.44
}
```

4.3 Delete:

```
http://localhost:8080/rentals/1
DELETE
```

# 5. Purchases
  
5.1 Get:
  
  Regular users will get only the purchases created by themselves. Admin users get all the purchases.
  
```
http://localhost:8080/purchases
GET
```

5.2 Create:

```
http://localhost:8080/purchases
POST

{
  "userId": 13,
  "movieId": 1051
}
```

5.3 Update:
  
  **Only admin users can update or delete a purchase.**
  
```
http://localhost:8080/purchases/19
PUT

{
  "userId": 14,
  "movieId": 1051
}
```

5.4 Delete:

```
http://localhost:8080/purchases/1
DELETE
```

# 6. Movie Updates
  
  Only admin users can get movie updates.
  
6.1 Get:

```
http://localhost:8080/movieUpdates
GET
```
