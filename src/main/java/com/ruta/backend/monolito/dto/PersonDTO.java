package com.ruta.backend.monolito.dto;

import com.ruta.backend.monolito.entities.DocumentType;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Data
@Generated
public class PersonDTO {

    @NotBlank (message = "El campo nombres no debe estár vacio")
    private String nombres;
    @NotBlank (message = "El campo apellidos no debe estár vacio")
    private String apellidos;
    @NotNull (message = "El campo tipoDocumento no debe estár vacio")
    private DocumentType tipoDocumento;
    @NotEmpty (message = "el campo numDocumento no debe estár vacio")
    @Pattern (regexp = "^[0-9]+$", message = "El campo numDocumento debe ser numerico")
    @Size(min = 8, max = 10, message = "El campo numDocumento debe tener entre 8 y 10 digitos")
    private String numDocumento;
    @Range (min = 18, max = 123, message = "El campo edad no debe estár vacio y debe ser mayor o igual a 18")
    private int edad;
    @NotBlank (message = "El campo ciudad no debe estár vacio")
    private String ciudad;
    @AssertFalse
    private boolean imagen;
}
