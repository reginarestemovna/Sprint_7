package courier.creation;

import static org.hamcrest.Matchers.equalTo;

import api.CourierApi;
import base.BaseTest;
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
public class CourierCreationTest extends BaseTest {

  private static final String LOGIN = UUID.randomUUID().toString();
  private static final String PASSWORD = UUID.randomUUID().toString();

  private final Integer statusCode;
  private final String responseFieldName;
  private final CourierCreationRequest body;
  private final Matcher<Object> responseFieldValueMatcher;

  private CourierApi courierApi;

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

  @Before
  @Override
  public void setUp() {
    super.setUp();
    courierApi = new CourierApi();
  }

  @Parameterized.Parameters
  public static Object[][] getLocatorsAndText() {
    return new Object[][] {
      {
        201,
        "ok",
        equalTo(true),
        new CourierCreationRequest(LOGIN, PASSWORD, UUID.randomUUID().toString())
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

  @Test
  @DisplayName("Test courier creation of /api/v1/courier")
  @Description("Check status code and response message")
  public void courierCreation() {
    Response response = courierApi.create(body);

    testResponse(response, responseFieldName, responseFieldValueMatcher, statusCode);
  }

  @After
  public void cleanup() {
    courierApi.deleteTestData(LOGIN, PASSWORD);
  }
}
