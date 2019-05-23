package com.example.xyzreader.data.model;

import java.util.ArrayList;

public class CreateThing {

    private ArrayList<String> identities;
    private String thing_name;
    private int schema_id;
    private Block data;

    public CreateThing(ArrayList<String> identities, String thing_name, int schema_id, Block data) {
        this.identities = identities;
        this.thing_name = thing_name;
        this.schema_id = schema_id;
        this.data = data;
    }

    public ArrayList<String> getIdentities() {
        return identities;
    }

    public void setIdentities(ArrayList<String> identities) {
        this.identities = identities;
    }

    public String getThing_name() {
        return thing_name;
    }

    public void setThing_name(String thing_name) {
        this.thing_name = thing_name;
    }

    public int getSchema_id() {
        return schema_id;
    }

    public void setSchema_id(int schema_id) {
        this.schema_id = schema_id;
    }

    public Block getData() {
        return data;
    }

    public void setData(Block data) {
        this.data = data;
    }
}
