package ar.edu.unlam.tpi.nexwork_api.exceptions;

import ar.edu.unlam.tpi.nexwork_api.dto.ErrorResponse;

public class AccountsClientException extends GenericException{
    public AccountsClientException(ErrorResponse error) {
        super(error.getCode(), error.getMessage(), error.getDetail());
    }
}
