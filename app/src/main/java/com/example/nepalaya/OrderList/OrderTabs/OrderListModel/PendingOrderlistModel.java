package com.example.nepalaya.OrderList.OrderTabs.OrderListModel;

public class PendingOrderlistModel {

    int Dproductid, Dprice, DVarientid, DItemqty, Daddons;
    String DProductName, DVarientName;





    public PendingOrderlistModel(int dproductid, int dprice, int DVarientid, int DItemqty, int daddons, String DProductName, String DVarientName) {
        Dproductid = dproductid;
        Dprice = dprice;
        this.DVarientid = DVarientid;
        this.DItemqty = DItemqty;
        Daddons = daddons;
        this.DProductName = DProductName;
        this.DVarientName = DVarientName;
    }



    public int getDproductid() {
        return Dproductid;
    }

    public int getDprice() {
        return Dprice;
    }

    public int getDVarientid() {
        return DVarientid;
    }

    public int getDItemqty() {
        return DItemqty;
    }

    public int getDaddons() {
        return Daddons;
    }

    public String getDProductName() {
        return DProductName;
    }

    public String getDVarientName() {
        return DVarientName;
    }
}
