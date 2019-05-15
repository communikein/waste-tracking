package com.example.xyzreader.data.contentprovider;

import android.net.Uri;

import static com.example.xyzreader.data.contentprovider.WasteProvider.BASE_CONTENT_URI;

public class WasteContract {

    public static final long INVALID_WASTE_ID = -1;

    public static final class WasteEntry {

        public static final String TABLE_NAME = "waste";
        public static final String COLUMN_ID = "waste_id";
        public static final String COLUMN_TYPE = "waste_type";
        public static final String COLUMN_WEIGHT = "waste_weight";
        public static final String COLUMN_VOLUME = "waste_volume";
        public static final String COLUMN_QUALITY = "waste_quality";
        public static final String COLUMN_PARAMETERS = "waste_parameters";

        public static final String COLUMN_TREATMENT_TYPE = "treatment_type";
        public static final String COLUMN_RECYCLE_RECYCLED_QUANTITY = "recycle_recycled_quantity";
        public static final String COLUMN_RECYCLE_PROCESSING_WASTE = "recycle_processing_waste";

        public static final String COLUMN_POWER_ENERGY_PRODUCTION = "power_energy_production";
        public static final String COLUMN_POWER_HEATING_LEVELS = "power_heating_levels";

        public static final String COLUMN_LANDFILL_WATER_PARAMETERS = "landfill_water_parameters";
        public static final String COLUMN_LANDFILL_AIR_PARAMETERS = "landfill_air_parameters";
        public static final String COLUMN_LANDFILL_GAS_PRODUCTION = "landfill_gas_production";

        private static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME).build();

        public static Uri buildDirUri() {
            return CONTENT_URI;
        }

        public static Uri buildItemUri(long id) {
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_ID, String.valueOf(id))
                    .build();
        }

    }

}
