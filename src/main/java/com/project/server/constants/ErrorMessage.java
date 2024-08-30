package com.project.server.constants;

public class ErrorMessage {

    public static final String UNEXPECTED = "예상하지 못한 오류가 발생했습니다.";
    public static final String INVALID_PARAMETER = "잘못된 파라미터입니다.";
    public static final String INVALID_ID = "잘못된 ID입니다.";
    public static final String NOT_FOUND_DATA = "데이터가 존재하지 않습니다.";
    public static final String NOT_FOUND_RECORD = "기록이 존재하지 않습니다.";

    private ErrorMessage() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
