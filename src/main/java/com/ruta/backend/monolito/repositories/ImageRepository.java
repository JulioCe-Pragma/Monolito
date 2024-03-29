package com.ruta.backend.monolito.repositories;

import com.ruta.backend.monolito.entities.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends MongoRepository <Image, String> {

}
