package com.ruta.backend.monolito.services.interfaces;

import com.ruta.backend.monolito.dto.PersonDTO;
import com.ruta.backend.monolito.exceptions.PersonNotFound;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PersonService {

    void save(PersonDTO personDTO);

    Optional<PersonDTO> findByDocumentTypeAndNumDocument(String documentType, String personId);

    boolean existsById (String personId);

    Optional<PersonDTO> findById(String id);

    List<PersonDTO> findAllByAgeGreaterThanEqual(int age);

    void update(PersonDTO personDTO);

    void deleteById(String personId);
}
