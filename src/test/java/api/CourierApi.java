package api;

import static api.Path.COURIER_CREATION_PATH;
import static api.Path.COURIER_LOGIN_PATH;
import static io.restassured.RestAssured.given;

import courier.creation.CourierCreationRequest;
import courier.login.CourierLoginRequest;
import courier.login.CourierLoginResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class CourierApi extends ServerApi {

  @Step("Courier creation")
  public Response create(CourierCreationRequest body) {
    return sendPostRequest(body, COURIER_CREATION_PATH.getPath());
  }

  @Step("Courier login")
  public Response login(CourierLoginRequest body) {
    return sendPostRequest(body, COURIER_LOGIN_PATH.getPath());
  }

  @Step("Courier delete")
  public Response delete(Long id) {
    return given().delete(COURIER_CREATION_PATH.getPath() + "/" + id);
  }

  @Step("Delete test data")
  public void deleteTestData(String login, String password) {
    Response response = login(new CourierLoginRequest(login, password));

    CourierLoginResponse courierLoginResponse = response.as(CourierLoginResponse.class);
    delete(courierLoginResponse.getId());
  }
}
