package com.project.server.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorEnum {
    UNEXPECTED(0, "예상하지 못한 오류가 발생했습니다."),
    GENERATE_FAIL(1, "값을 생성하지 못했습니다."),
    INVALID_PARAMETER(1, "잘못된 파라미터입니다."),
    INVALID_ID(2, "잘못된 ID입니다."),
    NOT_FOUND_DATA(0, "데이터가 존재하지 않습니다."),
    NOT_FOUND_RECORD(1, "기록이 존재하지 않습니다.");

    private final int code;
    private final String Message;
}
