package com.dbcourtnet.controller;

import com.dbcourtnet.court.CourtService;
import com.dbcourtnet.court.CourtTexture;
import com.dbcourtnet.location.Location;
import com.dbcourtnet.location.LocationService;
import com.dbcourtnet.dto.locationdto.ControllerLocationRequestDTO;
import com.dbcourtnet.login.session.SessionConst;
import com.dbcourtnet.review.Review;
import com.dbcourtnet.review.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
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
    public String findLocationPage(HttpServletRequest request,@SessionAttribute(name = SessionConst.sessionId, required = false) Long userId, Model model) {

        if(userId == null) {
            return "home";
        }

        model.addAttribute("controllerLocationRequest", new ControllerLocationRequestDTO());
        return "findLocation";
    }

    @PostMapping("")
    public String findLocation(HttpServletRequest request ,@SessionAttribute(name = SessionConst.sessionId, required = false) Long userId
                                                            ,@ModelAttribute ControllerLocationRequestDTO controllerLocationRequest, Model model) {

        if(userId == null) {
            return "home";
        }

        List<Location> locationList = locationService.findByAddress(controllerLocationRequest.getAddress());
        model.addAttribute("locationList", locationList);
        model.addAttribute("controllerLocationRequest", controllerLocationRequest);

        return "/findLocation";
    }

    @GetMapping("/location/{id}")
    public String locationDetail(HttpServletRequest request, @SessionAttribute(name = SessionConst.sessionId, required = false) Long userId,@PathVariable Long id, Model model) {

        if(userId == null) {
            return "home";
        }

        Optional<Location> location = locationService.findLocationById(id);

        List<CourtTexture> courtTextures = courtService.findCourtTextures(id);
        List<Review> reviewList = reviewService.findAllByLocationId(id);


        model.addAttribute("userId", request.getSession().getAttribute(SessionConst.sessionId));
        model.addAttribute("location", location);
        model.addAttribute("courtTextures", courtTextures);
        model.addAttribute("reviewList", reviewList);
        return "/locationDetail";
    }
}