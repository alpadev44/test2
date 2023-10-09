package com.example.Backend.Service.Implementation;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.ImageDTO;
import com.example.Backend.Model.Entity.Image;
import com.example.Backend.Model.Entity.Product;
import com.example.Backend.Repository.IImageRepository;
import com.example.Backend.Repository.IProductRepository;
import com.example.Backend.Service.IImageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@AllArgsConstructor
@Service
public class ImageService implements IImageService {

    @Autowired
    private IImageRepository imageRepository;

    public void addImage(ImageDTO imageDTO) {
        Image image = changeImageDTOToImage(imageDTO);
        imageRepository.save(image);
    }

    @Override
    public Set<ImageDTO> allImages() {
        List<Image> imageList = imageRepository.findAll();
        Set<ImageDTO> imageDTOSet = new HashSet<>();

        for (Image imageSets : imageList) {
            imageDTOSet.add(changeImageToImageDTO(imageSets));
        }
        System.out.println("GET");
        return imageDTOSet;
    }

    @Override
    public void updateImage(ImageDTO imageDTO) {
        changeImageToImageDTO(imageRepository.save(changeImageDTOToImage(imageDTO)));
    }

    @Override
    public void deleteImage(Long id) throws NotFoundException {
        Optional<Image> searchImage = imageRepository.findById(id);
        if(searchImage.isPresent()) {
            imageRepository.deleteById(id);
        }
        else {
            throw  new NotFoundException("ID " + id + " Not Found");
        }

    }

    @Override
    public Optional<ImageDTO> findImage(Long id) {
        Optional<Image> searchImage = imageRepository.findById(id);
        if(searchImage.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(changeImageToImageDTO(searchImage.get()));
    }

    @Override
    public Image changeImageDTOToImage(ImageDTO imageDTO) {
        Image image = new Image();

        image.setId(imageDTO.getId());
        image.setUrl(imageDTO.getUrl());

        System.out.println("Image");
        return image;
    }

    @Override
    public ImageDTO changeImageToImageDTO(Image image) {
        ImageDTO imageDTO = new ImageDTO();

        imageDTO.setId(image.getId());
        imageDTO.setUrl(image.getUrl());

        System.out.println("ImageDTO");
        return imageDTO;
    }
}
