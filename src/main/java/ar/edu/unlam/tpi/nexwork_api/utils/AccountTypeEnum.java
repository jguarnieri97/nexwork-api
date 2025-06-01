package ar.edu.unlam.tpi.nexwork_api.utils;

public enum AccountTypeEnum {
    SUPPLIER("supplier"),
    WORKER("worker"),
    APPLICANT("applicant");

    private final String value;

    AccountTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
