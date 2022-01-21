package com.ruta.backend.monolito.services.interfaces;

import com.ruta.backend.monolito.dto.ImageDTO;

import java.util.Optional;

public interface ImageService {

    void save(ImageDTO imagen);

    Optional<ImageDTO> findById(String id);

    void update(ImageDTO imagen);

    void deleteById(String id);
}
