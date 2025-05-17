package ar.edu.unlam.tpi.nexwork_api.exceptions;

import ar.edu.unlam.tpi.nexwork_api.dto.ErrorResponse;

public class WorkContractClientException extends GenericException {

    public WorkContractClientException(ErrorResponse error) {
        super(error.getCode(), error.getMessage(), error.getDetail());
    }
}
