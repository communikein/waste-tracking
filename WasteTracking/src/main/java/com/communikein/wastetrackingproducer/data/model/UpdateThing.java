package com.communikein.wastetrackingproducer.data.model;

import com.google.gson.JsonObject;

/**
 * This is a helper class, needed to update Things in the BlockChain.
 */
public class UpdateThing {

    private int schemaIndex;
    private JsonObject data;

    public UpdateThing(int schemaIndex, JsonObject data) {
        this.schemaIndex = schemaIndex;
        this.data = data;
    }

    public int getSchemaIndex() {
        return schemaIndex;
    }

    public void setSshemaIndex(int schemaIndex) {
        this.schemaIndex = schemaIndex;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

}
