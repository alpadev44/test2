package com.example.Backend.Service;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.ImageDTO;
import com.example.Backend.Model.DataTransferObject.RoleDTO;
import com.example.Backend.Model.Entity.Image;
import com.example.Backend.Model.Entity.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IImageService {
    void addImage(ImageDTO imageDTO);
    Set<ImageDTO> allImages();
    void updateImage(ImageDTO imageDTO);
    void deleteImage(Long id) throws NotFoundException;
    Optional<ImageDTO> findImage(Long id) throws NotFoundException;
    Image changeImageDTOToImage(ImageDTO imageDTO);
    ImageDTO changeImageToImageDTO(Image image);
}
