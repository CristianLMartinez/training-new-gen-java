package com.globant.trainingnewgen.exception.enums;

public enum ExceptionCode {

    /**
     * UE = User Error
     * SE = Server Error
     */
    USER_ALREADY_EXISTS("UE-1001"),
    INCOMPLETE_OR_INCORRECT_INFORMATION("UE-1002"),
    CUSTOMER_NOT_FOUND("UE-1004"),
    INVALID_DOCUMENT("UE-1005"),
    NO_CHANGES("UE-1006"),
    COMBO_ALREADY_EXISTS("UE-1007"),
    COMBO_NOT_FOUND("UE-1008"),
    INVALID_COMBO_UUID("UE-1009"),
    SERVER_ERROR("SE-2001");


    private final String code;

    ExceptionCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
