package com.project.server.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorEnum {
    UNEXPECTED(5000, "예상하지 못한 오류가 발생했습니다."),
    GENERATE_FAIL(5001, "값을 생성하지 못했습니다."),
    INVALID_PARAMETER(4001, "잘못된 파라미터입니다."),
    INVALID_ID(4002, "잘못된 ID입니다."),
    NOT_FOUND_DATA(4040, "데이터가 존재하지 않습니다."),
    NOT_FOUND_RECORD(4041, "기록이 존재하지 않습니다.");

    private final int code;
    private final String Message;
}
