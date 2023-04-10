package api;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class ServerApi {

  @Step("Send post request to server")
  protected Response sendPostRequest(Object body, String path) {
    return given().header("Content-type", "application/json").and().body(body).when().post(path);
  }
}
