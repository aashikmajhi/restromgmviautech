package com.example.nepalaya.OrderList.OrderTabs.OrderListModel;

public class PenAddonslistModel {

    int addonsvarientid;
    String addonsName, add_on_id, add_on_qty;
    float addprice;

    public PenAddonslistModel(int addonsvarientid, String addonsName, String add_on_id, float addprice, String add_on_qty) {
        this.addonsvarientid = addonsvarientid;
        this.addonsName = addonsName;
        this.add_on_id = add_on_id;
        this.addprice = addprice;
        this.add_on_qty = add_on_qty;
    }

    public int getAddonsvarientid() {
        return addonsvarientid;
    }

    public String getAddonsName() {
        return addonsName;
    }

    public String getAdd_on_id() {
        return add_on_id;
    }

    public float getAddprice() {
        return addprice;
    }

    public String getAdd_on_qty() {
        return add_on_qty;
    }
}
