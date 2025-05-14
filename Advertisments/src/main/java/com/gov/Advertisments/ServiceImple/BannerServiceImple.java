package com.gov.Advertisments.ServiceImple;

import com.gov.Advertisments.Model.Banner;
import com.gov.Advertisments.Model.Request.BannerRequest;
import com.gov.Advertisments.Model.Screen;
import com.gov.Advertisments.Repository.BannerRepo;
import com.gov.Advertisments.Repository.ScreenRepo;
import com.gov.Advertisments.Service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BannerServiceImple implements BannerService {

    @Autowired
    BannerRepo bannerRepository;

    @Autowired
    ScreenRepo screenRepo;

    public List<Banner> getAllBanners(){
        return bannerRepository.findAllByActiveTrue();
    }

    public String saveBanner(BannerRequest bannerRequest) {
        Banner banner = new Banner();
        Optional<Screen> screen = screenRepo.findById(bannerRequest.getScreenId());
        banner.setImage(bannerRequest.getImage());
        banner.setScreen(screen.get());
        banner.setActive(Boolean.TRUE);
        bannerRepository.save(banner);
        return "Banner Added !! ";
    }
    public String deleteScreen(Long id) {
        Optional<Banner> banner = bannerRepository.findById(id);
        if(banner.isPresent()){
            Banner existBanner = banner.get();
            existBanner.setActive(Boolean.FALSE);
            bannerRepository.save(existBanner);
            return "Banner Deactivate";
        }
        else {
            return "Banner Not Found !!";
        }
    }
}
