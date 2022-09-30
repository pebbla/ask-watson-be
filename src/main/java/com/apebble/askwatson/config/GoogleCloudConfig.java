package com.apebble.askwatson.config;

import java.io.FileInputStream;

import org.jboss.logging.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import lombok.Getter;

@Configuration

public class GoogleCloudConfig {

    final static String HOST_URL = "https://storage.googleapis.com/";
    final static String PROJECT_ID = "pelagic-berm-360511";
    final static String BUCKET_NAME = "ask_watson_pebbla";

    public String uploadObject(String objectName, MultipartFile file) throws Exception {

        Storage storage = StorageOptions.newBuilder().setProjectId(PROJECT_ID)
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream("src/main/resources/pelagic-berm-360511-199ce13e58b2.json")))
                .build().getService();
        BlobId blobId = BlobId.of(BUCKET_NAME, objectName);

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/png").build();
        Blob createdInfo = storage.create(blobInfo, file.getBytes());
        String createdName = createdInfo.getName();

        System.out.println("File " + file.getName() + " uploaded to bucket " + BUCKET_NAME + " as " + objectName);

        return HOST_URL + BUCKET_NAME + "/" + createdName;
    }


    @Getter
    public static class UploadReqDto {
        private String bucketName;
        private String uploadFileName;
        private String localFileLocation;
    }
}
