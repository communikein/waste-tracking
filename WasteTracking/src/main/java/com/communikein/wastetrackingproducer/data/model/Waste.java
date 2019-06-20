package com.communikein.wastetrackingproducer.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract;
import com.communikein.wastetrackingproducer.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import static com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_LANDFILL_AIR_PARAMETERS;
import static com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_LANDFILL_GAS_PRODUCTION;
import static com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_LANDFILL_WATER_PARAMETERS;
import static com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_POWER_ENERGY_PRODUCTION;
import static com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_POWER_HEATING_LEVELS;
import static com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_RECYCLED_PROCESS_WASTE;
import static com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_RECYCLED_QUANTITY;
import static com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_TREATMENT_TYPE;
import static com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_WASTE_ID;
import static com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_WASTE_PARAMETERS;
import static com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_WASTE_QUALITY;
import static com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_WASTE_TYPE;
import static com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_WASTE_VOLUME;
import static com.communikein.wastetrackingproducer.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_WASTE_WEIGHT;

/**
 * This class represents the Waste object.
 */
@Entity(tableName = BlockChainContract.BlockEntry.TABLE_NAME)
public class Waste implements Parcelable {

    public static final String WASTE_TYPE_PLASTIC = "plastic";
    public static final String WASTE_TYPE_METAL = "metal";
    public static final String WASTE_TYPE_GLASS = "glass";
    public static final String WASTE_TYPE_PAPER = "paper";
    public static final String WASTE_TYPE_ORGANIC = "organic";

    @SerializedName(COLUMN_WASTE_ID)
    @PrimaryKey
    @ColumnInfo(index = true, name = COLUMN_WASTE_ID)
    @NonNull
    private String id;

    @SerializedName(COLUMN_WASTE_TYPE)
    @ColumnInfo(name = COLUMN_WASTE_TYPE)
    private String type;

    @SerializedName(COLUMN_WASTE_WEIGHT)
    @ColumnInfo(name = COLUMN_WASTE_WEIGHT)
    private double weight;

    @SerializedName(COLUMN_WASTE_VOLUME)
    @ColumnInfo(name = COLUMN_WASTE_VOLUME)
    private double volume;

    @SerializedName(COLUMN_WASTE_QUALITY)
    @ColumnInfo(name = COLUMN_WASTE_QUALITY)
    private String quality;

    @SerializedName(COLUMN_WASTE_PARAMETERS)
    @ColumnInfo(name = COLUMN_WASTE_PARAMETERS)
    private String parameters;


    @SerializedName(COLUMN_TREATMENT_TYPE)
    @ColumnInfo(name = COLUMN_TREATMENT_TYPE)
    private String treatmentType;


    // The following two fields are used when a new Waste object is created after going through a
    // Recycler Facility.
    @SerializedName(COLUMN_RECYCLED_QUANTITY)
    @ColumnInfo(name = COLUMN_RECYCLED_QUANTITY)
    private double recycledQuantity;

    @SerializedName(COLUMN_RECYCLED_PROCESS_WASTE)
    @ColumnInfo(name = COLUMN_RECYCLED_PROCESS_WASTE)
    private double recycleProcessingWaste;


    // The following two fields are used when a new Waste object is created after going through a
    // Power Plant.
    @SerializedName(COLUMN_POWER_ENERGY_PRODUCTION)
    @ColumnInfo(name = COLUMN_POWER_ENERGY_PRODUCTION)
    private double powerEnergyProduction;

    @SerializedName(COLUMN_POWER_HEATING_LEVELS)
    @ColumnInfo(name = COLUMN_POWER_HEATING_LEVELS)
    private double powerHeatingLevels;


    // The following three fields are used when a new Waste object is created after getting to a
    // Landfill.
    @SerializedName(COLUMN_LANDFILL_WATER_PARAMETERS)
    @ColumnInfo(name = COLUMN_LANDFILL_WATER_PARAMETERS)
    private String landfillWaterParameters;

    @SerializedName(COLUMN_LANDFILL_AIR_PARAMETERS)
    @ColumnInfo(name = COLUMN_LANDFILL_AIR_PARAMETERS)
    private String landfillAirParameters;

    @SerializedName(COLUMN_LANDFILL_GAS_PRODUCTION)
    @ColumnInfo(name = COLUMN_LANDFILL_GAS_PRODUCTION)
    private String landfillGasProduction;


    public Waste(String id, String type, double weight, double volume, String quality, String parameters) {
        setId(id);
        setType(type);
        setWeight(weight);
        setVolume(volume);
        setQuality(quality);
        setParameters(parameters);
        setLandfillGasProduction("");
        setLandfillAirParameters("");
        setTreatmentType("");
        setLandfillAirParameters("");
        setLandfillGasProduction("");
        setLandfillWaterParameters("");
    }


    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String printType(Context context) {
        switch (getType()) {
            case WASTE_TYPE_PAPER:
                return context.getString(R.string.waste_type_paper);
            case WASTE_TYPE_GLASS:
                return context.getString(R.string.waste_type_glass);
            case WASTE_TYPE_METAL:
                return context.getString(R.string.waste_type_metal);
            case WASTE_TYPE_PLASTIC:
                return context.getString(R.string.waste_type_plastic);
            default:
                return context.getString(R.string.waste_type_organic);
        }
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }



    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public double getRecycledQuantity() {
        return recycledQuantity;
    }

    public void setRecycledQuantity(double recycle_recycled_quantity) {
        this.recycledQuantity = recycle_recycled_quantity;
    }

    public double getRecycleProcessingWaste() {
        return recycleProcessingWaste;
    }

    public void setRecycleProcessingWaste(double recycle_processing_waste) {
        this.recycleProcessingWaste = recycle_processing_waste;
    }



    public double getPowerEnergyProduction() {
        return powerEnergyProduction;
    }

    public void setPowerEnergyProduction(double power_energy_production) {
        this.powerEnergyProduction = power_energy_production;
    }

    public double getPowerHeatingLevels() {
        return powerHeatingLevels;
    }

    public void setPowerHeatingLevels(double power_heating_levels) {
        this.powerHeatingLevels = power_heating_levels;
    }



    public String getLandfillWaterParameters() {
        return landfillWaterParameters;
    }

    public void setLandfillWaterParameters(String landfill_water_parameters) {
        this.landfillWaterParameters = landfill_water_parameters;
    }

    public String getLandfillAirParameters() {
        return landfillAirParameters;
    }

    public void setLandfillAirParameters(String landfill_air_parameters) {
        this.landfillAirParameters = landfill_air_parameters;
    }

    public String getLandfillGasProduction() {
        return landfillGasProduction;
    }

    public void setLandfillGasProduction(String landfill_gas_production) {
        this.landfillGasProduction = landfill_gas_production;
    }


    @Ignore
    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Waste)) return false;

        Waste entry = (Waste) obj;
        return entry.getId().equals(this.getId());
    }


    public static Waste fromParcel(Parcel origin) {
        String id = origin.readString();
        String type = origin.readString();
        double weight = origin.readDouble();
        double volume = origin.readDouble();
        String quality = origin.readString();
        String parameters = origin.readString();

        String treatment_type = origin.readString();
        double recycled_quantity = origin.readDouble();
        double recycle_processing_waste = origin.readDouble();

        double power_energy_production = origin.readDouble();
        double power_heating_levels = origin.readDouble();

        String landfill_water_parameters = origin.readString();
        String landfill_air_parameters = origin.readString();
        String landfill_gas_production = origin.readString();

        Waste waste = new Waste(id, type, weight, volume, quality, parameters);
        waste.setTreatmentType(treatment_type);
        waste.setRecycledQuantity(recycled_quantity);
        waste.setRecycleProcessingWaste(recycle_processing_waste);

        waste.setPowerEnergyProduction(power_energy_production);
        waste.setPowerHeatingLevels(power_heating_levels);

        waste.setLandfillWaterParameters(landfill_water_parameters);
        waste.setLandfillAirParameters(landfill_air_parameters);
        waste.setLandfillGasProduction(landfill_gas_production);

        return waste;
    }

    public static Waste fromContentValues(ContentValues origin) {
        String id = origin.getAsString(COLUMN_WASTE_ID);
        String type = origin.getAsString(COLUMN_WASTE_TYPE);
        double weight = origin.getAsDouble(COLUMN_WASTE_WEIGHT);
        double volume = origin.getAsDouble(COLUMN_WASTE_VOLUME);
        String quality = origin.getAsString(COLUMN_WASTE_QUALITY);
        String parameters = origin.getAsString(COLUMN_WASTE_PARAMETERS);

        String treatment_type = origin.getAsString(COLUMN_TREATMENT_TYPE);
        double recycled_quantity = origin.getAsDouble(COLUMN_RECYCLED_QUANTITY);
        double recycle_processing_waste = origin.getAsDouble(COLUMN_RECYCLED_PROCESS_WASTE);

        double power_energy_production = origin.getAsDouble(COLUMN_POWER_ENERGY_PRODUCTION);
        double power_heating_levels = origin.getAsDouble(COLUMN_POWER_HEATING_LEVELS);

        String landfill_water_parameters = origin.getAsString(COLUMN_LANDFILL_WATER_PARAMETERS);
        String landfill_air_parameters = origin.getAsString(COLUMN_LANDFILL_AIR_PARAMETERS);
        String landfill_gas_production = origin.getAsString(COLUMN_LANDFILL_GAS_PRODUCTION);

        Waste waste = new Waste(id, type, weight, volume, quality, parameters);
        waste.setTreatmentType(treatment_type);
        waste.setRecycledQuantity(recycled_quantity);
        waste.setRecycleProcessingWaste(recycle_processing_waste);

        waste.setPowerEnergyProduction(power_energy_production);
        waste.setPowerHeatingLevels(power_heating_levels);

        waste.setLandfillWaterParameters(landfill_water_parameters);
        waste.setLandfillAirParameters(landfill_air_parameters);
        waste.setLandfillGasProduction(landfill_gas_production);

        return waste;
    }

    public static ArrayList<Waste> fromJSONArray(ArrayList<JsonObject> array) {
        ArrayList<Waste> result = new ArrayList<>();

        if (array != null) for (JsonObject object : array) {
            Waste waste = null;
            if (object.has(COLUMN_WASTE_ID))
                waste = new Gson().fromJson(object, Waste.class);
            if (waste != null)
                result.add(waste);
        }

        return result;
    }

    public static Waste fromJson(JsonObject origin) {
        origin.addProperty(COLUMN_WASTE_WEIGHT,
                Double.valueOf(origin.get(COLUMN_WASTE_WEIGHT).getAsString()));
        origin.addProperty(COLUMN_WASTE_VOLUME,
                Double.valueOf(origin.get(COLUMN_WASTE_VOLUME).getAsString()));
        origin.addProperty(COLUMN_RECYCLED_QUANTITY,
                Double.valueOf(origin.get(COLUMN_RECYCLED_QUANTITY).getAsString()));
        origin.addProperty(COLUMN_RECYCLED_PROCESS_WASTE,
                Double.valueOf(origin.get(COLUMN_RECYCLED_PROCESS_WASTE).getAsString()));
        origin.addProperty(COLUMN_POWER_ENERGY_PRODUCTION,
                Double.valueOf(origin.get(COLUMN_POWER_ENERGY_PRODUCTION).getAsString()));
        origin.addProperty(COLUMN_POWER_HEATING_LEVELS,
                Double.valueOf(origin.get(COLUMN_POWER_HEATING_LEVELS).getAsString()));

        return new Gson().fromJson(origin, Waste.class);
    }

    public JsonObject toJson() {
        JsonObject obj = new Gson().fromJson(new Gson().toJson(this), JsonObject.class);

        obj.addProperty(COLUMN_WASTE_WEIGHT, String.valueOf(getWeight()));
        obj.addProperty(COLUMN_WASTE_VOLUME, String.valueOf(getVolume()));
        obj.addProperty(COLUMN_RECYCLED_QUANTITY, String.valueOf(getRecycledQuantity()));
        obj.addProperty(COLUMN_RECYCLED_PROCESS_WASTE, String.valueOf(getRecycleProcessingWaste()));
        obj.addProperty(COLUMN_POWER_ENERGY_PRODUCTION, String.valueOf(getPowerEnergyProduction()));
        obj.addProperty(COLUMN_POWER_HEATING_LEVELS, String.valueOf(getPowerHeatingLevels()));

        return obj;
    }

    public static ContentValues toContentValues(Waste waste) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_WASTE_ID, waste.getId());
        contentValues.put(COLUMN_WASTE_TYPE, waste.getType());
        contentValues.put(COLUMN_WASTE_WEIGHT, waste.getWeight());
        contentValues.put(COLUMN_WASTE_VOLUME, waste.getVolume());
        contentValues.put(COLUMN_WASTE_QUALITY, waste.getQuality());
        contentValues.put(COLUMN_WASTE_PARAMETERS, waste.getParameters());

        contentValues.put(COLUMN_TREATMENT_TYPE, waste.getTreatmentType());
        contentValues.put(COLUMN_RECYCLED_QUANTITY, waste.getRecycledQuantity());
        contentValues.put(COLUMN_RECYCLED_PROCESS_WASTE, waste.getRecycleProcessingWaste());

        contentValues.put(COLUMN_POWER_ENERGY_PRODUCTION, waste.getPowerEnergyProduction());
        contentValues.put(COLUMN_POWER_HEATING_LEVELS, waste.getPowerHeatingLevels());

        contentValues.put(COLUMN_LANDFILL_WATER_PARAMETERS, waste.getLandfillWaterParameters());
        contentValues.put(COLUMN_LANDFILL_AIR_PARAMETERS, waste.getLandfillAirParameters());
        contentValues.put(COLUMN_LANDFILL_GAS_PRODUCTION, waste.getLandfillGasProduction());

        return contentValues;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());
        dest.writeString(getType());
        dest.writeDouble(getWeight());
        dest.writeDouble(getVolume());
        dest.writeString(getQuality());
        dest.writeString(getParameters());

        dest.writeString(getTreatmentType());
        dest.writeDouble(getRecycledQuantity());
        dest.writeDouble(getRecycleProcessingWaste());

        dest.writeDouble(getPowerEnergyProduction());
        dest.writeDouble(getPowerHeatingLevels());

        dest.writeString(getLandfillWaterParameters());
        dest.writeString(getLandfillAirParameters());
        dest.writeString(getLandfillGasProduction());
    }

    public static final Creator<Waste> CREATOR = new Creator<Waste>() {

        public Waste createFromParcel(Parcel origin) {
            return Waste.fromParcel(origin);
        }

        public Waste[] newArray(int size) {
            return new Waste[size];
        }
    };

}
