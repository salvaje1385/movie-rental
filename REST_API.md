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
  "name": "Alberto Espinoza",
  "email": "aespinoza@test.com",
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
  "name": "Alberto Espinoza 2",
  "email": "aespinoza@test2.com",
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




Like a Movie

```bash
http://localhost:8080/users/likeMovie
POST

{
  "userId": 6,
  "movieId": 3251
}
```


# Rentals

Get:

```bash
http://localhost:8080/rentals
GET
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

