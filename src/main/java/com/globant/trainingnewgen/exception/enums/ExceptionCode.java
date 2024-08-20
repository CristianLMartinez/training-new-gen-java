package com.globant.trainingnewgen.exception.enums;

import org.springframework.http.HttpStatus;

public enum ExceptionCode {

    /**
     * UE = User Error
     * SE = Server Error
     */
    USER_ALREADY_EXISTS("UE-1001", HttpStatus.Series.CLIENT_ERROR, "UserAlreadyExistsException..."),
    INCOMPLETE_OR_INCORRECT_INFORMATION("UE-1002", HttpStatus.Series.CLIENT_ERROR, "IncompleteOrIncorrectInformationException..."),
    CUSTOMER_NOT_FOUND("UE-1004", HttpStatus.Series.CLIENT_ERROR, "CustomerNotFoundException..."),
    INVALID_DOCUMENT("UE-1005", HttpStatus.Series.CLIENT_ERROR, "InvalidDocumentException..."),
    NO_CHANGES("UE-1006", HttpStatus.Series.CLIENT_ERROR, "NoChangesException..."),
    COMBO_ALREADY_EXISTS("UE-1007", HttpStatus.Series.CLIENT_ERROR, "ComboAlreadyExistsException..."),
    COMBO_NOT_FOUND("UE-1008", HttpStatus.Series.CLIENT_ERROR, "ComboNotFoundException..."),
    INVALID_COMBO_UUID("UE-1009", HttpStatus.Series.CLIENT_ERROR, "InvalidComboUUIDException..."),
    SERVER_ERROR("SE-2001", HttpStatus.Series.SERVER_ERROR, "ServerErrorException..."),;


    private final String code;
    private final HttpStatus.Series status;
    private final String description;

    ExceptionCode(String code, HttpStatus.Series status, String description ) {
        this.code = code;
        this.status = status;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription(){
        return description;
    }

    public HttpStatus.Series getStatus(){
        return status;
    }

}
