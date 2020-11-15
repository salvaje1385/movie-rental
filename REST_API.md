## REST API

# 1. Movies

1.1 Get:

All the Movie REST calls are paginated.

1.1.1 Get all Movies:

```bash
http://localhost:8080/movies
GET
```

1.1.2 Get all Movies which title contains "ave" ignoring case:

```bash
http://localhost:8080/movies?title=ave
GET
```

1.1.3 Get all Movies which title contains "ave" ignoring case, and are available:

```bash
http://localhost:8080/movies?title=ave&available=true
GET
```

1.1.4 Get all Movies which title contains "ave" ignoring case, and aren't available:

```bash
http://localhost:8080/movies?title=ave&available=false
GET
```

1.1.5 Get all available Movies:

```bash
http://localhost:8080/movies?available=true
GET
```

1.1.6 Get all unavailable Movies:

```bash
http://localhost:8080/movies?available=false
GET
```

1.1.7 Sort Movies by likes (descending), and then by title (descending):

```bash
http://localhost:8080/movies?sort=likes,desc&sort=title,desc
GET
```

1.1.8 Get all Movies which title contains "a" ignoring case, and sort by likes, descending:

```bash
http://localhost:8080/movies?title=a&sort=likes,desc
GET
```

1.1.9 Get all Movies in the second page (pages are 0 indexed), having 10 as the page size:

```bash
http://localhost:8080/movies?page=1&size=10
GET
```

1.2 Create:

```bash
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

```bash
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

```bash
http://localhost:8080/movies/3701
DELETE
```




# 2. Users


2.1 Get:

```bash
http://localhost:8080/users
GET
```

2.2 Create:

```bash
http://localhost:8080/users
POST

{
  "name": "Humberto Perez",
  "email": "hperez@test.com",
  "password": "1234",
  "phone": "155611511",
  "address": "at home"
}
```

2.3 Update:

```bash
http://localhost:8080/users/1000
PUT
{
  "name": "Humberto Perez",
  "email": "hperez@test.com",
  "password": "1234",
  "phone": "155611511",
  "address": "at home"
}
```

2.4 Delete:

```bash
http://localhost:8080/users/1000
DELETE
```




# 3 Likes

3.1 Like a Movie:

```bash
http://localhost:8080/users/likeMovie
POST

{
  "userId": 6,
  "movieId": 3251,
  "like": true
}
```

3.2 Unlike a Movie:

```bash
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

```bash
http://localhost:8080/rentals
GET
```

4.2.1 Rent a Movie:

```bash
http://localhost:8080/rentals
POST

{
  "userId": 14,
  "movieId": 1051,
  "dueDate": 1605326462890
}
```

4.2.2 Return a rented Movie:

```bash
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

```bash
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

```bash
http://localhost:8080/rentals/1
DELETE
```

# 5. Purchases

5.1 Get:

```bash
http://localhost:8080/purchases
GET
```

5.2 Create:

```bash
http://localhost:8080/purchases
POST

{
  "userId": 13,
  "movieId": 1051
}
```

5.3 Update:

```bash
http://localhost:8080/purchases/19
PUT

{
  "userId": 14,
  "movieId": 1051
}
```

5.4 Delete:

```bash
http://localhost:8080/purchases/1
DELETE
```

# 6. Movie Updates

6.1 Get:

```bash
http://localhost:8080/movieUpdates
GET
```

6.2 Delete:

```bash
http://localhost:8080/movieUpdates/1
DELETE

