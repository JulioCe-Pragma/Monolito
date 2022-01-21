package com.ruta.backend.monolito.services.implementations;

import com.ruta.backend.monolito.dto.ImageDTO;
import com.ruta.backend.monolito.entities.Image;
import com.ruta.backend.monolito.repositories.ImageRepository;
import com.ruta.backend.monolito.services.interfaces.ImageService;
import com.ruta.backend.monolito.utils.helpers.Helper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ImageImplement implements ImageService {

    private ImageRepository repository;

    @Override
    public void save (ImageDTO imageDTO){
        Image image = Helper.modelMapper().map(imageDTO, Image.class);
        this.repository.save(image);
    }

    @Override
    public Optional<ImageDTO> findById(String id) {
        Optional<Image> imagen = this.repository.findById(id);
        return imagen.map(this::convertToImagenDTO);
    }

    @Override
    public void update(ImageDTO imageDTO){
        Image image = Helper.modelMapper().map(imageDTO, Image.class);
        this.repository.save(image);
    }

    @Override
    public void deleteById(String personaId) {
        this.repository.deleteById(personaId);
    }

    private ImageDTO convertToImagenDTO(final Image image){
        return Helper.modelMapper().map(image, ImageDTO.class);
    }
}
