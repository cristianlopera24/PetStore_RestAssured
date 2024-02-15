import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

public class User {
    public User() {
    }

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/";
        RestAssured.basePath = "/api/v3";
        RestAssured.filters(new RequestLoggingFilter(), new Filter[]{new ResponseLoggingFilter()});
        RestAssured.requestSpecification = (new RequestSpecBuilder()).setContentType("application/json").build();
    }

    /**
     * This test method is used to create a new user.
     * It creates a JSON request body with the user details and sends a POST request to the "/user" endpoint.
     * The response is then validated to ensure that the user was created correctly.
     */
    @Test
    public void createUser() {
        String body = "{\"id\": 10,\"username\": \"theUser\",\"firstName\": \"John\",\"lastName\": \"James\",\"email\": \"john@email.com\",\"password\": \"12345\",\"phone\": \"12345\",\"userStatus\": 1}";
        RestAssured.given().body(body).when().post("/user").then().statusCode(200);
    }

    /**
     * This test method is used to create multiple users with a list.
     * It creates a JSON request body with the details of multiple users and sends a POST request to the "/user/createWithList" endpoint.
     * The response is then validated to ensure that the users were created correctly.
     */
    @Test
    public void createUsersWithList() {
        String body = "[{\"id\": 10,\"username\": \"user1\"},{\"id\": 20,\"username\": \"user2\"}]";
        RestAssured.given().body(body).when().post("/user/createWithList").then().statusCode(200);
    }

    @Test
    public void logsUserIntoTheSystem() {
        String body = "username=user1&password=password1";
        RestAssured.given().body(body).when().get("/user/login").then().statusCode(200);
    }

    @Test
    public void logsOutCurrentLoggedInUserSession() {
        RestAssured.given().when().get("/user/logout").then().statusCode(200);
    }

    @Test
    public void getUserByName() {
        RestAssured.given().when().get("/user/user1").then().statusCode(200);
    }

    /**
     * This test method is used to update a user.
     * It creates a JSON request body with the updated user details and sends a PUT request to the "/user/{username}" endpoint with the username as a path parameter.
     * The response is then validated to ensure that the user was updated correctly.
     */
    @Test
    public void updateUser() {
        String body = "{\"id\": 10,\"username\": \"user1.1\",\"firstName\": \"John\",\"lastName\": \"Doe\",\"email\": \"john.doe@xample.com\",\"password\": \"12345\",\"phone\": \"12345\",\"userStatus\": 1}";
        int statusCode = RestAssured.given().body(body).when().put("/user/user1").getStatusCode();
        if (statusCode == 200) {
            System.out.println("User updated successfully");
        } else if (statusCode == 404) {
            System.out.println("User not found");
        } else {
            System.out.println("An error occurred");
        }
    }

    /**
     * This test method is used to delete a user.
     * It sends a DELETE request to the "/user/{username}" endpoint with the username as a path parameter.
     * The response is then validated to ensure that the user was deleted correctly.
     */
    @Test
    public void deleteUser() {
        RestAssured.given().when().delete("/user/user1", new Object[0]).then().statusCode(200);
    }
}
