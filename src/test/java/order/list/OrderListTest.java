package order.list;

import static org.hamcrest.Matchers.notNullValue;

import api.OrderApi;
import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

public class OrderListTest extends BaseTest {

  private OrderApi orderApi;

  @Before
  @Override
  public void setUp() {
    super.setUp();
    orderApi = new OrderApi();
  }

  @Test
  @DisplayName("Test get order list of /api/v1/orders")
  @Description("Check status code and response body contains `order`")
  public void orderList() {
    Response response = orderApi.list();

    testResponse(response, "orders", notNullValue(), 200);
  }
}
