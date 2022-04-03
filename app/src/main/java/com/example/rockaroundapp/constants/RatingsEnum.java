package com.example.rockaroundapp.constants;

import com.example.rockaroundapp.R;

public enum RatingsEnum {

    COMMUNICATION_S1(R.id.communication_s1),
    COMMUNICATION_S2(R.id.communication_s2),
    COMMUNICATION_S3(R.id.communication_s1),
    COMMUNICATION_S4(R.id.communication_s1),
    COMMUNICATION_S5(R.id.communication_s1),

    RELIABILITY_S1(R.id.reliability_s1),
    RELIABILITY_S2(R.id.reliability_s2),
    RELIABILITY_S3(R.id.reliability_s3),
    RELIABILITY_S4(R.id.reliability_s4),
    RELIABILITY_S5(R.id.reliability_s5),

    STAGEPRESENCE_S1(R.id.stage_presence_s1),
    STAGEPRESENCE_S2(R.id.stage_presence_s2),
    STAGEPRESENCE_S3(R.id.stage_presence_s3),
    STAGEPRESENCE_S4(R.id.stage_presence_s4),
    STAGEPRESENCE_S5(R.id.stage_presence_s5),

    VOCALS_S1(R.id.vocals_s1),
    VOCALS_S2(R.id.vocals_s2),
    VOCALS_S3(R.id.vocals_s3),
    VOCALS_S4(R.id.vocals_s4),
    VOCALS_S5(R.id.vocals_s5);

    private final int ratingId;

    RatingsEnum(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getRatingId() {
        return ratingId;
    }
}
