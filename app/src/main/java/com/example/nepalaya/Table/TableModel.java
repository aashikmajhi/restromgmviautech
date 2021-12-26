package com.example.nepalaya.Table;

public class TableModel {
    String Table_id, Table_name, Table_location;

    public TableModel(String table_id, String table_name, String table_location) {
        Table_id = table_id;
        Table_name = table_name;
        Table_location = table_location;
    }

    public String getTable_id() {
        return Table_id;
    }

    public String getTable_name() {
        return Table_name;
    }


    public java.lang.String getTable_location() {
        return Table_location;
    }
}
