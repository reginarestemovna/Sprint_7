package courier.creation;

import static org.hamcrest.Matchers.equalTo;

import api.CourierApi;
import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CourierCreationDuplicateTest extends BaseTest {

  private static final String LOGIN = UUID.randomUUID().toString();
  private static final String PASSWORD = UUID.randomUUID().toString();

  private CourierApi courierApi;

  @Before
  @Override
  public void setUp() {
    super.setUp();
    courierApi = new CourierApi();
  }

  @Test
  @DisplayName("Test courier duplicate creation of /api/v1/courier")
  @Description("Check status code and response message")
  public void courierCreation() {

    CourierCreationRequest courierCreationRequest =
        new CourierCreationRequest(LOGIN, PASSWORD, UUID.randomUUID().toString());

    courierApi.create(courierCreationRequest);

    Response response = courierApi.create(courierCreationRequest);

    testResponse(
        response, "message", equalTo("Этот логин уже используется. Попробуйте другой."), 409);
  }

  @After
  public void cleanup() {
    courierApi.deleteTestData(LOGIN, PASSWORD);
  }
}
