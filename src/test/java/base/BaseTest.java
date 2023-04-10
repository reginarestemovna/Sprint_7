package base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.junit.Before;

public class BaseTest {

  private static final String URL = "http://qa-scooter.praktikum-services.ru";

  @Before
  public void setUp() {
    RestAssured.baseURI = URL;
  }

  protected void testResponse(
      Response response,
      String responseFieldName,
      Matcher<Object> responseFieldValueMatcher,
      Integer statusCode) {
    response
        .then()
        .assertThat()
        .body(responseFieldName, responseFieldValueMatcher)
        .and()
        .statusCode(statusCode);
  }
}
