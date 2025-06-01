package ar.edu.unlam.tpi.nexwork_api.exceptions;

import ar.edu.unlam.tpi.nexwork_api.dto.response.ErrorResponse;
import static ar.edu.unlam.tpi.nexwork_api.utils.Constants.INTERNAL_ERROR;
import static ar.edu.unlam.tpi.nexwork_api.utils.Constants.STATUS_INTERNAL;

public class AccountsClientException extends GenericException{
    public AccountsClientException(ErrorResponse error) {
        super(error.getCode(), error.getMessage(), error.getDetail());
    }
    public AccountsClientException(String detail) {
        super(STATUS_INTERNAL, INTERNAL_ERROR, detail);
    }
}
