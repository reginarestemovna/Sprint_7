package courier.creation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.UUID;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CourierCreationTest {

  private static final String COURIER_PATH = "/api/v1/courier";
  private static final String URL = "http://qa-scooter.praktikum-services.ru";

  private final Integer statusCode;
  private final String responseFieldName;
  private final Matcher<Object> responseFieldValueMatcher;
  private final CourierCreationRequest body;

  public CourierCreationTest(
      Integer statusCode,
      String responseFieldName,
      Matcher<Object> responseFieldValueMatcher,
      CourierCreationRequest body) {
    this.statusCode = statusCode;
    this.responseFieldName = responseFieldName;
    this.responseFieldValueMatcher = responseFieldValueMatcher;
    this.body = body;
  }

  @Parameterized.Parameters
  public static Object[][] getLocatorsAndText() {
    String parameter = UUID.randomUUID().toString();
    return new Object[][] {
      {201, "ok", equalTo(true), new CourierCreationRequest(parameter, parameter, parameter)},
      {
        409,
        "message",
        equalTo("Этот логин уже используется. Попробуйте другой."),
        new CourierCreationRequest(parameter, parameter, parameter)
      },
      {
        400,
        "message",
        equalTo("Недостаточно данных для создания учетной записи"),
        new CourierCreationRequest("", UUID.randomUUID().toString(), UUID.randomUUID().toString())
      },
      {
        400,
        "message",
        equalTo("Недостаточно данных для создания учетной записи"),
        new CourierCreationRequest(UUID.randomUUID().toString(), "", UUID.randomUUID().toString())
      },
    };
  }

  @Before
  public void setUp() {
    RestAssured.baseURI = URL;
  }

  @Test
  @DisplayName("Test courier creation of /api/v1/courier")
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
        .post(COURIER_PATH);
  }
}
