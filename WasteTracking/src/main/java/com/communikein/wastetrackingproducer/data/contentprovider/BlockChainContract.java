package com.communikein.wastetrackingproducer.data.contentprovider;

import android.net.Uri;

import static com.communikein.wastetrackingproducer.data.contentprovider.WasteProvider.BASE_CONTENT_URI;

public class BlockChainContract {

    public static final long INVALID_WASTE_ID = -1;

    /**
     * This class contains the names of the columns/fields used by both the API and as DB columns.
     * Each object saved in the BlockChain or in the DB has its fields declared here.
     */
    public static final class BlockEntry {

        // Name of the DB table containing all the data
        public static final String TABLE_NAME = "blockchain";

        // Waste
        public static final String COLUMN_WASTE_ID = "WASTE_ID";
        public static final String COLUMN_WASTE_TYPE = "WASTE_TYPE";
        public static final String COLUMN_WASTE_WEIGHT = "WASTE_WEIGHT";
        public static final String COLUMN_WASTE_VOLUME = "WASTE_VOLUME";
        public static final String COLUMN_WASTE_QUALITY = "WASTE_QUALITY";
        public static final String COLUMN_WASTE_PARAMETERS = "WASTE_PARAMETERS";

        public static final String COLUMN_TREATMENT_TYPE = "TREATMENT_TYPE";

        public static final String COLUMN_RECYCLE_ID = "TREATMENT_PLANT_ID";
        public static final String COLUMN_RECYCLED_QUANTITY = "RECYCLED_QUANTITY";
        public static final String COLUMN_RECYCLED_PROCESS_WASTE = "RECYCLED_PROCESS_WASTE";

        public static final String COLUMN_POWER_ID = "POWER_ID";
        public static final String COLUMN_POWER_ENERGY_PRODUCTION = "POWER_ENERGY_PRODUCTION";
        public static final String COLUMN_POWER_HEATING_LEVELS = "POWER_HEATING_LEVELS";

        public static final String COLUMN_LANDFILL_ID = "LANDFILL_ID";
        public static final String COLUMN_LANDFILL_WATER_PARAMETERS = "LANDFILL_WATER_PARAMETERS";
        public static final String COLUMN_LANDFILL_AIR_PARAMETERS = "LANDFILL_AIR_PARAMETERS";
        public static final String COLUMN_LANDFILL_GAS_PRODUCTION = "LANDFILL_GAS_PARAMETERS";

        // Change of Ownership
        public static final String COLUMN_PREV_WASTE_ID = "PREV_WASTE_ID";
        public static final String COLUMN_PREV_OWNER_ID = "PREV_OWNER_ID";
        public static final String COLUMN_NEXT_WASTE_ID = "NEXT_WASTE_ID";
        public static final String COLUMN_NEXT_OWNER_ID = "NEXT_OWNER_ID";
        public static final String COLUMN_COORDINATES = "COORDINATES";
        public static final String COLUMN_DATE = "DATE";

        // Collector
        public static final String COLUMN_COLLECTOR_ID = "COLLECTOR_ID";

        // Producer
        public static final String COLUMN_PRODUCER_ID = "PRODUCER_ID";
        public static final String COLUMN_PRODUCER_NAME = "NAME";
        public static final String COLUMN_PRODUCER_SCORE = "SCORE";
        public static final String COLUMN_PRODUCER_LOCATION = "LOCATION";
        public static final String COLUMN_PRODUCER_FAMILY_SIZE = "FAMILY_SIZE";



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
