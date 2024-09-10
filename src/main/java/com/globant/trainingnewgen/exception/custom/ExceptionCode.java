package com.globant.trainingnewgen.exception.custom;

public enum ExceptionCode {

    /**
     * UE = User Error
     * SE = Server Error
     */

    USER_ALREADY_EXISTS("UE-1001", "User already exists exception"),

    INCOMPLETE_OR_INCORRECT_INFORMATION("UE-1002", "Incomplete or incorrect information exception"),

    CLIENT_NOT_FOUND("UE-1004", "Customer not found exception"),

    INVALID_DOCUMENT("UE-1005", "Invalid document exception"),

    NO_CHANGES("UE-1006", "No changes exception"),

    COMBO_ALREADY_EXISTS("UE-1007", "Combo already exists exception"),

    COMBO_NOT_FOUND("UE-1008", "Combo not found Exception"),

    FANTASY_NAME_ALREADY_USED("UE-1009", "Fantasy name already in use"),

    INVALID_COMBO_UUID("UE-1010", "Invalid combo UUID exception"),

    INVALID_DATE_TIME("UE-1011", "Invalid date time exception"),

    ILLEGAL_ARGUMENT_EXCEPTION("UE-1012", "Illegal value exception"),

    INVALID_REQUEST_BODY("UE-1013", "Invalid request body"),

    SERVER_ERROR("SE-2001", "Server error exception");


    private final String code;
    private final String description;

    ExceptionCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }


}
