package icu.agony.clm.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public abstract class BasicClmException extends RuntimeException {

    private static final ObjectMapper OM = new ObjectMapper();

    private final ObjectNode json = OM.createObjectNode();

    private static final String MESSAGE_KEY = "message";

    private static final String DATA_KEY = "data";

    public BasicClmException(String message) {
        super(message);
    }

    public BasicClmException(String message, Throwable cause) {
        super(message, cause);
    }

    protected abstract HttpStatus httpStatus();

    protected MediaType contentType() {
        return MediaType.APPLICATION_JSON;
    }

    public void message(@NonNull String message) {
        json.put(MESSAGE_KEY, message);
    }

    public void data(@NonNull Map<String, Object> data) {
        json.putPOJO(DATA_KEY, data);
    }

    public ResponseEntity<String> responseEntity() {
        return ResponseEntity
                .status(httpStatus())
                .contentType(contentType())
                .body(json.toString());
    }


}
