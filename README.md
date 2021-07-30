# Lunch Microservice

The service provides an endpoint that will determine, from a set of recipes, what I can have for lunch at a given date, based on my fridge ingredient's expiry date, so that I can quickly decide what I’ll be having to eat, and the ingredients required to prepare the meal.

## Prerequisites

* [Java 11 Runtime](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
* [Docker](https://docs.docker.com/get-docker/) & [Docker-Compose](https://docs.docker.com/compose/install/)

*Note: Docker is used for the local MySQL database instance, feel free to use your own instance or any other SQL database and insert data from lunch-data.sql script* 


### Run

1. Start database:

    ```
    docker-compose up -d
    ```
   
2. Add test data from  `sql/lunch-data.sql` to the database. Here's a helper script if you prefer:


    ```
    CONTAINER_ID=$(docker inspect --format="{{.Id}}" lunch-db)
    ```
    
    ```
    docker cp sql/lunch-data.sql $CONTAINER_ID:/lunch-data.sql
    ```
    
    ```
    docker exec $CONTAINER_ID /bin/sh -c 'mysql -u root -prezdytechtask lunch </lunch-data.sql'
    ```
    
3. Run Springboot LunchApplication



Solutions:
1.End point URL: http://localhost:8089/lunch?date=2021-07-25
  Response: it will return all recipes with usedby and bestbefore, excluding ingredients expired already

2.End point URL: http://localhost:8089/receipesExcludeIngredients?ingredients=Eggs,bacon
  Response: it will return recipes which don't have eggs or bacon with its ingredients, when pass multi params,
            use comma as separator

3.End point URL: http://localhost:8089/getRecipeByTitle?title=fry-up
  Response: it will return error 404 not found if recipe not exist, if recipe exists, will return recipes and its ingredients

Thank you for your time!
Colin

