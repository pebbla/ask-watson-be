package com.apebble.askwatson.config;

import java.io.FileInputStream;
import java.util.Iterator;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Storage.BlobListOption;

import lombok.Getter;

@Configuration

public class GoogleCloudConfig {

    final static String HOST_URL = "https://storage.googleapis.com/";
    final static String PROJECT_ID = "pelagic-berm-360511";
    final static String BUCKET_NAME = "ask_watson_pebbla";


    /**
     * 구글 스토리지에 이미지를 저장합니다.
     * @param objectName
     * @param file
     * @return
     * @throws Exception
     */
    public String uploadObject(String objectName, MultipartFile file) throws Exception {

        Storage storage = StorageOptions.newBuilder().setProjectId(PROJECT_ID)
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream("src/main/resources/pelagic-berm-360511-199ce13e58b2.json")))
                .build().getService();
        BlobId blobId = BlobId.of(BUCKET_NAME, objectName);

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/png").build();
        Blob createdInfo = storage.create(blobInfo, file.getBytes());
        String createdName = createdInfo.getName();

        return HOST_URL + BUCKET_NAME + "/" + createdName;
    }


    /**
     * 구글 스토리지의 이미지를 삭제합니다.
     * @param objectNamePrefix
     * @return
     * @throws Exception
     */
    public void deleteObject(String objectNamePrefix) throws Exception {

        Storage storage = StorageOptions.newBuilder().setProjectId(PROJECT_ID)
        .setCredentials(GoogleCredentials.fromStream(new FileInputStream("src/main/resources/pelagic-berm-360511-199ce13e58b2.json")))
        .build().getService();

        Page<Blob> blobs = storage.list(BUCKET_NAME, BlobListOption.prefix(objectNamePrefix));
        Iterator<Blob> blobIterator = blobs.iterateAll().iterator();
        while (blobIterator.hasNext()) {
          Blob blob = blobIterator.next();
          blob.delete();
        }      
    }


    @Getter
    public static class UploadReqDto {
        private String bucketName;
        private String uploadFileName;
        private String localFileLocation;
    }
}
