package com.ruta.backend.monolito.services.implementations;

import com.ruta.backend.monolito.dto.PersonDTO;
import com.ruta.backend.monolito.entities.DocumentType;
import com.ruta.backend.monolito.entities.Person;
import com.ruta.backend.monolito.exceptions.PersonAlreadyExist;
import com.ruta.backend.monolito.exceptions.PersonNotFound;
import com.ruta.backend.monolito.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonImplementTest {

    @InjectMocks
    PersonImplement personImplement;

    @Mock
    PersonRepository personRepository;

    PersonDTO personDTO;
    Person person;
    DocumentType documentType = DocumentType.CC;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
    void saveError() {
        when(personRepository.existsById("1012443736")).thenReturn(true);
        assertThrows(PersonAlreadyExist.class, () -> { personImplement.save(personDTO); });
    }

    @Test
    void save() {
        when(personRepository.existsById("1012443736")).thenReturn(false);
        personImplement.save(personDTO);
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void findByDocumentTypeAndNumDocument() {
        Optional<Person> personOptional = Optional.of(person);
        when(personRepository.findByTipoDocumentoAndNumDocumento(documentType,"1012443736")).thenReturn(personOptional);
        assertEquals(Optional.of(personDTO), personImplement.findByDocumentTypeAndNumDocument("CC","1012443736"));
    }

    @Test
    void findByDocumentTypeAndNumDocumentError() {
        when(personRepository.findByTipoDocumentoAndNumDocumento(documentType,"1012443736")).thenReturn(Optional.empty());
        assertThrows(PersonNotFound.class, () -> { personImplement.findByDocumentTypeAndNumDocument("CC", "1012443736"); });
    }

    @Test
    void existsById() {
        when(personRepository.existsById("1012443736")).thenReturn(true);
        assertEquals(true, personImplement.existsById("1012443736"));
    }

    @Test
    void findById() {
        Optional<Person> personOptional = Optional.of(person);
        when(personRepository.findById("1012443736")).thenReturn(personOptional);
        assertEquals(Optional.of(personDTO),personImplement.findById("1012443736"));
    }

    @Test
    void findAllByAgeGreaterThanEqual() {
        List<PersonDTO> personDTOList = new ArrayList<>();
        List<Person> personList = new ArrayList<>();
        personDTOList.add(personDTO);
        personList.add(person);
        when(personRepository.findAllByEdadGreaterThanEqual(18)).thenReturn(personList);
        assertEquals(personDTOList,personImplement.findAllByAgeGreaterThanEqual(18));
    }

    @Test
    void updateError() {
        when(personRepository.existsById("1012443736")).thenReturn(false);
        assertThrows(PersonNotFound.class, () -> { personImplement.update(personDTO); });
    }

    @Test
    void update() {
        when(personRepository.existsById("1012443736")).thenReturn(true);
        personImplement.update(personDTO);
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void deleteByIdError() {
        when(personRepository.existsById("1012443736")).thenReturn(false);
        assertThrows(PersonNotFound.class, () -> { personImplement.deleteById("1012443736"); });
    }

    @Test
    void deleteById() {
        when(personRepository.existsById("1012443736")).thenReturn(true);
        personImplement.deleteById("1012443736");
        verify(personRepository, times(1)).deleteById("1012443736");
    }
}