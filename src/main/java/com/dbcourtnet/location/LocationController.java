package com.dbcourtnet.location;

import com.dbcourtnet.court.Court;
import com.dbcourtnet.court.CourtService;
import com.dbcourtnet.court.CourtTexture;
import com.dbcourtnet.location.dto.ControllerLocationRequestDTO;
import com.dbcourtnet.location.dto.LocationResponseDTO;
import com.dbcourtnet.login.LoginController;
import com.dbcourtnet.login.LoginService;
import com.dbcourtnet.review.Review;
import com.dbcourtnet.review.ReviewService;
import jdk.jfr.MemoryAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/findLocation")
public class LocationController {

    private final LocationService locationService;
    private final CourtService courtService;
    private final ReviewService reviewService;

    @GetMapping(value = "")
    public String findLocationPage(@CookieValue(name = "userId", required = true) Long userId, Model model) {
        model.addAttribute("controllerLocationRequest", new ControllerLocationRequestDTO());
        return "findLocation";
    }

    @PostMapping("")
    public String findLocation(@ModelAttribute ControllerLocationRequestDTO controllerLocationRequest, Model model) {

        List<Location> locationList = locationService.findByAddress(controllerLocationRequest.getAddress());
        model.addAttribute("locationList", locationList);
        model.addAttribute("controllerLocationRequest", controllerLocationRequest);

        return "/findLocation";
    }

    @GetMapping("/location/{id}")
    public String locationDetail(@CookieValue (name = "userId", required = true) Long userId, @PathVariable Long id, Model model) {
        Optional<Location> location = locationService.findLocationById(id);

        List<CourtTexture> courtTextures = courtService.findCourtTextures(id);
        List<Review> reviewList = reviewService.findAllByLocationId(id);

        model.addAttribute("userId", userId);
        model.addAttribute("location", location);
        model.addAttribute("courtTextures", courtTextures);
        model.addAttribute("reviewList", reviewList);
        return "/locationDetail";
    }
}