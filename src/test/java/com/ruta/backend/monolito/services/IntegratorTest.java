package com.ruta.backend.monolito.services;

import com.ruta.backend.monolito.dto.ImageDTO;
import com.ruta.backend.monolito.dto.PersonDTO;
import com.ruta.backend.monolito.entities.DocumentType;
import com.ruta.backend.monolito.entities.Image;
import com.ruta.backend.monolito.entities.Person;
import com.ruta.backend.monolito.exceptions.PersonNotFound;
import com.ruta.backend.monolito.services.interfaces.ImageService;
import com.ruta.backend.monolito.services.interfaces.PersonService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IntegratorTest {

    @InjectMocks
    Integrator integrator;

    @Mock
    ImageService imageService;

    @Mock
    PersonService personService;

    Image image;
    ImageDTO imageDTO;
    Person person;
    PersonDTO personDTO;
    DocumentType documentType = DocumentType.CC;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        image = new Image();
        image.setNumDocumento("1012443736");
        image.setImagen64("image64");

        imageDTO = new ImageDTO();
        imageDTO.setNumDocumento("1012443736");
        imageDTO.setImagen64("image64");

        personDTO = new PersonDTO();
        personDTO.setNombres("pedro carlos");
        personDTO.setApellidos("gomez torres");
        personDTO.setNumDocumento("1012443736");
        personDTO.setTipoDocumento(documentType);
        personDTO.setEdad(24);
        personDTO.setCiudad("Bogotá");
        personDTO.setImagen(false);

        person = new Person();
        person.setNombres("pedro carlos");
        person.setApellidos("gomez torres");
        person.setNumDocumento("1012443736");
        person.setTipoDocumento(documentType);
        person.setEdad(24);
        person.setCiudad("Bogotá");
        person.setImagen(false);
    }

    @Test
    void saveImageError() {
        when(personService.findById("1012443736")).thenReturn(Optional.empty());
        assertThrows(PersonNotFound.class,() -> { integrator.saveImage(imageDTO); });
    }

    @Test
    void saveImage() {
        Optional<PersonDTO> optionalPersonDTO = Optional.of(personDTO);
        when(personService.findById("1012443736")).thenReturn(optionalPersonDTO);
        integrator.saveImage(imageDTO);
        verify(imageService, times(1)).save(imageDTO);
        verify(personService, times(1)).update(optionalPersonDTO.get());
    }

    @Test
    void findImageById() {
        when(personService.existsById("1012443736")).thenReturn(true);
        when(imageService.findById("1012443736")).thenReturn(Optional.of(imageDTO));
        assertEquals(Optional.of(imageDTO),integrator.findImageById("1012443736"));
    }

    @Test
    void findImageByIdError() {
        when(personService.existsById("1012443736")).thenReturn(false);
        assertThrows(PersonNotFound.class,() -> { integrator.findImageById("1012443736"); });
    }

    @Test
    void updateImageError() {
        when(personService.existsById("1012443736")).thenReturn(false);
        assertThrows(PersonNotFound.class, () -> { integrator.updateImage(imageDTO); });
    }

    @Test
    void updateImage() {
        when(personService.existsById("1012443736")).thenReturn(true);
        integrator.updateImage(imageDTO);
        verify(imageService, times(1)).save(imageDTO);
    }

    @Test
    void deleteImageByIdError() {
        when(personService.findById("1012443736")).thenReturn(Optional.empty());
        assertThrows(PersonNotFound.class,() -> { integrator.deleteImageById("1012443736"); });
    }

    @Test
    void deleteImageById() {
        Optional<PersonDTO> optionalPersonDTO = Optional.of(personDTO);
        when(personService.findById("1012443736")).thenReturn(optionalPersonDTO);
        integrator.deleteImageById("1012443736");
        verify(imageService, times(1)).deleteById("1012443736");
        verify(personService, times(1)).update(optionalPersonDTO.get());
    }

    @Test
    void deletePersonById() {
        Optional<PersonDTO> optionalPersonDTO = Optional.of(personDTO);
        when(personService.findById("1012443736")).thenReturn(optionalPersonDTO);
        integrator.deletePersonById("1012443736");
        verify(personService, times(1)).deleteById("1012443736");
    }
}