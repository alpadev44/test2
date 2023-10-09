package com.example.Backend.Controller;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.ImageDTO;
import com.example.Backend.Model.Entity.Image;
import com.example.Backend.Service.Implementation.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/images")
@CrossOrigin(origins = "*")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addImage")
    public ResponseEntity<?> addImage(@RequestBody ImageDTO imageDTO) {
        imageService.addImage(imageDTO);
        URI uri = URI.create("/images/" + imageDTO.getId());
        return ResponseEntity.created(uri).body(imageDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/allImages")
    public Set<ImageDTO> allImages() { return imageService.allImages(); }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteImage/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable Long id) throws NotFoundException {
        imageService.deleteImage(id);
        return ResponseEntity.ok("ID " + id + " was deleted.");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateImage")
    public ResponseEntity<?> updateImage(@RequestBody ImageDTO imageDTO) {
        imageService.updateImage(imageDTO);
        return ResponseEntity.ok(imageDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/findImage/{id}")
    public ResponseEntity<?> findImage(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(imageService.findImage(id));
    }
}
