package courier.login;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import courier.creation.CourierCreationRequest;
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
public class CourierLoginTest {
  private static final String COURIER_CREATION_PATH = "/api/v1/courier";
  private static final String COURIER_LOGIN_PATH = "/api/v1/courier/login";
  private static final String URL = "http://qa-scooter.praktikum-services.ru";

  private static final String LOGIN = UUID.randomUUID().toString();
  private static final String PASSWORD = UUID.randomUUID().toString();

  private final Integer statusCode;
  private final String responseFieldName;
  private final Matcher<Object> responseFieldValueMatcher;
  private final CourierLoginRequest body;

  public CourierLoginTest(
      Integer statusCode,
      String responseFieldName,
      Matcher<Object> responseFieldValueMatcher,
      CourierLoginRequest body) {
    this.statusCode = statusCode;
    this.responseFieldName = responseFieldName;
    this.responseFieldValueMatcher = responseFieldValueMatcher;
    this.body = body;
  }

  @Parameterized.Parameters
  public static Object[][] getLocatorsAndText() {
    return new Object[][] {
      {200, "id", notNullValue(), new CourierLoginRequest(LOGIN, PASSWORD)},
      {
        400,
        "message",
        equalTo("Недостаточно данных для входа"),
        new CourierLoginRequest("", PASSWORD)
      },
      {
        400, "message", equalTo("Недостаточно данных для входа"), new CourierLoginRequest(LOGIN, "")
      },
      {
        404,
        "message",
        equalTo("Учетная запись не найдена"),
        new CourierLoginRequest(LOGIN + "test", PASSWORD)
      },
      {
        404,
        "message",
        equalTo("Учетная запись не найдена"),
        new CourierLoginRequest(LOGIN, PASSWORD + "test")
      },
    };
  }

  @Before
  public void setUp() {
    RestAssured.baseURI = URL;

    CourierCreationRequest courierCreationRequest =
        new CourierCreationRequest(LOGIN, PASSWORD, UUID.randomUUID().toString());

    given()
        .header("Content-type", "application/json")
        .and()
        .body(courierCreationRequest)
        .when()
        .post(COURIER_CREATION_PATH);
  }

  @Test
  @DisplayName("Test courier login of /api/v1/courier/login")
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
        .post(COURIER_LOGIN_PATH);
  }
}
