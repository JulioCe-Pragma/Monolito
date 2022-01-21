package com.ruta.backend.monolito.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@Generated
@Table(name = "persons")
public class Person {

    private String nombres;
    private String apellidos;
    @Enumerated(value = EnumType.STRING)
    private DocumentType tipoDocumento;
    @Id
    @Size(max = 10)
    private String numDocumento;
    private int edad;
    private String ciudad;
    private boolean imagen;
}
