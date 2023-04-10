package api;

import static api.Path.*;
import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import order.creation.OrderCreationRequest;

public class OrderApi extends ServerApi {

  @Step("Order creation")
  public Response create(OrderCreationRequest body) {
    return sendPostRequest(body, ORDER_PATH.getPath());
  }

  @Step("Order list")
  public Response list() {
    return given().get(ORDER_PATH.getPath());
  }
}
