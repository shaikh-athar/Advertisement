package com.gov.Advertisments.Service;

import com.gov.Advertisments.Model.Banner;
import com.gov.Advertisments.Model.Request.BannerRequest;

import java.util.List;

public interface BannerService {
    List<Banner> getAllBanners();
    String saveBanner(BannerRequest bannerRequest);
    String deleteScreen(Long id);
}
