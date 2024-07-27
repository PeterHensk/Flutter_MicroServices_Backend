package tech.henskens.stationservice.manager.station;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import tech.henskens.stationservice.manager.S3Manager;

import java.io.InputStream;

@ExtendWith(MockitoExtension.class)
public class S3ManagerTest {

    @Mock
    private AmazonS3 s3Client;

    @InjectMocks
    private S3Manager s3Manager;

    @Test
    public void uploadImageTest() {
        String base64Image = "testImage";
        String contentType = "image/jpeg";

        PutObjectResult putObjectResult = new PutObjectResult();
        when(s3Client.putObject(anyString(), anyString(), any(InputStream.class), any(ObjectMetadata.class))).thenReturn(putObjectResult);

        String result = s3Manager.uploadImage(base64Image, contentType);

        assertNotNull(result);
        verify(s3Client, times(1)).putObject(anyString(), anyString(), any(InputStream.class), any(ObjectMetadata.class));
    }
}
