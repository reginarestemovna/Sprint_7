package order.creation;

import static io.restassured.RestAssured.given;
import static order.color.Color.BLACK;
import static order.color.Color.GREY;
import static org.hamcrest.Matchers.notNullValue;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class OrderCreationTest {

  private static final String ORDER_PATH = "/api/v1/orders";
  private static final String URL = "http://qa-scooter.praktikum-services.ru";

  private final Integer statusCode;
  private final String responseFieldName;
  private final Matcher<Object> responseFieldValueMatcher;
  private final OrderCreationRequest body;

  public OrderCreationTest(
      Integer statusCode,
      String responseFieldName,
      Matcher<Object> responseFieldValueMatcher,
      OrderCreationRequest body) {
    this.statusCode = statusCode;
    this.responseFieldName = responseFieldName;
    this.responseFieldValueMatcher = responseFieldValueMatcher;
    this.body = body;
  }

  @Parameterized.Parameters
  public static Object[][] getLocatorsAndText() {
    String parameter = UUID.randomUUID().toString();
    return new Object[][] {
      {
        201,
        "track",
        notNullValue(),
        new OrderCreationRequest(
            parameter,
            1L,
            parameter,
            parameter,
            parameter,
            parameter,
            parameter,
            "2222-12-31",
            List.of(BLACK.toString()))
      },
      {
        201,
        "track",
        notNullValue(),
        new OrderCreationRequest(
            parameter,
            1L,
            parameter,
            parameter,
            parameter,
            parameter,
            parameter,
            "2222-12-31",
            List.of(GREY.toString()))
      },
      {
        201,
        "track",
        notNullValue(),
        new OrderCreationRequest(
            parameter,
            1L,
            parameter,
            parameter,
            parameter,
            parameter,
            parameter,
            "2222-12-31",
            List.of(GREY.toString(), BLACK.toString()))
      },
      {
        201,
        "track",
        notNullValue(),
        new OrderCreationRequest(
            parameter,
            1L,
            parameter,
            parameter,
            parameter,
            parameter,
            parameter,
            "2222-12-31",
            new ArrayList<>())
      },
    };
  }

  @Before
  public void setUp() {
    RestAssured.baseURI = URL;
  }

  @Test
  @DisplayName("Test order creation of /api/v1/orders")
  @Description("Check status code and response message")
  public void courierCreation() {
    Response response = sendRequest();

    response
        .then()
        .assertThat()
        .body(responseFieldName, responseFieldValueMatcher)
        .and()
        .statusCode(statusCode);
  }

  @Step("Send request to server")
  public Response sendRequest() {
    return given()
        .header("Content-type", "application/json")
        .and()
        .body(body)
        .when()
        .post(ORDER_PATH);
  }
}
