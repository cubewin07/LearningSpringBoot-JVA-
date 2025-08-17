package org.example.file_uploading_service.controller;

import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.example.Exception.TooManyRequest;
import org.example.file_uploading_service.service.R2_service;
import org.example.rate_limiting.RateLimiterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/v1/r2")
@RequiredArgsConstructor
public class R2_Controller {
    private final R2_service r2_service;
    private final RateLimiterService rateLimiterService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
       if(file.isEmpty()){
           return ResponseEntity.badRequest().body("File is empty");
       }

       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       String username = auth.getPrincipal().getClass().getName();

       Bucket bucket = rateLimiterService.resolveBucket(username);

       if(!bucket.tryConsume(1))
           throw new TooManyRequest("You many request sent, please try again after a minute");

       String key = username+"/"+file.getOriginalFilename();
       if(file.getSize()>1024*1024*5){
           Path temp = Files.createTempFile(null, null);
           file.transferTo(temp);
           r2_service.uploadLargeFile(key,temp);
           return ResponseEntity.ok("File uploaded successfully");
       }

       byte[] data = file.getBytes();
       r2_service.uploadFile(key, data, file.getContentType());

        return ResponseEntity.ok(key);
    }

    @PostMapping("/upload/avatar")
    public ResponseEntity<String> uploadUserAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("File is empty");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().getClass().getName();
        String key = username+"/"+file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        Long contentSize = file.getSize();
        String contentType = file.getContentType();
        String url = r2_service.upLoadUserAvatar(key, inputStream, contentSize, contentType);
        return ResponseEntity.ok(url);
    }

}
