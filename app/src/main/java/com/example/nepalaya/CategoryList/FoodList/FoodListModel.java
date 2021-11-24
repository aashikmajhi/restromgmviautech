package com.example.nepalaya.CategoryList.FoodList;

public class FoodListModel {

    String ProductsID, Varientid, variantName,  ProductName, ProductImage, destcription,  price;
    int addones;

//    String prodID, addonsid, add_on_name, addonsprice;

    String qnty;

    public FoodListModel(String productsID, String varientid, String variantName, String productName, String productImage, String destcription, String price, int addones, String qnty) {
        ProductsID = productsID;
        Varientid = varientid;
        this.variantName = variantName;
        ProductName = productName;
        ProductImage = productImage;
        this.destcription = destcription;
        this.price = price;
        this.addones = addones;
        this.qnty = qnty;
    }

    public String getProductsID() {
        return ProductsID;
    }

    public String getVarientid() {
        return Varientid;
    }

    public String getVariantName() {
        return variantName;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public String getDestcription() {
        return destcription;
    }

    public String getPrice() {
        return price;
    }

    public int getAddones() {
        return addones;
    }

    public String getQnty() {
        return qnty;
    }
}
