package order.list;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

public class OrderListTest {

  private static final String ORDER_PATH = "/api/v1/orders";
  private static final String URL = "http://qa-scooter.praktikum-services.ru";

  @Before
  public void setUp() {
    RestAssured.baseURI = URL;
  }

  @Test
  @DisplayName("Test get order list of /api/v1/orders")
  @Description("Check status code and response body contains `order`")
  public void courierCreation() {
    Response response = given().get(ORDER_PATH);

    response.then().assertThat().body("orders", notNullValue()).and().statusCode(200);
  }
}
