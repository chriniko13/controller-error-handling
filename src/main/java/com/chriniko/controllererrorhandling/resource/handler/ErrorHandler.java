package com.chriniko.controllererrorhandling.resource.handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import io.vavr.control.Try;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

import static com.nurkiewicz.typeof.TypeOf.whenTypeOf;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Throwable cause = ex.getCause();
        return whenTypeOf(cause)
                .is(MismatchedInputException.class).thenReturn(mie -> {

                    final String payloadErrorPath = mie.getPath()
                            .stream()
                            .map(JsonMappingException.Reference::getFieldName)
                            .collect(Collectors.joining(" -> ", "[", "]"));
                    final String requiredTargetType = mie.getTargetType().getSimpleName();
                    final String detailedErrorMessage = getSafeMessage(mie);

                    PayloadErrorDetails payloadErrorDetails = new PayloadErrorDetails(payloadErrorPath,
                            requiredTargetType,
                            detailedErrorMessage);
                    return new ResponseEntity<Object>(payloadErrorDetails, HttpStatus.BAD_REQUEST);

                })
                .orElse(error -> super.handleHttpMessageNotReadable(ex, headers, status, request));
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {

        String errorMessage = ex.getMessage();

        return new ResponseEntity<>(new HeaderErrorDetails(errorMessage), HttpStatus.BAD_REQUEST);
    }

    private String getSafeMessage(MismatchedInputException mie) {
        return Try.of(() -> mie.getMessage().split("\n")[0])
                .getOrElseGet(t -> mie.getMessage());
    }

}
