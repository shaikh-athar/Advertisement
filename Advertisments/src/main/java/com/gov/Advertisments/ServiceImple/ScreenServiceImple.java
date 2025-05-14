package com.gov.Advertisments.ServiceImple;

import com.gov.Advertisments.Model.Request.ScreenRequest;
import com.gov.Advertisments.Model.Response.ScreenResponse;
import com.gov.Advertisments.Model.Screen;
import com.gov.Advertisments.Repository.ScreenRepo;
import com.gov.Advertisments.Service.ScreenService;
import com.gov.Advertisments.ServiceImple.OtherImple.IdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScreenServiceImple implements ScreenService {

    @Autowired
    private ScreenRepo screenRepository;
    
    public List<Screen> getAllScreens() {
        return screenRepository.findAll();
    }

    public List<Screen> getScreenByLocationAndActive(String location,Boolean active){
        return screenRepository.findByLocationAndActive(location,active);
    }

    public List<ScreenResponse> getLocationByRating(int rating) {
        List<Screen> screenList = screenRepository.findByRating(rating);
        List<ScreenResponse> responseList = new ArrayList<>();

        for (Screen screen : screenList) {
            responseList.add(new ScreenResponse(screen.getSerialNo(), screen.getLocation(), screen.getRating()));
        }
        return responseList;
    }


    public String saveScreen(ScreenRequest screenRequest) {
            Screen screen = new Screen();

            screen.setLocation(screenRequest.getLocation());
            screen.setSerialNo(IdGenerator.getId(5));
            screen.setRating(screenRequest.getRating());
            screen.setLatitude(screenRequest.getLatitude());
            screen.setLongitude(screenRequest.getLongitude());
            screen.setIsActive(Boolean.TRUE);
            screenRepository.save(screen);
            return "Screen Added !! ";

    }

    public Optional<Screen> getScreenById(Long id){
        return screenRepository.findById(id);
    }

    @Transactional
//    "org.springframework.dao.InvalidDataAccessApiUsageException: Executing an update/delete query"
    public String deleteScreen(Long id) {
        screenRepository.removeScreen(id);
        return "Screen Deactivate";
    }
}
