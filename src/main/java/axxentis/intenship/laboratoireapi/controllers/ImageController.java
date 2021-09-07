package axxentis.intenship.laboratoireapi.controllers;

import axxentis.intenship.laboratoireapi.entities.Image;
import axxentis.intenship.laboratoireapi.payload.responses.ApiResponse;
import axxentis.intenship.laboratoireapi.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    @GetMapping(value = "/all")
    public ResponseEntity<?>getAll(){
        List<Image> images = imageService.getAllImages();
        if (!CollectionUtils.isEmpty(images)){
            return ResponseEntity.ok(new ApiResponse(true, images, "Images loaded successfully" , HttpStatus.OK));
        }else {
            return ResponseEntity.ok(new ApiResponse(false, images, "Failed to load images, empty List", HttpStatus.NOT_FOUND));
        }
    }

    @GetMapping(value = "{/id}")
    public ResponseEntity<?>getImage(@PathVariable(value = "id") final Long id){
        Optional<Image> image = imageService.findImageById(id);
        if (image.isPresent()){
            return ResponseEntity.ok(new ApiResponse(true, image, "Image loaded successfully", HttpStatus.OK));
        }else {
            return ResponseEntity.ok(new ApiResponse(false, image, "Unsuccessful attempt, image not found", HttpStatus.NOT_FOUND));
        }
    }
    @PostMapping(value = "/add")
    public ResponseEntity<?> addImage(@RequestBody Image image) {
        Image newImage = imageService.addImage(image);
        if (image.getUrl().isBlank() || image.getUrl().isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(false, image, "Please enter the image url!", HttpStatus.PARTIAL_CONTENT));
        }else {
            return ResponseEntity.ok(new ApiResponse(true, newImage, "Image added successfully", HttpStatus.OK));
        }

    }

//    @PostMapping(value = "/add")
//    public ResponseEntity<?>createImage(@RequestBody Image image){
//        Image saveImage = imageService.addImage(image);
//        return ResponseEntity.ok(new ApiResponse(true, image, "Image added Successfully", HttpStatus.OK));
//    }


    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?>updateImage(@PathVariable(value = "id") final Long id, @RequestBody Image image){
        Image newImage = imageService.updateImage(id, image);
        if (imageService.findImageById(id).isEmpty()){
            return ResponseEntity.ok(new ApiResponse(false, image, "Image id " + id + " not found", HttpStatus.NOT_FOUND));
        }else {
            return ResponseEntity.ok(new ApiResponse(true, newImage, "Image updated Successfully", HttpStatus.OK));
        }

    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?>deleteImage(@PathVariable(value = "id") final Long id){
        imageService.deleteImage(id);
        return ResponseEntity.ok(new ApiResponse(true, "Image deleted Successfully", HttpStatus.OK));
    }
}


