package order.creation;

import java.util.List;

public class OrderCreationRequest {

  private String phone;
  private Long rentTime;
  private String address;
  private String comment;
  private String lastName;
  private String firstName;
  private String metroStation;
  private String deliveryDate;
  private List<String> color;

  public OrderCreationRequest() {}

  public OrderCreationRequest(
      String phone,
      Long rentTime,
      String address,
      String comment,
      String lastName,
      String firstName,
      String metroStation,
      String deliveryDate,
      List<String> color) {
    this.phone = phone;
    this.rentTime = rentTime;
    this.address = address;
    this.comment = comment;
    this.lastName = lastName;
    this.firstName = firstName;
    this.metroStation = metroStation;
    this.deliveryDate = deliveryDate;
    this.color = color;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Long getRentTime() {
    return rentTime;
  }

  public void setRentTime(Long rentTime) {
    this.rentTime = rentTime;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMetroStation() {
    return metroStation;
  }

  public void setMetroStation(String metroStation) {
    this.metroStation = metroStation;
  }

  public String getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(String deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public List<String> getColor() {
    return color;
  }

  public void setColor(List<String> color) {
    this.color = color;
  }
}
