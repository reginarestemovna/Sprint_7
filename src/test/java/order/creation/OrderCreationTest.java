package order.creation;

import static order.color.Color.BLACK;
import static order.color.Color.GREY;
import static org.hamcrest.Matchers.notNullValue;

import api.OrderApi;
import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
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
public class OrderCreationTest extends BaseTest {

  private final Integer statusCode;
  private final String responseFieldName;
  private final Matcher<Object> responseFieldValueMatcher;
  private final OrderCreationRequest body;

  private OrderApi orderApi;

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

  @Before
  @Override
  public void setUp() {
    super.setUp();
    orderApi = new OrderApi();
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

  @Test
  @DisplayName("Test order creation of /api/v1/orders")
  @Description("Check status code and response message")
  public void orderCreation() {
    Response response = orderApi.create(body);

    testResponse(response, responseFieldName, responseFieldValueMatcher, statusCode);
  }
}
