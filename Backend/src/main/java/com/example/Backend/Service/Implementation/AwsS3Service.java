package com.example.Backend.Service.Implementation;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.*;
import com.example.Backend.Model.Entity.Image;
import com.example.Backend.Service.IAwsS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AwsS3Service implements IAwsS3Service {
    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public AwsS3Service(AmazonS3 amazonS3, String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    public AwsS3Service() {
    }

    @Override
    public List<String> uploadFile(List<MultipartFile> files) {
        List<String> filesUrl = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String newFileName = System.currentTimeMillis() + file.getOriginalFilename();
                PutObjectRequest request = new PutObjectRequest(bucketName, newFileName, file.getInputStream(), new ObjectMetadata());
                amazonS3.putObject(request);
                GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, newFileName)
                        .withMethod(HttpMethod.GET);
                URL url = amazonS3.generatePresignedUrl(urlRequest);
                String fileUrl = "http://" + url.getHost() + url.getPath();
                filesUrl.add(fileUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filesUrl;
    }

    @Override
    public List<String> getObjectsFromS3() {
        ListObjectsV2Result result = amazonS3.listObjectsV2(bucketName);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        List<String> list = objects.stream().map(item -> {
            return item.getKey();
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public InputStream downloadFile(String key) {
        S3Object object = amazonS3.getObject(bucketName, key);
        return object.getObjectContent();
    }

}
