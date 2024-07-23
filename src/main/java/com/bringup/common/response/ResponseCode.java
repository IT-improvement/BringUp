package com.bringup.common.response;

public interface ResponseCode {
    //public static final

    // HTTP Status 200
    String SUCCESS = "SU";

    // HTTP Status 400
    String VALIDATION_FAILED = "VE";
    String NOT_EXISTED_USER = "NU";
    String NOT_EXISTED_BOARD = "NB";
    String NOT_EXISTED_STUDY = "NS";
    String NOT_EXISTED_REPORT = "NR";

    String SPARE_REPORT_ALREADY_EXIST = "SR";
    String REPORT_ALREADY_FULL = "RF";
    String REPORT_ALREADY_WROTE = "RW";

    // HTTP Status 401
    String SIGN_IN_FAIL = "SF";
    String AUTHORIZATION_FAILED = "AF";

    // HTTP Status 403
    String NO_PERMISSION = "NP";

    // HTTP Status 500
    String DATABASE_ERROR= "DBE";

}
