package api;

public enum Path {
  ORDER_PATH("/api/v1/orders"),
  COURIER_CREATION_PATH("/api/v1/courier"),
  COURIER_LOGIN_PATH("/api/v1/courier/login");

  Path(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }

  private final String path;
}
