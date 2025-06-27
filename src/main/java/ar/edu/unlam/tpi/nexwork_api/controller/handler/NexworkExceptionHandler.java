package ar.edu.unlam.tpi.nexwork_api.controller.handler;

import ar.edu.unlam.tpi.nexwork_api.dto.response.ErrorResponse;
import ar.edu.unlam.tpi.nexwork_api.exceptions.BudgetsClientException;
import ar.edu.unlam.tpi.nexwork_api.exceptions.ConverterException;
import ar.edu.unlam.tpi.nexwork_api.exceptions.GenericException;
import ar.edu.unlam.tpi.nexwork_api.exceptions.ValidatorException;

import ar.edu.unlam.tpi.nexwork_api.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.ConnectException;

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

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> handleGenericException(GenericException ex) {
        int code = (ex.getCode() >= 100 && ex.getCode() <= 599) ? ex.getCode() : 500;
    
        return ResponseEntity
                .status(code)
                .body(ErrorResponse.builder()
                        .code(code)
                        .message(ex.getMessage() != null ? ex.getMessage() : "INTERNAL_ERROR")
                        .detail(ex.getDetail() != null ? ex.getDetail() : "Ocurrió un error inesperado")
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var detail = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        return ResponseEntity
                .status(Constants.STATUS_BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .code(Constants.STATUS_BAD_REQUEST)
                        .message(Constants.BAD_REQUEST)
                        .detail(detail)
                        .build());
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ErrorResponse> handleConnectException(ConnectException ex) {
        return ResponseEntity
                .status(Constants.STATUS_INTERNAL)
                .body(ErrorResponse.builder()
                        .code(Constants.STATUS_INTERNAL)
                        .message(Constants.INTERNAL_ERROR)
                        .detail("Error de conexión con el servicio externo")
                        .build());
    }

}
