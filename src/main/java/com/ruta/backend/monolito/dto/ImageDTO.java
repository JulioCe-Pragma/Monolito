package com.ruta.backend.monolito.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Generated
public class ImageDTO {

    @NotEmpty(message = "el campo numDocumento no debe estár vacio")
    @Pattern(regexp = "^[0-9]+$", message = "El campo numDocumento debe ser numerico")
    @Size(min = 8, max = 10, message = "El campo numDocumento debe tener entre 8 y 10 digitos")
    private String numDocumento;
    private String imagen64;
}
