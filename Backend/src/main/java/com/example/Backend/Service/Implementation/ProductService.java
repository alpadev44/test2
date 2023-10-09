package com.example.Backend.Service.Implementation;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.*;
import com.example.Backend.Model.Entity.*;
import com.example.Backend.Repository.*;
import com.example.Backend.Service.IProductService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository iProductRepository;

    @Autowired
    private ICategoryRepository iCategoryRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ICityRepository cityRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.access_key_id}")
    private String accessKey;

    @Value("${aws.secret_access_key}")
    private String secretAccessKey;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;


    @Override
    public void addProduct(@Valid ProductDTO productDTO) {

        if (productDTO.getScore() < 1 || productDTO.getScore() > 5) {
            throw new IllegalArgumentException("Score must be between 1 and 5");
        }

        Product product = changeProductDTOToProduct(productDTO);

        if(productDTO.getImageDTO() != null) {
            Set<Image> imageSet = new HashSet<>();
            for (ImageDTO imageDTO : productDTO.getImageDTO()) {
                imageSet.add(imageService.changeImageDTOToImage(imageDTO));
            }
            product.setImages(imageSet);
        }


        System.out.println("POST");
        iProductRepository.save(product);
    }

    @Override
    public Set<ProductDTO> allProducts() {
        List<Product> productList = iProductRepository.findAll();
        Set<ProductDTO> productDTOSet =  new HashSet<>();

        for (Product productSets : productList) {
            productDTOSet.add(changeProductToProductDTO(productSets));
        }
        return productDTOSet;
    }

    @Override
    public void updateProduct(ProductDTO productDTO) {
        Product product = changeProductDTOToProduct(productDTO);
        if(product.getId() != null) {
            Optional<Product> existingProduct = iProductRepository.findById(product.getId());
            if(existingProduct.isPresent()) {
                Product managedProduct = existingProduct.get();

                managedProduct.setTitle(product.getTitle());
                managedProduct.setPrice(product.getPrice());
                managedProduct.setDescription(product.getDescription());
                managedProduct.setScore(product.getScore());
                managedProduct.setDateTimeProduct(product.getDateTimeProduct());

                Boolean noExistingImages = false;

                for(ImageDTO imageDTO : productDTO.getImageDTO()) {
                    Optional<ImageDTO> searchImageDTO = imageService.findImage(imageDTO.getId());
                    if(searchImageDTO.isEmpty()) {
                        noExistingImages = true;
                    }
                }


                if(product.getImages() != null &&
                        noExistingImages.equals(false) &&
                        product.getCategories() != null &&
                        product.getCategories().getId() != null
                ) {

                    Optional<Category> searchCategory = iCategoryRepository.findById(product.getCategories().getId());
                    if(searchCategory.isPresent()) {
                        Category managedCategory = searchCategory.get();
                        product.setCategories(managedCategory);
                    }
                }
            }
            iProductRepository.save(product);
        }
    }

    @Override
    public void deleteProduct(Long id) throws NotFoundException {
        Optional<Product> searchProduct = iProductRepository.findById(id);
        if(searchProduct.isPresent()) {
            iProductRepository.deleteById(id);
        }
        else {
            throw  new NotFoundException("ID " + id + " Not Found");
        }
    }

    @Override
    public Optional<ProductDTO> findProduct(Long id) throws NotFoundException {
        Optional<Product> searchProduct = iProductRepository.findById(id);
        if(searchProduct.isPresent()) {
            return Optional.of(changeProductToProductDTO(searchProduct.get()));
        }
        else {
            throw  new NotFoundException("ID " + id + " Not Found");
        }
    }

    public List<LocalDateTime> getAvailableDatesForProduct(Product product) {

        List<LocalDateTime> productAvailableDates = new ArrayList<>();

        Set<LocalDateTime> productReservedDates = new HashSet<>();
        for (Booking booking : product.getBookings() ) {
            LocalDateTime startDateProduct = booking.getStart_date();
            LocalDateTime finishDateProudct = booking.getFinish_date();

            LocalDateTime currentDate = startDateProduct;
            while(currentDate.isBefore(finishDateProudct) || currentDate.equals(finishDateProudct)) {
               productReservedDates.add(currentDate);
               currentDate = currentDate.plusDays(1);
            }
        }

        LocalDateTime currentStartDate = LocalDateTime.now();
        LocalDateTime currentFinishDate = LocalDateTime.now().plusMonths(6);

        LocalDateTime currentDate = currentStartDate;
        while(currentDate.isBefore(currentFinishDate) || currentDate.equals(currentFinishDate)) {
            if(!productReservedDates.contains(currentDate)) {
                productAvailableDates.add(currentDate);
            }
            currentDate = currentDate.plusDays(1);
        }

        return productAvailableDates;
    }

    @Override
    public Product changeProductDTOToProduct(ProductDTO productDTO) {
        Product product = new Product();
        Category category = new Category();
        City city = new City();


        product.setId(productDTO.getId());
        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setScore(productDTO.getScore());
        product.setDateTimeProduct(productDTO.getDateTimeProduct());

        Set<Image> imageSet = new HashSet<>();
        if(productDTO.getImageDTO() != null) {
            for (ImageDTO imageDTO : productDTO.getImageDTO()) {
                imageSet.add(imageService.changeImageDTOToImage(imageDTO));
            }
        }
        product.setImages(imageSet);

        category.setId(productDTO.getCategories_id());
        product.setCategories(category);

        city.setId(productDTO.getCity_id());
        product.setCity(city);

        System.out.println("Product");
        return product;
    }

    @Override
    public ProductDTO changeProductToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setTitle(product.getTitle());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setScore(product.getScore());
        productDTO.setDateTimeProduct(product.getDateTimeProduct());
        productDTO.setCategories_id(product.getCategories().getId());


        Set<ImageDTO> imageDTOSet = new HashSet<>();
        if (product.getImages() != null) {
            for (Image image : product.getImages()) {
                imageDTOSet.add(imageService.changeImageToImageDTO(image));
            }
        }
        productDTO.setImageDTO(imageDTOSet);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(product.getCategories().getId());
        categoryDTO.setTitle(product.getCategories().getTitle());
        categoryDTO.setDescription(product.getCategories().getDescription());
        categoryDTO.setUrl(product.getCategories().getUrl());

        productDTO.setCategoryDTO(categoryDTO);

        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(product.getCity().getId());
        cityDTO.setName(product.getCity().getName());
        cityDTO.setCountry(product.getCity().getCountry());

        productDTO.setCityDTO(cityDTO);

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(product.getCity().getAddress().getId());
        addressDTO.setCalle(product.getCity().getAddress().getCalle());
        addressDTO.setNumero(product.getCity().getAddress().getNumero());
        addressDTO.setLocalidad(product.getCity().getAddress().getLocalidad());
        addressDTO.setProvincia(product.getCity().getAddress().getProvincia());

        productDTO.getCityDTO().setAddressDTO(addressDTO);

        System.out.println("ProductDTO");
        return productDTO;
    }
}
