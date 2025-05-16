package ar.edu.unlam.tpi.nexwork_api.controller.handler;

import ar.edu.unlam.tpi.nexwork_api.dto.ErrorResponse;
import ar.edu.unlam.tpi.nexwork_api.exceptions.BudgetsClientException;
import ar.edu.unlam.tpi.nexwork_api.exceptions.ConverterException;
import ar.edu.unlam.tpi.nexwork_api.exceptions.ValidatorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NexworkExceptionHandler {

    @ExceptionHandler(BudgetsClientException.class)
    public ResponseEntity<ErrorResponse> handleBudgetsClientException(BudgetsClientException ex) {
        return ResponseEntity
                .status(ex.getCode())
                .body(ErrorResponse.builder()
                        .code(ex.getCode())
                        .message(ex.getMessage())
                        .detail(ex.getDetail())
                        .build());
    }

    @ExceptionHandler(ConverterException.class)
    public ResponseEntity<ErrorResponse> handleConverterException(ConverterException ex) {
        return ResponseEntity
                .status(ex.getCode())
                .body(ErrorResponse.builder()
                        .code(ex.getCode())
                        .message(ex.getMessage())
                        .detail(ex.getDetail())
                        .build());
    }

    @ExceptionHandler(ValidatorException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidatorException ex) {
        return ResponseEntity
                .status(ex.getCode())
                .body(ErrorResponse.builder()
                        .code(ex.getCode())
                        .message(ex.getMessage())
                        .detail(ex.getDetail())
                        .build());
    }

}
