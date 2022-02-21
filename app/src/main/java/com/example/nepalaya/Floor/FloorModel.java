package com.example.nepalaya.Floor;

public class FloorModel {

    String Floor_id, Floor_name;

    public FloorModel(String floor_id, String floor_name) {
        Floor_id = floor_id;
        Floor_name = floor_name;
    }

    public String getFloor_id() {
        return Floor_id;
    }

    public String getFloor_name() {
        return Floor_name;
    }
}
