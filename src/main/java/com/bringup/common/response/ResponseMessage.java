package com.bringup.common.response;

public interface ResponseMessage {
    //public static final

    // HTTP Status 200
    String SUCCES = "Succes.";

    // HTTP Status 400
    String VALIDATION_FAILED = "Validation Failed.";
    String NOT_EXISTED_USER = "This user does not exist.";
    String NOT_EXISTED_BOARD = "This board does not exist";
    String NOT_EXISTED_STUDY = "This study does not exist";
    String NOT_EXISTED_REPORT = "This report does not exist";
    String NOT_EXISTEDT_RECRUITMENTFREELANCER = "This recruitmentFreelancer does not exist";
    String EXISTED_EMAIL="Already existed Email";
    String SPARE_REPORT_ALREADY_EXIST = "Spare report already exist";
    String REPORT_ALREADY_FULL = "Report already exist";
    String REPORT_ALREADY_WROTE  = "Report already wrote";
    String NOT_EXISTED_BLOG="NOT_EXISTED_BLOG";
    String NOT_EXISTED_LETTER="NOT_EXISTED_LETTER";
    String NOT_EXISTED_CAREER="NOT_EXISTED_CAREER";
    String EXISTED_CAREER = "Existed career";
    String EXISTED_AWARD = "Existed award";
    // HTTP Status 401
    String SIGN_IN_FAIL = "Login Inforam.";
    String AUTHORIZATION_FAILED = "Authorization Failed.";
    String EXISTED_URL = "Existed URL";
    // HTTP Status 403
    String NO_PERMISSION = "Do not have Permission.";

    // HTTP Status 500
    String DATABASE_ERROR= "Database error.";

    String EMAIL_AVAILABLE = "사용 가능한 이메일입니다.";
    String EMAIL_TAKEN = "이미 사용 중인 이메일입니다.";
}
