package com.ruta.backend.monolito.exceptions.responses;

import lombok.Data;
import lombok.Generated;
import lombok.NonNull;

@Data
@Generated
public class ResponseError {
    @NonNull
    String title;
    @NonNull
    String message;
}
