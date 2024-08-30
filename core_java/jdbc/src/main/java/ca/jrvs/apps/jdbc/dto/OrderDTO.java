package ca.jrvs.apps.jdbc.dto;

import ca.jrvs.apps.jdbc.util.DataTransferObject;
import java.util.Date;

public class OrderDTO implements DataTransferObject {
  private long orderId;
  private Date creationDate;
  private int totalDue;
  private String status;

  private String customerFirstName;
  private String customerLastName;
  private String customerEmail;

  private String salespersonFirstName;
  private String salespersonLastName;
  private String salespersonEmail;

  private int orderItemQuantity;

  private String productCode;
  private String productName;
  private int productSize;
  private String productVariety;
  private int productPrice;

  @Override
  public long getId() {
    return orderId;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public int getTotalDue() {
    return totalDue;
  }

  public String getStatus() {
    return status;
  }

  public String getCustomerFirstName() {
    return customerFirstName;
  }

  public String getCustomerLastName() {
    return customerLastName;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public String getSalespersonFirstName() {
    return salespersonFirstName;
  }

  public String getSalespersonLastName() {
    return salespersonLastName;
  }

  public String getSalespersonEmail() {
    return salespersonEmail;
  }

  public int getOrderItemQuantity() {
    return orderItemQuantity;
  }

  public String getProductCode() {
    return productCode;
  }

  public String getProductName() {
    return productName;
  }

  public int getProductSize() {
    return productSize;
  }

  public String getProductVariety() {
    return productVariety;
  }

  public int getProductPrice() {
    return productPrice;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public void setTotalDue(int totalDue) {
    this.totalDue = totalDue;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setCustomerFirstName(String customerFirstName) {
    this.customerFirstName = customerFirstName;
  }

  public void setCustomerLastName(String customerLastName) {
    this.customerLastName = customerLastName;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public void setSalespersonFirstName(String salespersonFirstName) {
    this.salespersonFirstName = salespersonFirstName;
  }

  public void setSalespersonLastName(String salespersonLastName) {
    this.salespersonLastName = salespersonLastName;
  }

  public void setSalespersonEmail(String salespersonEmail) {
    this.salespersonEmail = salespersonEmail;
  }

  public void setOrderItemQuantity(int orderItemQuantity) {
    this.orderItemQuantity = orderItemQuantity;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public void setProductSize(int productSize) {
    this.productSize = productSize;
  }

  public void setProductVariety(String productVariety) {
    this.productVariety = productVariety;
  }

  public void setProductPrice(int productPrice) {
    this.productPrice = productPrice;
  }
}
