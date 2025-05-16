package ar.edu.unlam.tpi.nexwork_api.exceptions;

import static ar.edu.unlam.tpi.nexwork_api.utils.Constants.BAD_REQUEST;
import static ar.edu.unlam.tpi.nexwork_api.utils.Constants.STATUS_BAD_REQUEST;

public class ValidatorException extends GenericException {

    public ValidatorException(String detail) {
        super(STATUS_BAD_REQUEST, BAD_REQUEST, detail);
    }
}
