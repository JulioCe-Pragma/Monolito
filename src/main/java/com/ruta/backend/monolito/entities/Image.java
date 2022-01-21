package com.ruta.backend.monolito.entities;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@Generated
public class Image {

    @Id
    private String numDocumento;
    private String imagen64;
}
