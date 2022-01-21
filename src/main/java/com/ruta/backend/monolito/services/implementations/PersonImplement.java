package com.ruta.backend.monolito.services.implementations;

import com.ruta.backend.monolito.dto.PersonDTO;
import com.ruta.backend.monolito.entities.Person;
import com.ruta.backend.monolito.entities.DocumentType;
import com.ruta.backend.monolito.exceptions.PersonNotFound;
import com.ruta.backend.monolito.exceptions.PersonAlreadyExist;
import com.ruta.backend.monolito.repositories.PersonRepository;
import com.ruta.backend.monolito.services.interfaces.PersonService;
import com.ruta.backend.monolito.utils.helpers.Helper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonImplement implements PersonService {

    private PersonRepository repository;

    @Override
    public void save(PersonDTO personDTO) {
        Person person = Helper.modelMapper().map(personDTO, Person.class);
        if(!this.repository.existsById(person.getNumDocumento())){
            this.repository.save(person);
        }else{
            throw new PersonAlreadyExist();
        }
    }

    @Override
    public Optional<PersonDTO> findByDocumentTypeAndNumDocument(String documentType, String personId){
        DocumentType documentTypeEnum = DocumentType.valueOf(documentType);
        Optional<Person> person = this.repository.findByTipoDocumentoAndNumDocumento(documentTypeEnum, personId);
        if(!person.isEmpty()){
            return person.map(this::convertToPersonasDTO);
        }else{
            throw new PersonNotFound();
        }
    }

    @Override
    public boolean existsById(String personId) {
        return this.repository.existsById(personId);
    }

    @Override
    public Optional<PersonDTO> findById(String Id) {
        Optional<Person> person = this.repository.findById(Id);
        return person.map(this::convertToPersonasDTO);
    }

    @Override
    public List<PersonDTO> findAllByAgeGreaterThanEqual(int edad) {
        List<Person> person = this.repository.findAllByEdadGreaterThanEqual(edad);
        return person.stream().map(this::convertToPersonasDTO).collect(Collectors.toList());
    }

    @Override
    public void update(PersonDTO personDTO) {
        Person person = Helper.modelMapper().map(personDTO, Person.class);
        if(this.repository.existsById(person.getNumDocumento())){
            this.repository.save(person);
        }else{
            throw new PersonNotFound();
        }
    }

    @Override
    public void deleteById(String personaId) {
        if(this.repository.existsById(personaId)){
            this.repository.deleteById(personaId);
        }else{
            throw new PersonNotFound();
        }
    }

    private PersonDTO convertToPersonasDTO(final Person person){
        return Helper.modelMapper().map(person, PersonDTO.class);
    }
}
