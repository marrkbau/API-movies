﻿# API-movies

### _Creating a full stack web application!_ 🙌

## 🦾 Technologies used 🦾

_Front end:_  

_Back end:_ Java with Spring Boot framework. 

_Database:_ MongoDB. 


This is an example of a movie stored in the database: 

```json
 {
  "_id": {
    "$oid": "65e7da39fdf9ee7e3cdfe22bdada"
  },
  "imdbId": "tt1630029dad",
  "title": "Avatar: The Way of Water",
  "releaseDate": "2022-12-16",
  "trailerLink": "https://www.youtube.com/watch?v=d9MyW72ELq0",
  "genres": [
    "Science Fiction",
    "Action",
    "Adventure"
  ],
  "poster": "https://image.tmdb.org/t/p/w500/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg",
  "backdrops": [
    "https://image.tmdb.org/t/p/original/s16H6tpK2utvwDtzZ8Qy4qm5Emw.jpg",
    "https://image.tmdb.org/t/p/original/evaFLqtswezLosllRZtJNMiO1UR.jpg",
    "https://image.tmdb.org/t/p/original/198vrF8k7mfQ4FjDJsBmdQcaiyq.jpg",
    "https://image.tmdb.org/t/p/original/zaapQ1zjKe2BGhhowh5pM251Gpl.jpg",
    "https://image.tmdb.org/t/p/original/tQ91wWQJ2WRNDXwxuO7GCXX5VPC.jpg",
    "https://image.tmdb.org/t/p/original/5gPQKfFJnl8d1edbkOzKONo4mnr.jpg",
    "https://image.tmdb.org/t/p/original/2fS9cpar9rzxixwnRptg4bGmIym.jpg",
    "https://image.tmdb.org/t/p/original/fkGR1ltNbvERk3topo4dP3gWsvR.jpg",
    "https://image.tmdb.org/t/p/original/rb9IHprKNoSKqatP2vr25unUDSu.jpg",
    "https://image.tmdb.org/t/p/original/37ZswIuRQcRBN7kHij5MBjzRMRt.jpg"
  ],
  "reviewIds": []
}
```

### Movies ### 

The movie service class uses the repository class and talks to the database
get the list of the movies and returns to the API layer. Finally, the
repository layer, the access layer for the API, does the job of 
actually talking to the database and getting the data back. 

<br>


### Reviews ###

This piece of code saves the review on insert(). Then,
performs an update call in the movie class 
because each movie contains an empty array of review IDs. So the 
method performs a push in the reviewId array. The movie updated is
the movie where the imdbId in the database matched the imdbId that
is received from the user. Finally, to make sure it is getting a 
single movie, it uses the method first(). Then returns 
the review. 



    public Review createReview(String reviewBody, String imdbId){

        Review review = reviewRepository.insert(new Review(reviewBody));

        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update()
                .push("reviewIds").value(review)).first();

        return review;
    }

