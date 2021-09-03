package axxentis.intenship.laboratoireapi.services;

import axxentis.intenship.laboratoireapi.entities.Image;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    public List<Image> getAllImages();

    Optional<Image> findImageById(Long imageId);

    public Image addImage(Image image);

    public Image updateImage(Long imageId, Image image);

    void deleteImage(Long id);
}
