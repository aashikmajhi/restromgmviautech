package com.example.nepalaya.CategoryList.AddToCartDatabase;

public class OfflineCheckModel {


    String Varient_ID, Cart_INFO, Cart_UNIT, Total_Price, Notes, Product_ID, addonsid, add_on_name, addonsprice, addonstotal, Cart_UNITPRICE;

    public OfflineCheckModel(String varient_ID, String cart_INFO, String cart_UNIT, String total_Price, String notes, String product_ID, String addonsid, String add_on_name, String addonsprice, String addonstotal, String cart_UNITPRICE) {
        Varient_ID = varient_ID;
        Cart_INFO = cart_INFO;
        Cart_UNIT = cart_UNIT;
        Total_Price = total_Price;
        Notes = notes;
        Product_ID = product_ID;
        this.addonsid = addonsid;
        this.add_on_name = add_on_name;
        this.addonsprice = addonsprice;
        this.addonstotal = addonstotal;
        Cart_UNITPRICE = cart_UNITPRICE;
    }

    public OfflineCheckModel() {

    }

    public String getVarient_ID() {
        return Varient_ID;
    }

    public void setVarient_ID(String varient_ID) {
        Varient_ID = varient_ID;
    }

    public String getCart_INFO() {
        return Cart_INFO;
    }

    public void setCart_INFO(String cart_INFO) {
        Cart_INFO = cart_INFO;
    }

    public String getCart_UNIT() {
        return Cart_UNIT;
    }

    public void setCart_UNIT(String cart_UNIT) {
        Cart_UNIT = cart_UNIT;
    }

    public String getTotal_Price() {
        return Total_Price;
    }

    public void setTotal_Price(String total_Price) {
        Total_Price = total_Price;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(String product_ID) {
        Product_ID = product_ID;
    }

    public String getAddonsid() {
        return addonsid;
    }

    public void setAddonsid(String addonsid) {
        this.addonsid = addonsid;
    }

    public String getAdd_on_name() {
        return add_on_name;
    }

    public void setAdd_on_name(String add_on_name) {
        this.add_on_name = add_on_name;
    }

    public String getAddonsprice() {
        return addonsprice;
    }

    public void setAddonsprice(String addonsprice) {
        this.addonsprice = addonsprice;
    }

    public String getAddonstotal() {
        return addonstotal;
    }

    public void setAddonstotal(String addonstotal) {
        this.addonstotal = addonstotal;
    }

    public String getCart_UNITPRICE() {
        return Cart_UNITPRICE;
    }

    public void setCart_UNITPRICE(String cart_UNITPRICE) {
        Cart_UNITPRICE = cart_UNITPRICE;
    }
}
