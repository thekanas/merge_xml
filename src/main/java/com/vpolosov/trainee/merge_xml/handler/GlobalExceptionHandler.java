package com.vpolosov.trainee.merge_xml.handler;

import com.vpolosov.trainee.merge_xml.handler.dto.ErrorResponseDTO;
import com.vpolosov.trainee.merge_xml.handler.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
        IncorrectXmlFileException.class,
        MoreFiveHundredKbException.class,
        NotExactlyOneXsdFileException.class,
        NotExactlyTenFilesException.class,
        DuplicationProcessingException.class,
        IncorrectMinAmountException.class,
        NonExistentTagException.class,
        IncorrectDateException.class,
        DifferentPayerException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleIllegalArgumentException(Exception e) {
        return new ErrorResponseDTO("Bad Request", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO handleInternalServerError(Exception e) {
        return new ErrorResponseDTO("Server Error", e.getMessage());
    }
}
