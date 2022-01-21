package com.ruta.backend.monolito.repositories;

import com.ruta.backend.monolito.entities.Person;
import com.ruta.backend.monolito.entities.DocumentType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository <Person, String> {

    @Transactional(readOnly = true)
    Optional<Person> findByTipoDocumentoAndNumDocumento(DocumentType documentType, String id);

    @Transactional(readOnly = true)
    List<Person> findAllByEdadGreaterThanEqual(int age);
}
