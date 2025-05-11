package ar.edu.unlam.tpi.nexwork_api.exceptions;

import static ar.edu.unlam.tpi.nexwork_api.utils.Constants.INTERNAL_ERROR;
import static ar.edu.unlam.tpi.nexwork_api.utils.Constants.STATUS_INTERNAL;

public class ConverterException extends GenericException {


    public ConverterException(String detail) {
        super(STATUS_INTERNAL, INTERNAL_ERROR, detail);
    }
}
