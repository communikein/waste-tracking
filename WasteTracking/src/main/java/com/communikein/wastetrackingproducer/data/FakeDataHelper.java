package com.communikein.wastetrackingproducer.data;

import com.communikein.wastetrackingproducer.R;

public class FakeDataHelper {

    public static int pickFeelingResource(float score) {
        if (score <= 4)
            return R.drawable.ic_face_sad;
        else if (score <= 6)
            return R.drawable.ic_face_neutral;
        else
            return R.drawable.ic_face_happy;
    }

}
