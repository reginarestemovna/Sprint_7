package courier.login;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import api.CourierApi;
import base.BaseTest;
import courier.creation.CourierCreationRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import java.util.UUID;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CourierLoginTest extends BaseTest {
  private static final String LOGIN = UUID.randomUUID().toString();
  private static final String PASSWORD = UUID.randomUUID().toString();

  private final Integer statusCode;
  private final String responseFieldName;
  private final Matcher<Object> responseFieldValueMatcher;
  private final CourierLoginRequest body;

  private CourierApi courierApi;

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

  @Before
  @Override
  public void setUp() {
    super.setUp();
    courierApi = new CourierApi();

    CourierCreationRequest courierCreationRequest =
        new CourierCreationRequest(LOGIN, PASSWORD, UUID.randomUUID().toString());
    courierApi.create(courierCreationRequest);
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

  @Test
  @DisplayName("Test courier login of /api/v1/courier/login")
  @Description("Check status code and response message")
  public void courierLogin() {
    Response response = courierApi.login(body);

    testResponse(response, responseFieldName, responseFieldValueMatcher, statusCode);
  }

  @After
  public void cleanup() {
    courierApi.deleteTestData(LOGIN, PASSWORD);
  }
}
