package com.dbcourtnet.review;

import com.dbcourtnet.court.CourtService;
import com.dbcourtnet.location.LocationService;
import com.dbcourtnet.review.dto.ReviewRequestDTO;
import com.dbcourtnet.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final LocationService locationService;
    private final UserService userService;
    private final CourtService courtService;

    @PostMapping(value = {"", "/"})
    public String createReview(@ModelAttribute ReviewRequestDTO reviewRequest) {



        return "/reviews";
    }

}



