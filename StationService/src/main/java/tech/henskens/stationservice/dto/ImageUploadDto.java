package tech.henskens.stationservice.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageUploadDto {
    private String image;
    private String contentType;
}
