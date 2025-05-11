package ar.edu.unlam.tpi.nexwork_api.exceptions;

import ar.edu.unlam.tpi.nexwork_api.dto.ErrorResponse;

public class BudgetsClientException extends GenericException {

    public BudgetsClientException(ErrorResponse error) {
        super(error.getCode(), error.getMessage(), error.getDetail());
    }
}
