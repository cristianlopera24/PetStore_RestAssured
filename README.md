# PetStore_RestAssured

Pet Store Java - RestAssured

## Table of Contents
1. [General Info](#general-info)
2. [Technologies](#technologies)
3. [Installation](#installation)
4. [Collaboration](#collaboration)

### General Info
***
API testing automation for a pet store.
What the API does and its different operations will be detailed; which were automated:

API for managing operations related to a pet store. Here's a brief description of some of the key operations and entities within this API:

Pet:

Create a Pet (POST /pet): Allows adding a new pet to the store with information such as name, status (available, pending, sold), category, tags, and links to photos.
Update a Pet (PUT /pet/{petId}): Allows updating the information of an existing pet using its unique identifier (petId).
Order:

Place an Order (POST /store/order): Allows placing an order for a pet, specifying the desired pet and quantity.
Get an Order by ID (GET /store/order/{orderId}): Allows retrieving detailed information about a specific order using its unique identifier (orderId).
Delete an Order by ID (DELETE /store/order/{orderId}): Allows canceling an order using its unique identifier.
User:

Create a User (POST /user): Allows creating a new user in the store, providing information such as username, email address, and password.
Get a User by Username (GET /user/{username}): Allows retrieving information about a specific user using their username.
Update a User (PUT /user/{username}): Allows updating the information of an existing user using their username.
Delete a User (DELETE /user/{username}): Allows deleting a user using their username.
These operations represent essential functions that a pet store might need, such as adding new pets to the inventory, placing and managing orders, and maintaining information about users utilizing the platform.

### Screenshot

Swagger: 
![image](https://github.com/cristianlopera24/PetStore_RestAssured/assets/56046255/a0dcbef7-8edd-4728-a538-b4469b21580d)

Test cases automated:
![image](https://github.com/cristianlopera24/PetStore_RestAssured/assets/56046255/9e5ffbb8-857d-420f-a3a2-02fe9cc216fd)



## Technologies
***
A list of technologies used within the project:

* [java]([https://www.cypress.io/](https://www.java.com/es/)): version "17.0.10"
* [Maven]([https://fakerjs.dev/guide/](https://maven.apache.org/download.cgi)): Version 17
* [JUnit](https://junit.org/junit5/): Version 4.13.2
* [Rest Assured](https://rest-assured.io/): Version 5.4.0

## Installation
***
A little intro about the installation. 
```
$ instal Java 17
$ cd /your/project/path/petStore
$ open git bash
$ git clone https://github.com/cristianlopera24/PetStore_RestAssured.git
$ Open the IDE
$ Open the project
$ Search the src/test/java/Pet
$ Execute the test cases
```

## Collaboration
***

CristianLopera24 worked on this.

