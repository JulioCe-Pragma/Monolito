package com.ruta.backend.monolito.controllers;

import com.ruta.backend.monolito.dto.ImageDTO;
import com.ruta.backend.monolito.dto.PersonDTO;
import com.ruta.backend.monolito.exceptions.responses.ResponseError;
import com.ruta.backend.monolito.services.Integrator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@CrossOrigin("*")
@RestController
@RequestMapping("/imagen/")
@Validated
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ImageController {

    private Integrator integrator;

    @Operation(summary = "Endpoint que permite crear una imagen en base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imagen creada existosamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "No existe ninguna persona con el id asociado a la imagen",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class)) })
    })
    @PostMapping(value = "create")
    public ResponseEntity<Object> createImagen(@Valid @RequestBody ImageDTO imagen) {
        this.integrator.saveImage(imagen);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Endpoint que permite consultar/obtener una imagen a traves del id de la persona asociada a la imagen.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen obtenida de forma exitosa",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ImageDTO.class))}),
            @ApiResponse(responseCode = "404", description = "No existe ninguna persona con el id asociado a la imagen",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class)) })
    })
    @GetMapping(value = "getBy/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getImagenById(@PathVariable("id") @Pattern(regexp = "^[0-9]+$", message = "El parametro id debe ser numerico")
                                                    @Size(min = 8, max = 10, message = "El parametro id debe tener entre 8 y 10 digitos") String id){
        return ResponseEntity.ok(this.integrator.findImageById(id));
    }

    @Operation(summary = "Endpoint que permite actualizar una imagen ya creada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen actualizada existosamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Actualizacion no exitosa. No existe ninguna persona con el id asociado a la imagen",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class)) })
    })
    @PutMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateImagen(@Valid @RequestBody ImageDTO request){
        this.integrator.updateImage(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Endpoint que permite eliminar una imagen ya creada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Imagen eliminada existosamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Eliminacion no exitosa, No existe ninguna persona con el id asociado a la imagen",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class)) })
    })
    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<Object> deleteImagen(@PathVariable("id") @Pattern(regexp = "^[0-9]+$", message = "El parametro id debe ser numerico")
                                                   @Size(min = 8, max = 10, message = "El parametro id debe tener entre 8 y 10 digitos") String id){
        this.integrator.deleteImageById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
