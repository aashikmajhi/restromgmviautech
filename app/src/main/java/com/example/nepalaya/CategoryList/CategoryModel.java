package com.example.nepalaya.CategoryList;

public class CategoryModel {

    int CatID;
    String CatName;
    String CatImage;


    public CategoryModel(int catID, String catName, String catImage) {
        CatID = catID;
        CatName = catName;
        CatImage = catImage;
    }

    public int getCatID() {
        return CatID;
    }

    public String getCatName() {
        return CatName;
    }

    public String getCatImage() {
        return CatImage;
    }
}
