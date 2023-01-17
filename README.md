# Project Goal
This project demonstrates how to use Lock to resolve concurrency problems.

## Behaviors
 - Users can get specified cyber star information from the GET method via the cyber star's login Id.  
Such as how many followings or following and friends.
 - Users can get followings, followers or friends list with page navigation via the POST method.
  - A cyber star can subscribe or unsubscribe other cyber stars.
  - When two cyber stars subscribe to each other, then they will be marked as friends.

## Swagger API
[Swagger](http://localhost:8080/swagger-ui/index.html#/)

## Technical stack
- Spring Boot
- Spring Data JPA
- H2 embedded database


### TODO
 - Unit test
 - Improve performance