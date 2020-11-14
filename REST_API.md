## REST API

# Movies

Get:

```bash
http://localhost:8080/movies
GET
```

Create:

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

Update:

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

Delete:

```bash
http://localhost:8080/movies/3701
DELETE
```




# Users


Get:

```bash
http://localhost:8080/users
GET
```

Create:

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

Update:

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

Delete:

```bash
http://localhost:8080/users/1000
DELETE
```




# Likes

Like a Movie:

```bash
http://localhost:8080/users/likeMovie
POST

{
  "userId": 6,
  "movieId": 3251,
  "like": true
}
```

Unlike a Movie:

```bash
http://localhost:8080/users/likeMovie
POST

{
  "userId": 6,
  "movieId": 3251,
  "like": false
}
```


# Rentals

Get:

```bash
http://localhost:8080/rentals
GET
```

Rent a Movie:

```bash
http://localhost:8080/rentals
POST

{
  "userId": 14,
  "movieId": 1051,
  "dueDate": 1605326462890
}
```

Return a Movie:

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

Add a penalty:

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

Delete:

```bash
http://localhost:8080/rentals/1
DELETE
```

# Purchases

Get:

```bash
http://localhost:8080/purchases
GET
```

Create:

```bash
http://localhost:8080/purchases
POST

{
  "userId": 6,
  "movieId": 3551,
  "price": 180.45
}
```

Update:

```bash
http://localhost:8080/purchases/12
PUT

{
  "userId": 6,
  "movieId": 3551,
  "price": 180.45
}
```

Delete:

```bash
http://localhost:8080/purchases/1
DELETE
```

# Movie Updates

Get:

```bash
http://localhost:8080/movieUpdates
GET
```

Delete:

```bash
http://localhost:8080/movieUpdates/1
DELETE

