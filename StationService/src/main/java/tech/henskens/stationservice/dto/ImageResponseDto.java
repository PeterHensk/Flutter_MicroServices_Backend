package tech.henskens.stationservice.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponseDto {
    private String base64Image;
    private String contentType;
}
