package com.example.nepalaya.FoodHome;

public class AddsonesModel {

    String prodID, addonsid, add_on_name, addonsprice;

    public AddsonesModel(String prodID, String addonsid, String add_on_name, String addonsprice) {
        this.prodID = prodID;
        this.addonsid = addonsid;
        this.add_on_name = add_on_name;
        this.addonsprice = addonsprice;
    }

    public String getProdID() {
        return prodID;
    }

    public String getAddonsid() {
        return addonsid;
    }

    public String getAdd_on_name() {
        return add_on_name;
    }

    public String getAddonsprice() {
        return addonsprice;
    }
}
