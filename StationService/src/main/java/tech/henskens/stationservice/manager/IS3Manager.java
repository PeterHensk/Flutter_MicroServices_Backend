package tech.henskens.stationservice.manager;

import tech.henskens.stationservice.dto.ImageResponseDto;

public interface IS3Manager {
    String uploadImage(String base64Image, String contentType);
    ImageResponseDto loadImage(String imageId);
}