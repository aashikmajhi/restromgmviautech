package com.example.nepalaya.OrderList.OrderTabs.Pending;

public class PendingModel {

    String orderid, CustomerName, TableName, OrderDate, TotalAmount;

    public PendingModel(String orderid, String customerName, String tableName, String orderDate, String totalAmount) {
        this.orderid = orderid;
        CustomerName = customerName;
        TableName = tableName;
        OrderDate = orderDate;
        TotalAmount = totalAmount;
    }

    public String getOrderid() {
        return orderid;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public String getTableName() {
        return TableName;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }
}
