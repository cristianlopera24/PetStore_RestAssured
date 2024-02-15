import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class Store {
    public Store() {
    }

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/";
        RestAssured.basePath = "/api/v3";
        RestAssured.filters(new RequestLoggingFilter(), new Filter[]{new ResponseLoggingFilter()});
        RestAssured.requestSpecification = (new RequestSpecBuilder()).setContentType("application/json").build();
    }

    /**
     * This test method is used to check the status of pet inventories.
     * It sends a GET request to the "/store/inventory" endpoint.
     * The response is then validated to ensure that the status of the pet inventories is as expected.
     * The status code of the response is checked to be 200, indicating a successful request.
     * The body of the response is also checked to ensure that the status of the pet inventories is as expected.
     */
    @Test
    public void returnsPetInventoriesByStatus() {
        RestAssured.given()
                .when()
                .get("/store/inventory")
                .then()
                .statusCode(200)
                .body("approved", CoreMatchers.notNullValue())
                .body("placed", CoreMatchers.notNullValue())
                .body("delivered", Matchers.is(50));
    }

    /**
     * This test method is used to place an order for a pet.
     * It creates a JSON request body with the order details and sends a POST request to the "/store/order" endpoint.
     * The response is then validated to ensure that the order was placed correctly.
     */
    @Test
    public void placeAnOrderForAPet() {
        String body = "{ \"id\": 1, \"petId\": 0, \"quantity\": 1, \"shipDate\": \"2021-07-01T12:00:00.000Z\", \"status\": \"placed\", \"complete\": true }";
        RestAssured.given()
                .body(body)
                .when()
                .post("/store/order")
                .then().statusCode(200)
                .body("id", Matchers.is(1))
                .body("petId", Matchers.is(0))
                .body("quantity", Matchers.is(1))
                .body("status", Matchers.is("placed"))
                .body("complete", Matchers.is(true));
    }

    /**
     * This test method is used to find a purchase order by its ID.
     * It sends a GET request to the "/store/order/1" endpoint.
     * The response is then validated to ensure that the correct order details are returned.
     */
    @Test
    public void findPurchaseOrderById() {
        Response response = RestAssured.given().when().get("/store/order/1");
        if (response.getStatusCode() == 200) {
            response.then()
                    .body("id", Matchers.is(1))
                    .body("petId", Matchers.is(0))
                    .body("quantity", Matchers.is(1))
                    .body("status", Matchers.is("placed"))
                    .body("complete", Matchers.is(true));
        } else {
            MatcherAssert.assertThat(response.getBody().asString(), CoreMatchers.containsString("Order not found"));
        }
    }

    /**
     * This test method is used to delete a purchase order by its ID.
     * It sends a DELETE request to the "/store/order/1" endpoint.
     * The response is then validated to ensure that the order was deleted correctly.
     */
    @Test
    public void deletePurchaseOrderById() {
        RestAssured.given().when().delete("/store/order/1").then().statusCode(200);
    }

    /**
     * This test method is used to place an order for a pet with invalid data.
     * It creates a JSON request body with the invalid order details and sends a POST request to the "/store/order" endpoint.
     * The response is then validated to ensure that a 400 status code is returned, indicating a bad request.
     */
    @Test
    public void placeAnOrderForAPetWithInvalidData() {
        String body = "{ \"id\": 0, \"petId\": 0, \"quantity\": 0, \"shipDate\": \"2021-07-01T12\", \"status\": \"placed\", \"complete\": true }";
        RestAssured.given().body(body).when().post("/store/order").then().statusCode(400);
    }

    /**
     * This test method is used to delete a purchase order by an invalid ID.
     * It sends a DELETE request to the "/store/order/0" endpoint.
     * The response is then validated to ensure that a 404 status code is returned, indicating that the order was not found.
     */
    @Test
    public void deletePurchaseOrderByIdWithInvalidId() {
        RestAssured.given().when().delete("/store/order/0").then().statusCode(404);
    }
}
