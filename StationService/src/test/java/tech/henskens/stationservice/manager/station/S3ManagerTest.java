package tech.henskens.stationservice.manager.station;

import com.amazonaws.services.s3.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.amazonaws.services.s3.AmazonS3;
import tech.henskens.stationservice.dto.ImageResponseDto;
import tech.henskens.stationservice.manager.S3Manager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@ExtendWith(MockitoExtension.class)
public class S3ManagerTest {

    @Mock
    private AmazonS3 s3Client;

    @InjectMocks
    private S3Manager s3Manager;

    @Test
    public void uploadImageTest() {
        String base64Image = "iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==";
        String contentType = "image/jpeg";
        PutObjectResult putObjectResult = new PutObjectResult();
        when(s3Client.putObject(anyString(), anyString(), any(InputStream.class), any(ObjectMetadata.class))).thenReturn(putObjectResult);

        String result = s3Manager.uploadImage(base64Image, contentType);

        assertNotNull(result);
        verify(s3Client, times(1)).putObject(anyString(), anyString(), any(InputStream.class), any(ObjectMetadata.class));
    }

    @Test
    public void loadImageTest() throws IOException {
        String imageId = "testImageId";
        String contentType = "image/jpeg";
        byte[] imageBytes = "testImageContent".getBytes();
        InputStream inputStream = new ByteArrayInputStream(imageBytes);

        S3Object s3Object = new S3Object();
        s3Object.setObjectContent(inputStream);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        s3Object.setObjectMetadata(metadata);

        when(s3Client.getObject(any(GetObjectRequest.class))).thenReturn(s3Object);

        ImageResponseDto result = s3Manager.loadImage(imageId);

        assertNotNull(result);
        assertEquals(Base64.getEncoder().encodeToString(imageBytes), result.getBase64Image());
        assertEquals(contentType, result.getContentType());
        verify(s3Client, times(1)).getObject(any(GetObjectRequest.class));
    }
}
