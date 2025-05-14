package com.gov.Advertisments.Service;

import com.gov.Advertisments.Model.Request.ScreenRequest;
import com.gov.Advertisments.Model.Response.ScreenResponse;
import com.gov.Advertisments.Model.Screen;

import java.util.List;
import java.util.Optional;

public interface ScreenService {
    List<Screen> getAllScreens();
    List<Screen> getScreenByLocationAndActive(String location, Boolean active);
    List<ScreenResponse> getLocationByRating(int rating);
    String saveScreen(ScreenRequest screenRequest);
    Optional<Screen> getScreenById(Long id);
    String deleteScreen(Long id);
}
