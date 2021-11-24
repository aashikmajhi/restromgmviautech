package com.example.nepalaya.CategoryList.AddToCartDatabase;

public class OfflineAddonsModel {

    int Varientid, Addonsquantity, addonsprice, addonsid;

    public OfflineAddonsModel(int varientid, int addonsquantity, int addonsprice, int addonsid) {
        Varientid = varientid;
        Addonsquantity = addonsquantity;
        this.addonsprice = addonsprice;
        this.addonsid = addonsid;
    }

    public OfflineAddonsModel() {

    }

    public int getVarientid() {
        return Varientid;
    }

    public void setVarientid(int varientid) {
        Varientid = varientid;
    }

    public int getAddonsquantity() {
        return Addonsquantity;
    }

    public void setAddonsquantity(int addonsquantity) {
        Addonsquantity = addonsquantity;
    }

    public int getAddonsprice() {
        return addonsprice;
    }

    public void setAddonsprice(int addonsprice) {
        this.addonsprice = addonsprice;
    }

    public int getAddonsid() {
        return addonsid;
    }

    public void setAddonsid(int addonsid) {
        this.addonsid = addonsid;
    }
}
