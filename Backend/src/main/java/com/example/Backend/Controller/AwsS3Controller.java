package com.example.Backend.Controller;

import java.util.List;

import com.example.Backend.Service.Implementation.AwsS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/s3")
@CrossOrigin(origins = "*")
public class AwsS3Controller {
    @Autowired
    private AwsS3Service awss3Service;

    @PostMapping(value = "/upload")
    public ResponseEntity<List<String>> uploadFile(@RequestPart(value="file") List<MultipartFile> file) {
        return ResponseEntity.ok(awss3Service.uploadFile(file));
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<String>> listFiles() {
        return new ResponseEntity<List<String>>(awss3Service.getObjectsFromS3(), HttpStatus.OK);
    }

    @GetMapping(value = "/download")
    public ResponseEntity<Resource> download(@RequestParam("key") String key) {
        InputStreamResource resource  = new InputStreamResource(awss3Service.downloadFile(key));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+key+"\"").body(resource);
    }
}
