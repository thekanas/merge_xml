package com.vpolosov.trainee.mergexml.handler.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;

/**
 * Data Transfer Object representing an outgoing error response.
 * This class encapsulates information about an error that occurred in the application.
 *
 * @author Ali Takushinov
 */
@Getter
@Setter
@EqualsAndHashCode
public class ErrorResponseDTO {

    /**
     * The URI where the error occurred.
     */
    private String uri;

    /**
     * The type of the error.
     */
    private String type;

    /**
     * A descriptive message detailing the error.
     */
    private String message;

    /**
     * The date and time when the error occurred.
     */
    private String timestamp;

    /**
     * Конструктор с двумя параметрами.
     *
     * @param type    тип ошибки.
     * @param message текст ошибки.
     */
    public ErrorResponseDTO(String type, String message) {
        this.uri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        this.timestamp = LocalDateTime.now().toString();
        this.type = type;
        this.message = message;
    }
}
