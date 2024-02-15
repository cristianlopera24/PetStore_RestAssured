import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class Pet {

    /**
     * This method is annotated with @Before which means it will be run before each test method.
     * It sets up the base URI, base path, filters, and request specification for the tests.
     * The base URI is the base part of the URL for the API we are testing.
     * The base path is the base path of the API we are testing.
     * The filters are used to log the request and response for debugging purposes.
     * The request specification is used to set the content type of the requests to "application/json".
     */
    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/";
        RestAssured.basePath = "/api/v3";
        RestAssured.filters(new RequestLoggingFilter(), new Filter[]{new ResponseLoggingFilter()});
        RestAssured.requestSpecification = (new RequestSpecBuilder()).setContentType("application/json").build();
    }

    /**
     * This test method is used to add a new pet to the store.
     * It creates a JSON request body with the pet details and sends a POST request to the "/pet" endpoint.
     * It then validates the response to ensure that the pet was added correctly.
     */
    @Test
    public void postAddANewPetToTheStore() {
        String requestBody = "{\"id\":1,\"category\":{\"id\":1,\"name\":\"Dog\"},\"name\":\"Doggie\",\"photoUrls\":[\"www.testurl.com\"],\"tags\":[{\"id\":1,\"name\":\"Red\"}],\"status\":\"available\"}";
        RestAssured
                .given()
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .body("id", Matchers.is(1))
                .body("category.id", Matchers.is(1))
                .body("category.name", Matchers.is("Dog"))
                .body("name", Matchers.is("Doggie"))
                .body("photoUrls[0]", Matchers.is("www.testurl.com"))
                .body("tags[0].id", Matchers.is(1))
                .body("tags[0].name", Matchers.is("Red"))
                .body("status", Matchers.is("available"));
    }

    /**
 * This test method is used to find a pet by its ID.
 * It sends a GET request to the "/pet/1" endpoint.
 * The response is then validated to ensure that the correct pet details are returned.
 * The status code of the response is checked to be 200, indicating a successful request.
 * The body of the response is also checked to ensure that the correct pet details are returned.
 * The details checked include the pet's ID, category, name, photo URL, tags, and status.
 */
@Test
public void getFindPetById() {
    RestAssured
            .given()
            .when()
            .get("/pet/1")
            .then()
            .statusCode(200)
            .body("id", Matchers.is(1))
            .body("category.id", Matchers.is(1))
            .body("category.name", Matchers.is("Dog"))
            .body("name", Matchers.is("Doggie"))
            .body("photoUrls[0]", Matchers.is("www.testurl.com"))
            .body("tags[0].id", Matchers.is(1))
            .body("tags[0].name", Matchers.is("Red"))
            .body("status", Matchers.is("available"));
}

    /**
     * This test method is used to handle the scenario when a pet is not found by its ID.
     * It sends a GET request to the "/pet/1000" endpoint and validates the response to ensure that a 404 status code is returned.
     */
@Test
public void getFindPetByIdNotFound() {
    RestAssured
            .given()
            .when()
            .get("/pet/1000")
            .then().statusCode(404);
}

    /**
     * This test method is used to handle the scenario when a new pet is added to the store with an invalid endpoint.
     * It creates a JSON request body with the pet details and sends a POST request to the "/pet1" endpoint.
     * It then validates the response to ensure that a 404 status code is returned.
     */
    @Test
    public void postAddANewPetToTheStoreBadRequestWithInvalidEndpoint() {
        String requestBody = "{\"id\":1,\"category\":{\"_id\":1,\"name\":\"Dog\"},\"name\":\"Doggie\",\"photoUrls\":[\"www.testurl.com\"],\"tags\":[{\"id\":1,\"name\":\"Red\"}],\"status\":\"available\"}";
        RestAssured
                .given()
                .body(requestBody)
                .when()
                .post("/pet1")
                .then().statusCode(404);
    }

    /**
     * This test method is used to handle the scenario when a new pet is added to the store with an invalid body.
     * It creates a JSON request body with the pet details and sends a POST request to the "/pet" endpoint.
     * It then validates the response to ensure that a 400 status code is returned.
     */
    @Test
    public void postAddANewPetToTheStoreBadRequestWithInvalidBody() {
        String requestBody = "{\"id\":1}";
        RestAssured
                .given()
                .body(requestBody)
                .when().post("/pet")
                .then().statusCode(400);
    }

    /**
     * This test method is used to update an existing pet in the store.
     * It creates a JSON request body with the updated pet details and sends a PUT request to the "/pet" endpoint.
     * It then validates the response to ensure that the pet was updated correctly.
     */
    @Test
    public void putUpdateAnExistentPet() {
        String requestBody = "{\"id\":1,\"category\":{\"id\":1,\"name\":\"Dog Update\"},\"name\":\"Doggie Update\"}";
        RestAssured
                .given()
                .body(requestBody)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .body("id", Matchers.is(1))
                .body("category.id", Matchers.is(1))
                .body("category.name", Matchers.is("Dog Update"))
                .body("name", Matchers.is("Doggie Update"));
    }

    /**
     * This test method is used to delete a pet from the store.
     * It sends a DELETE request to the "/pet/1" endpoint and validates the response to ensure that a 200 status code is returned.
     */
    @Test
    public void deleteDeletePet() {
        RestAssured
                .given()
                .when()
                .delete("/pet/1")
                .then()
                .statusCode(200);
    }

    /**
     * This test method is used to add a new pet to the store with an existing ID.
     * It creates a JSON request body with the pet details and sends a POST request to the "/pet" endpoint.
     * It then validates the response to ensure that the pet was added correctly.
     */
    @Test
    public void postAddANewPetWithExistentId() {
        String requestBody = "{\"id\":1,\"category\":{\"id\":1,\"name\":\"Dog\"},\"name\":\"Doggie\",\"photoUrls\":[\"www.testurl.com\"],\"tags\":[{\"id\":1,\"name\":\"Red\"}],\"status\":\"available\"}";
        RestAssured
                .given()
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .body("id", Matchers.is(1))
                .body("category.id", Matchers.is(1))
                .body("category.name", Matchers.is("Dog"))
                .body("name", Matchers.is("Doggie"))
                .body("photoUrls[0]", Matchers.is("www.testurl.com"))
                .body("tags[0].id", Matchers.is(1))
                .body("tags[0].name", Matchers.is("Red"))
                .body("status", Matchers.is("available"));
    }

    /**
     * This test method is used to handle the scenario when a new pet is added to the store with an existing ID.
     * It creates a JSON request body with the pet details and sends a POST request to the "/pet" endpoint.
     * It then validates the response to ensure that a 400 status code is returned, indicating a bad request.
     */
    @Test
    public void postAddANewPetWithExistentIdBadRequest() {
        String requestBody = "{\"id\":1,\"category\":{\"id\":1,\"name\":\"Dog\"},\"name\":\"Doggie\",\"photoUrls\":[\"www.testurl.com\"],\"tags\":[{\"id\":1,\"name\":\"Red\"}],\"status\":\"available\"}";
        RestAssured
                .given()
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(400);
    }

    /**
     * This test method is used to find pets by their status.
     * It iterates over an array of statuses and for each status, it sends a GET request to the "/pet/findByStatus" endpoint with the status as a query parameter.
     * It then validates the response to ensure that a 200 status code is returned, indicating a successful request.
     */
    @Test
    public void getFindPetByStatus() {
        String[] statuses = {"available", "pending", "sold"};

        for (String status : statuses) {
            RestAssured
                    .given()
                    .queryParam("status", status)
                    .when()
                    .get("/pet/findByStatus")
                    .then()
                    .statusCode(200);
        }
    }

    /**
     * This test method is used to handle the scenario when pets are searched by a status that does not exist.
     * It sends a GET request to the "/pet/findByStatus" endpoint with a non-existent status as a query parameter.
     * It then validates the response to ensure that a 400 status code is returned, indicating a bad request.
     */
    @Test
    public void getFindPetByStatusNotFound() {
        RestAssured
                .given()
                .when()
                .get("/pet/findByStatus?status=notavailable")
                .then()
                .statusCode(400);
    }

    /**
     * This test method is used to handle the scenario when pets are searched by status but no status is provided.
     * It sends a GET request to the "/pet/findByStatus" endpoint with an empty status as a query parameter.
     * It then validates the response to ensure that a 400 status code is returned, indicating a bad request.
     */
    @Test
    public void getFindPetByStatusBadRequest() {
        RestAssured
                .given()
                .when()
                .get("/pet/findByStatus?status=")
                .then()
                .statusCode(400);
    }

    /**
     * This test method is used to find pets by their tags.
     * It iterates over an array of tags and for each tag, it sends a GET request to the "/pet/findByTags" endpoint with the tag as a query parameter.
     * It then validates the response to ensure that a 200 status code is returned, indicating a successful request.
     */
    @Test
    public void getFindsPetsByTags() {
        String[] tags = {"tag1", "tag2", "tag3"};

        for (String tag : tags) {
            RestAssured
                    .given()
                    .queryParam("tags", tag)
                    .when()
                    .get("/pet/findByTags")
                    .then()
                    .statusCode(200);
        }
    }

    /**
     * This test method is used to handle the scenario when pets are searched by a tag that does not exist.
     * It sends a GET request to the "/pet/findByTags" endpoint with a non-existent tag as a query parameter.
     * It then validates the response to ensure that a 404 status code is returned, indicating that the requested resource could not be found.
     */
    @Test
    public void getFindsPetsByTagsNotFound() {
        RestAssured
                .given()
                .when()
                .get("/pet/findByTags?tags=notag")
                .then()
                .statusCode(404);
    }

    /**
     * This test method is used to handle the scenario when pets are searched by tags but no tag is provided.
     * It sends a GET request to the "/pet/findByTags" endpoint with an empty tag as a query parameter.
     * It then validates the response to ensure that a 400 status code is returned, indicating a bad request.
     */
    @Test
    public void getFindsPetsByTagsBadRequest() {
        RestAssured
                .given()
                .when()
                .get("/pet/findByTags?tags=")
                .then()
                .statusCode(400);
    }
}
