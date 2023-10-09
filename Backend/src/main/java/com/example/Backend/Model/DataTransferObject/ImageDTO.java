package com.example.Backend.Model.DataTransferObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDTO {
    private Long id;
    private String url;

    public ImageDTO() {
    }

    public ImageDTO(String url) {
        this.url = url;
    }
}
