package com.example.nepalaya.CategoryList.AddToCartDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cart";
    SQLiteDatabase database;
    Context context;

/*
    String Query = "CREATE TABLE IF NOT EXISTS \"cart\" (\n" +
            "\t\"Cart_ID\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t\"Cart_INFO\"\tTEXT,\n" +
            "\t\"Cart_UNITPRICE\"\tTEXT,\n" +
            "\t\"Cart_UNIT\"\tTEXT,\n" +
            "\t\"Total_Price\"\tTEXT,\n" +
            "\t\"Notes\"\tTEXT,\n" +
            "\t\"Varient_ID\"\tTEXT,\n" +
            "\t\"Product_ID\"\tTEXT,\n" +
            "\t\"addonsid\"\tTEXT,\n" +
            "\t\"add_on_name\"\tTEXT,\n" +
            "\t\"addonsprice\"\tTEXT,\n" +
            "\t\"addonstotal\"\tTEXT\n" +
            ");";
*/


    String Query = "CREATE TABLE IF NOT EXISTS \"Cart\" (\n" +
            "\t\"Cart_ID\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t\"Cart_INFO\"\tTEXT,\n" +
            "\t\"Cart_UNITPRICE\"\tTEXT,\n" +
            "\t\"Cart_UNIT\"\tTEXT,\n" +
            "\t\"Total_Price\"\tTEXT,\n" +
            "\t\"Notes\"\tTEXT,\n" +
            "\t\"Varient_ID\"\tTEXT UNIQUE,\n" +
            "\t\"Product_ID\"\tTEXT,\n" +
            "\t\"addonsid\"\tTEXT,\n" +
            "\t\"add_on_name\"\tTEXT,\n" +
            "\t\"addonsprice\"\tTEXT,\n" +
            "\t\"addonstotal\"\tTEXT\n" +
            ");";

    String Addons = "CREATE TABLE IF NOT EXISTS \"Addones\" (\n" +
            "\t\"Varientid\"\tINTEGER,\n" +
            "\t\"Addonsquantity\"\tINTEGER,\n" +
            "\t\"addonsprice\"\tINTEGER,\n" +
            "\t\"addonsid\"\tINTEGER,\n" +
            "\tFOREIGN KEY(\"Varientid\") REFERENCES \"Cart\"(\"Varient_ID\")\n" +
            ");";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        getWritableDatabase().execSQL(Query); //Runnning Query
        getWritableDatabase().execSQL(Addons);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Query);
        sqLiteDatabase.execSQL(Addons);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }

    public void addadone(ContentValues contentValues) {
        getReadableDatabase().insert("Addones", "", contentValues);
        getWritableDatabase().close();
    }

    public void insertfood(ContentValues contentValues) {
        getReadableDatabase().insert("cart", "", contentValues);
        getWritableDatabase().close();
    }

    public List<OfflineCheckModel> getAllCartList() {
        List<OfflineCheckModel> offlineCheckModels = new ArrayList<>();

        String selectQuery = "SELECT * FROM cart";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            do {
                OfflineCheckModel offlineCheckModel = new OfflineCheckModel();
                offlineCheckModel.setVarient_ID(cursor.getString(cursor.getColumnIndex("Varient_ID")));
                offlineCheckModel.setCart_INFO(cursor.getString(cursor.getColumnIndex("Cart_INFO")));
                offlineCheckModel.setCart_UNIT(cursor.getString(cursor.getColumnIndex("Cart_UNIT")));
                offlineCheckModel.setTotal_Price(cursor.getString(cursor.getColumnIndex("Total_Price")));
                offlineCheckModel.setNotes(cursor.getString(cursor.getColumnIndex("Notes")));
                offlineCheckModel.setProduct_ID(cursor.getString(cursor.getColumnIndex("Product_ID")));
                offlineCheckModel.setAddonsid(cursor.getString(cursor.getColumnIndex("addonsid")));
                offlineCheckModel.setAdd_on_name(cursor.getString(cursor.getColumnIndex("add_on_name")));
                offlineCheckModel.setAddonstotal(cursor.getString(cursor.getColumnIndex("addonstotal")));
                offlineCheckModel.setCart_UNITPRICE(cursor.getString(cursor.getColumnIndex("Cart_UNITPRICE")));

                offlineCheckModels.add(offlineCheckModel);
            } while (cursor.moveToNext());

        }
        db.close();

        return offlineCheckModels;
    }

    public List<OfflineAddonsModel> getallAddonsList() {
        List<OfflineAddonsModel> offlineAddonsModels = new ArrayList<>();
        String selectQuery = "SELECT * FROM Addones";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToNext()) {
            do {

                OfflineAddonsModel offlineAddonsModel = new OfflineAddonsModel();

                offlineAddonsModel.setVarientid(cursor.getInt(cursor.getColumnIndex("Varientid")));
                offlineAddonsModel.setAddonsquantity(cursor.getInt(cursor.getColumnIndex("Addonsquantity")));
                offlineAddonsModel.setAddonsprice(cursor.getInt(cursor.getColumnIndex("addonsprice")));
                offlineAddonsModel.setAddonsid(cursor.getInt(cursor.getColumnIndex("addonsid")));

                offlineAddonsModels.add(offlineAddonsModel);
            } while (cursor.moveToNext());

        }
        db.close();
        return offlineAddonsModels;
    }

    public void deleteFood(String Varientid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cart", "Varient_ID " + "=?", new String[]{String.valueOf(Varientid)});
    }

    public void CartDelVarientid(String Varientid) { // Delete the addons while deleting the Varient
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Addones", "Varientid" + "=?", new String[]{String.valueOf(Varientid)});
    }


    public void deleteAddons(int Addonsid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Addones", "addonsid" + "=?", new String[]{String.valueOf(Addonsid)});
    }

    public void deletealladdons(int Varientid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Addones", "Varientid" + "=?", new String[]{String.valueOf(Varientid)});
    }

    public int updatefood(int Varientid, int Quanitity) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("Cart_UNIT", Quanitity);

        return db.update("cart", contentValues, "Varient_ID" + "= ?",
                new String[]{String.valueOf(Varientid)});
    }


    public int UpdateDatabaseAddones(int Addonsid, int Quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Addonsquantity", Quantity);

        return db.update("Addones", contentValues, "addonsid" + "= ?",
                new String[]{String.valueOf(Addonsid)});

    }

    public int updateforAddones(int Varientid, String AddonesName, float price) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("add_on_name", AddonesName);

        contentValues.put("addonstotal", price);


        return sqLiteDatabase.update("cart", contentValues, "Varient_ID" + "=?",
                new String[]{String.valueOf(Varientid)});


    }

    public void Deletedatabase() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS Addones");
        db.execSQL("DROP TABLE IF EXISTS Cart");
        db.close();


    }
}
