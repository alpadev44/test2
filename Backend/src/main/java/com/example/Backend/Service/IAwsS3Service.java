package com.example.Backend.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface IAwsS3Service {
    List<String> uploadFile(List<MultipartFile> file);

    List<String> getObjectsFromS3();

    InputStream downloadFile(String key);
}
