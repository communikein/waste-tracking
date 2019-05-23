package com.example.xyzreader.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.example.xyzreader.R;
import com.example.xyzreader.data.contentprovider.BlockChainContract;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import static com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_WASTE_ID;
import static com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_LANDFILL_AIR_PARAMETERS;
import static com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_LANDFILL_GAS_PRODUCTION;
import static com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_LANDFILL_WATER_PARAMETERS;
import static com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_WASTE_PARAMETERS;
import static com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_POWER_ENERGY_PRODUCTION;
import static com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_POWER_HEATING_LEVELS;
import static com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_WASTE_QUALITY;
import static com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_RECYCLE_PROCESSING_WASTE;
import static com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_RECYCLE_RECYCLED_QUANTITY;
import static com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_TREATMENT_TYPE;
import static com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_WASTE_TYPE;
import static com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_WASTE_VOLUME;
import static com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry.COLUMN_WASTE_WEIGHT;


@Entity(tableName = BlockChainContract.BlockEntry.TABLE_NAME)
public class Waste extends Block implements Parcelable {

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

    @SerializedName(COLUMN_RECYCLE_RECYCLED_QUANTITY)
    @ColumnInfo(name = COLUMN_RECYCLE_RECYCLED_QUANTITY)
    private double recycledQuantity;

    @SerializedName(COLUMN_RECYCLE_PROCESSING_WASTE)
    @ColumnInfo(name = COLUMN_RECYCLE_PROCESSING_WASTE)
    private double recycleProcessingWaste;


    @SerializedName(COLUMN_POWER_ENERGY_PRODUCTION)
    @ColumnInfo(name = COLUMN_POWER_ENERGY_PRODUCTION)
    private double powerEnergyProduction;

    @SerializedName(COLUMN_POWER_HEATING_LEVELS)
    @ColumnInfo(name = COLUMN_POWER_HEATING_LEVELS)
    private double powerHeatingLevels;


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
        super(null);

        setId(id);
        setType(type);
        setWeight(weight);
        setVolume(volume);
        setQuality(quality);
        setParameters(parameters);

        super.setJson(this.getJson());
    }

    public Waste(JsonObject origin) {
        super(origin);

        String id = origin.get(COLUMN_WASTE_ID).getAsString();
        String type = origin.get(COLUMN_WASTE_TYPE).getAsString();
        double weight = origin.get(COLUMN_WASTE_WEIGHT).getAsDouble();
        double volume = origin.get(COLUMN_WASTE_VOLUME).getAsDouble();
        String quality = origin.get(COLUMN_WASTE_QUALITY).getAsString();
        String parameters = origin.get(COLUMN_WASTE_PARAMETERS).getAsString();

        String treatment_type = origin.get(COLUMN_TREATMENT_TYPE).getAsString();
        double recycled_quantity = origin.get(COLUMN_RECYCLE_RECYCLED_QUANTITY).getAsDouble();
        double recycle_processing_waste = origin.get(COLUMN_RECYCLE_PROCESSING_WASTE).getAsDouble();

        double power_energy_production = origin.get(COLUMN_POWER_ENERGY_PRODUCTION).getAsDouble();
        double power_heating_levels = origin.get(COLUMN_POWER_HEATING_LEVELS).getAsDouble();

        String landfill_water_parameters = origin.get(COLUMN_LANDFILL_WATER_PARAMETERS).getAsString();
        String landfill_air_parameters = origin.get(COLUMN_LANDFILL_AIR_PARAMETERS).getAsString();
        String landfill_gas_production = origin.get(COLUMN_LANDFILL_GAS_PRODUCTION).getAsString();

        setId(id);
        setType(type);
        setWeight(weight);
        setVolume(volume);
        setQuality(quality);
        setParameters(parameters);

        setTreatmentType(treatment_type);
        setRecycledQuantity(recycled_quantity);
        setRecycleProcessingWaste(recycle_processing_waste);

        setPowerEnergyProduction(power_energy_production);
        setPowerHeatingLevels(power_heating_levels);

        setLandfillWaterParameters(landfill_water_parameters);
        setLandfillAirParameters(landfill_air_parameters);
        setLandfillGasProduction(landfill_gas_production);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        if (! (obj instanceof  Waste)) return false;

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
        double recycled_quantity = origin.getAsDouble(COLUMN_RECYCLE_RECYCLED_QUANTITY);
        double recycle_processing_waste = origin.getAsDouble(COLUMN_RECYCLE_PROCESSING_WASTE);

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

    public JsonObject toJSON() {
        return new Gson().fromJson(new Gson().toJson(this), JsonObject.class);
    }


    public static ContentValues writeToContentValues(Waste waste) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_WASTE_ID, waste.getId());
        contentValues.put(COLUMN_WASTE_TYPE, waste.getType());
        contentValues.put(COLUMN_WASTE_WEIGHT, waste.getWeight());
        contentValues.put(COLUMN_WASTE_VOLUME, waste.getVolume());
        contentValues.put(COLUMN_WASTE_QUALITY, waste.getQuality());
        contentValues.put(COLUMN_WASTE_PARAMETERS, waste.getParameters());

        contentValues.put(COLUMN_TREATMENT_TYPE, waste.getTreatmentType());
        contentValues.put(COLUMN_RECYCLE_RECYCLED_QUANTITY, waste.getRecycledQuantity());
        contentValues.put(COLUMN_RECYCLE_PROCESSING_WASTE, waste.getRecycleProcessingWaste());

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

    public static final Parcelable.Creator<Waste> CREATOR = new Parcelable.Creator<Waste>() {

        public Waste createFromParcel(Parcel origin) {
            return Waste.fromParcel(origin);
        }

        public Waste[] newArray(int size) {
            return new Waste[size];
        }
    };

}
