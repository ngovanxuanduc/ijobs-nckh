package com.immortal.internship.controller;

import com.immortal.internship.payload.request.CreateRecruitmentForm;
import com.immortal.internship.service.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AWSS3Controller {
    @Autowired
    private AmazonClient amazonClient;

//    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity saveFilesToS3(@RequestParam(value = "files") MultipartFile[] files) {
//        List<String> urlResponse = new ArrayList<>();
//        for (MultipartFile file : files) {
//            urlResponse.add(amazonClient.uploadFile(file));
//        }
//        return ResponseEntity.status(HttpStatus.CREATED).body(urlResponse);
//    }

    @PostMapping("/uploadimg")
    public ResponseEntity saveImageToS3(@RequestBody CreateRecruitmentForm form){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Optional.ofNullable(form.getImage())
                .map(img -> amazonClient.uploadFile(img))
                .orElseGet(() -> new String("Image Null !!!"))
        );
    }



}
