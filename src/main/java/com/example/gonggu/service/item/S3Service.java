package com.example.gonggu.service.item;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class S3Service {

    private AmazonS3 s3client;

    @Value("${cloud.aws.endpoinurl}")
    private String endpointUrl;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
//        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
        PutObjectRequest objectRequest = new PutObjectRequest(bucketName, fileName, file);
        objectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
        s3client.putObject(objectRequest);
    }

    // 파일 업로드
    public List<String> uploadFile(MultipartFile[] multipartFile) {

        String fileUrl = "";
        List<String> returnPaths = new ArrayList<>();

        // itemId와 tab단계에 해당하는 이미지 존재 여부 해서 S3버킷에 있는 사진을 지워야하지 않을까?

        // 3개의
        // 파일 저장
        try {
            for (MultipartFile fileOne : multipartFile){
                File file = convertMultiPartToFile(fileOne); // 파일형식
                String fileName = generateFileName(fileOne); // 파일이름
                fileUrl = endpointUrl + "/" + bucketName + "/" + fileName; // url 설정
                returnPaths.add(fileUrl);
                uploadFileTos3bucket(fileName, file); // 버킷에 저장될 파일이름
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnPaths;
    }

    // 파일 삭제
    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName =fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return "Successfully deleted";
    }
}
