package com.gov.Advertisments.Model.Request;


import jakarta.validation.constraints.*;

public class BannerRequest {

    @NotBlank(message = "Image/Video is required")
    @NotNull(message = "Image/Video can't be Null")
    private String image;


    @NotNull(message = "Screen ID is required")
    @Positive(message = "Screen ID must be a positive number")
    private Long screenId;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getScreenId() {
        return screenId;
    }

    public void setScreenId(Long screenId) {
        this.screenId = screenId;
    }
}
