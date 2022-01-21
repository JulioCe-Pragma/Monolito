package com.ruta.backend.monolito.services.implementations;

import com.ruta.backend.monolito.dto.ImageDTO;
import com.ruta.backend.monolito.dto.PersonDTO;
import com.ruta.backend.monolito.entities.Image;
import com.ruta.backend.monolito.repositories.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageImplementTest {

    @InjectMocks
    ImageImplement imageImplement;

    @Mock
    ImageRepository imageRepository;

    Image image;
    ImageDTO imageDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        image = new Image();
        image.setNumDocumento("1012443736");
        image.setImagen64("image64");

        imageDTO = new ImageDTO();
        imageDTO.setNumDocumento("1012443736");
        imageDTO.setImagen64("image64");
    }

    @Test
    void save() {
        imageImplement.save(imageDTO);
        verify(imageRepository, times(1)).save(image);
    }

    @Test
    void findById() {
        Optional<Image> optionalImage = Optional.of(image);
        Optional<ImageDTO> optionalImageDTO = Optional.of(imageDTO);
        when(imageRepository.findById("1012443736")).thenReturn(optionalImage);
        assertEquals(optionalImageDTO,imageImplement.findById("1012443736"));
    }

    @Test
    void update() {
        imageImplement.update(imageDTO);
        verify(imageRepository, times(1)).save(image);
    }

    @Test
    void deleteById() {
        imageImplement.deleteById("1012443736");
        verify(imageRepository, times(1)).deleteById("1012443736");
    }
}