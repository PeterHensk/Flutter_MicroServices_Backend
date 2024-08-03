package tech.henskens.stationservice.manager;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;
import java.util.Base64;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.GetObjectRequest;
import tech.henskens.stationservice.dto.ImageResponseDto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class S3Manager implements IS3Manager {
    private AmazonS3 s3Client;

    @Value("${aws.accessKeyId}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @PostConstruct
    public void init() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(this.region)
                .build();
    }

    @Override
    public String uploadImage(String base64Image, String contentType) {
        String fileName = UUID.randomUUID().toString();
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        InputStream fis = new ByteArrayInputStream(imageBytes);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(imageBytes.length);
        metadata.setContentType(contentType);
        String bucketName = "station-images";
        this.s3Client.putObject(bucketName, fileName, fis, metadata);
        return fileName;
    }

    @Override
    public ImageResponseDto loadImage(String imageId) {
        String bucketName = "station-images";
        S3Object s3Object = this.s3Client.getObject(new GetObjectRequest(bucketName, imageId));
        try (InputStream inputStream = s3Object.getObjectContent();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            String base64Image = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            String contentType = s3Object.getObjectMetadata().getContentType();
            return ImageResponseDto.builder()
                    .base64Image(base64Image)
                    .contentType(contentType)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image from S3", e);
        }
    }
}

