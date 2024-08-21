package com.globant.trainingnewgen.exception;

public enum ExceptionCode {

    /**
     * UE = User Error
     * SE = Server Error
     */
    USER_ALREADY_EXISTS("UE-1001", "UserAlreadyExistsException..."),

    INCOMPLETE_OR_INCORRECT_INFORMATION("UE-1002", "IncompleteOrIncorrectInformationException..."),
    CUSTOMER_NOT_FOUND("UE-1004", "CustomerNotFoundException..."),
    INVALID_DOCUMENT("UE-1005", "InvalidDocumentException..."),
    NO_CHANGES("UE-1006", "NoChangesException..."),
    COMBO_ALREADY_EXISTS("UE-1007", "ComboAlreadyExistsException..."),
    COMBO_NOT_FOUND("UE-1008", "ComboNotFoundException..."),
    INVALID_COMBO_UUID("UE-1009", "InvalidComboUUIDException..."),
    SERVER_ERROR("SE-2001", "ServerErrorException..."),
    ;


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
