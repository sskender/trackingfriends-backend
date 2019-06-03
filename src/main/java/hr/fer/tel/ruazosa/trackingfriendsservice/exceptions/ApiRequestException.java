package hr.fer.tel.ruazosa.trackingfriendsservice.exceptions;

import org.springframework.http.HttpStatus;

public class ApiRequestException extends RuntimeException {

    private final HttpStatus status;

    public ApiRequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    ApiRequestException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    HttpStatus getStatus() {
        return status;
    }

}
