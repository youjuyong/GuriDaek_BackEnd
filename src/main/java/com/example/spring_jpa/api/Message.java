package com.example.spring_jpa.api;

public class Message {
    
    public static final String SUCCESS = "success";
    public static final String NO_CONTENT_AVAILABLE = "there is no content available";
    public static final String UNIQUE_CONSTRAINT_VIOLATION = "unique constraint violation";
    public static final String FK_CONSTRAINT_VIOLATION = "foreign key constraint violation";

    public static final String TRANSACTION_FAILURE = "the transaction has been failed";
    public static final String EVENT_PRIZE_SUMIT_SUCCESS = "이벤트 경품 신청 완료되었습니다. 이벤트후 당첨자 발표가 진행됩니다.";
    public static final String EVENT_PRIZE_USER_YN = "이미 경품 신청을 하셨습니다.";
	public static final String EVENT_PRIZE_USER_LIMIT= "경품 목록이 모두 소진되었습니다. 다음 이벤트를 노려 주세요 ^^.";
    public static final String UNEXPECTED_ERROR = "unexpected error";
    public static final String SERVER_NOT_CONNECTED = "server is not connected";
    public static final String TOKEN_EXPIRED = "token has been expired";

    public static final String INVALID_USER = "id and password is wrong";

    public static final String FILE_UPLOAD_FAILURE = "file uploading is failed";

    public static final String VALIDATION_FAILED = "input validation is failed";

    public static final String EXCEL_UPLOAD_FAILURE = "excel file upload is failed";
    
}
