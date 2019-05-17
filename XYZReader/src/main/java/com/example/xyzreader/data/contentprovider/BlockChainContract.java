package com.example.xyzreader.data.contentprovider;

import android.net.Uri;

import static com.example.xyzreader.data.contentprovider.WasteProvider.BASE_CONTENT_URI;

public class BlockChainContract {

    public static final long INVALID_WASTE_ID = -1;

    public static final class BlockEntry {

        public static final String TABLE_NAME = "blockchain";

        /* */
        public static final String COLUMN_WASTE_ID = "waste_id";
        public static final String COLUMN_WASTE_TYPE = "waste_type";
        public static final String COLUMN_WASTE_WEIGHT = "waste_weight";
        public static final String COLUMN_WASTE_VOLUME = "waste_volume";
        public static final String COLUMN_WASTE_QUALITY = "waste_quality";
        public static final String COLUMN_WASTE_PARAMETERS = "waste_parameters";

        public static final String COLUMN_TREATMENT_TYPE = "treatment_type";

        public static final String COLUMN_RECYCLE_ID = "treatment_plant_id";
        public static final String COLUMN_RECYCLE_RECYCLED_QUANTITY = "recycled_quantity";
        public static final String COLUMN_RECYCLE_PROCESSING_WASTE = "recycled_process_waste";

        public static final String COLUMN_POWER_ID = "power_id";
        public static final String COLUMN_POWER_ENERGY_PRODUCTION = "power_energy_production";
        public static final String COLUMN_POWER_HEATING_LEVELS = "power_heating_levels";

        public static final String COLUMN_LANDFILL_ID = "landfill_id";
        public static final String COLUMN_LANDFILL_WATER_PARAMETERS = "landfill_water_parameters";
        public static final String COLUMN_LANDFILL_AIR_PARAMETERS = "landfill_air_parameters";
        public static final String COLUMN_LANDFILL_GAS_PRODUCTION = "landfill_gas_parameters";

        /* */
        public static final String COLUMN_PREV_WASTE_ID = "prev_waste_id";
        public static final String COLUMN_PREV_OWNER_ID = "prev_owner_id";
        public static final String COLUMN_NEXT_WASTE_ID = "next_waste_id";
        public static final String COLUMN_NEXT_OWNER_ID = "next_owner_id";
        public static final String COLUMN_COORDINATES = "coordinates";
        public static final String COLUMN_DATE = "date";

        /* */
        public static final String COLUMN_COLLECTOR_ID = "collector_id";

        /* */
        public static final String COLUMN_PRODUCER_ID = "producer_id";



        private static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME).build();

        public static Uri buildDirUri() {
            return CONTENT_URI;
        }

        public static Uri buildItemUri(long id) {
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_WASTE_ID, String.valueOf(id))
                    .build();
        }

    }

}
