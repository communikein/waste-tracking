package com.example.xyzreader.data.model;

public class UpdateThing {

    private int schemaIndex;
    private Block data;

    public UpdateThing(int schemaIndex, Block data) {
        this.schemaIndex = schemaIndex;
        this.data = data;
    }

    public int getSchemaIndex() {
        return schemaIndex;
    }

    public void setSshemaIndex(int schemaIndex) {
        this.schemaIndex = schemaIndex;
    }

    public Block getData() {
        return data;
    }

    public void setData(Block data) {
        this.data = data;
    }

}
