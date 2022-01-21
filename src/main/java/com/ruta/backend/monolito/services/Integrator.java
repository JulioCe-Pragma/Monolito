package com.ruta.backend.monolito.services;

import com.ruta.backend.monolito.dto.ImageDTO;
import com.ruta.backend.monolito.dto.PersonDTO;
import com.ruta.backend.monolito.exceptions.PersonNotFound;
import com.ruta.backend.monolito.services.interfaces.ImageService;
import com.ruta.backend.monolito.services.interfaces.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class Integrator {

    private ImageService imageService;
    private PersonService personService;

    public void saveImage (ImageDTO imageDTO){
        Optional<PersonDTO> personDTO = personService.findById(imageDTO.getNumDocumento());
        if(!personDTO.isEmpty()){
            this.imageService.save(imageDTO);
            personDTO.get().setImagen(true);
            this.personService.update(personDTO.get());
        }else {
            throw new PersonNotFound();
        }
    }

    public Optional<ImageDTO> findImageById(String id) {
        if(personService.existsById(id)){
            return this.imageService.findById(id);
        }else {
            throw new PersonNotFound();
        }
    }

    public void updateImage(ImageDTO imageDTO){
        if(personService.existsById(imageDTO.getNumDocumento())){
            this.imageService.save(imageDTO);
        }else {
            throw new PersonNotFound();
        }
    }

    public void deleteImageById(String personaId) {
        Optional<PersonDTO> personDTO = personService.findById(personaId);
        if(!personDTO.isEmpty()){
            this.imageService.deleteById(personaId);
            personDTO.get().setImagen(false);
            this.personService.update(personDTO.get());
        }else {
            throw new PersonNotFound();
        }
    }

    public void deletePersonById(String personaId) {
        deleteImageById(personaId);
        this.personService.deleteById(personaId);
    }
}
