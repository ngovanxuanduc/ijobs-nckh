package com.immortal.internship.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.immortal.internship.utility.Base64Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class AmazonClient {

    private Logger logger = LoggerFactory.getLogger(AmazonClient.class);

    private AmazonS3 s3client;

    @Value("${awss3.endpointUrl}")
    private String endpointUrl;
    @Value("${awss3.bucketName}")
    private String bucketName;
    @Value("${awss3.accessKey}")
    private String accessKey;
    @Value("${awss3.secretKey}")
    private String secretKey;
    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTHEAST_1)
                .build();
    }

//    public void listObject(){
//        ObjectListing objectListing = s3client.listObjects(bucketName);
//        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
//            logger.info(os.getKey());
//        }
//    }
//
//    private File convertMultiPartToFile(MultipartFile file) throws IOException {
//        File convFile = new File(file.getOriginalFilename());
//        FileOutputStream fos = new FileOutputStream(convFile);
//        fos.write(file.getBytes());
//        fos.close();
//        return convFile;
//    }
//
//    private String generateFileName(MultipartFile multiPart) {
//        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
//    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName,fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

//    public String uploadFile(MultipartFile multipartFile) {
//        String fileUrl = "";
//        try {
//            File file = convertMultiPartToFile(multipartFile);
//            String fileName = generateFileName(multipartFile);
//            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
//            uploadFileTos3bucket(fileName, file);
//            file.delete();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return fileUrl;
//    }

    public String uploadFile(String encodedString){
        String fileUrl = null;
        try {
            File file = Base64Helper.base64ToFile(encodedString);
            String key = "images/"+file.getName();
            fileUrl = endpointUrl + "/" + bucketName + "/"+key;
            uploadFileTos3bucket(key, file);
            file.delete();
        } catch (Exception e) {
            logger.error("------------ ERRORS WHEN UP FILE TO S3 -----------------------");
            e.printStackTrace();
        }
        return fileUrl;
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
        return "Successfully deleted";
    }
}
