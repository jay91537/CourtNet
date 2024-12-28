package com.dbcourtnet.review;

import com.dbcourtnet.court.CourtService;
import com.dbcourtnet.location.LocationService;
import com.dbcourtnet.login.session.SessionManager;
import com.dbcourtnet.review.dto.ReviewRequestDTO;
import com.dbcourtnet.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final LocationService locationService;
    private final UserService userService;
    private final CourtService courtService;
    private final SessionManager sessionManager;

    @GetMapping(value = "/findLocation/location/{locationId}/review")
    public String createReviewPage(HttpServletRequest request,
                                   @ModelAttribute ReviewRequestDTO reviewRequest, @PathVariable Long locationId, Model model) {

        if(sessionManager.getSession(request)==null) {
            return "/home";
        }

        reviewRequest.setLocationId(locationId);
        model.addAttribute("username", userService.findById(sessionManager.getSession(request)).get().getUsername());
        model.addAttribute("reviewRequest", reviewRequest);

        return "/review";
    }

    @PostMapping(value = "/findLocation/location/{locationId}/review")
    public String createReview(HttpServletRequest request,@ModelAttribute ReviewRequestDTO reviewRequest,
                               @PathVariable Long locationId, Model model) {

        reviewRequest.setUserId(sessionManager.getSession(request));
        reviewRequest.setUsername(userService.findById(sessionManager.getSession(request)).get().getUsername());
        reviewService.join(reviewRequest);

        return "redirect:/findLocation/location/{locationId}";
    }

    @GetMapping(value = "/home/myReview")
    public String getMyReview(HttpServletRequest request, Model model) {

        if(sessionManager.getSession(request)==null) {
            return "/home";
        }

        List<Review> reviewList = reviewService.findAllByUserId(sessionManager.getSession(request));

        model.addAttribute("reviewList", reviewList);
        return "/myReview";
    }

    @GetMapping("/findLocation/location/{locationId}/review/{reviewId}/edit")
    public String editReviewPage(HttpServletRequest request,
                                 @PathVariable Long locationId,
                                 @PathVariable Long reviewId,
                                 Model model) {

        if(sessionManager.getSession(request)==null) {
            return "/home";
        }

        Review review = reviewService.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));


        if (!review.getUser().getId().equals(sessionManager.getSession(request))) {
            throw new IllegalArgumentException("리뷰 수정 권한이 없습니다.");
        }

        ReviewRequestDTO reviewRequest = new ReviewRequestDTO();
        reviewRequest.setRating(review.getRating());
        reviewRequest.setText(review.getText());

        model.addAttribute("review", review);
        model.addAttribute("locationId", locationId);
        model.addAttribute("reviewRequest", reviewRequest);
        model.addAttribute("username", review.getUsername());

        return "updateReview";
    }

    @PostMapping("/findLocation/location/{locationId}/review/{reviewId}/edit")
    public String editReview(HttpServletRequest request,
                             @PathVariable Long locationId,
                             @PathVariable Long reviewId,
                             @ModelAttribute ReviewRequestDTO reviewRequest) {

        if(sessionManager.getSession(request)==null) {
            return "/home";
        }

        reviewRequest.setUserId(sessionManager.getSession(request));
        reviewService.updateReview(reviewId, reviewRequest);

        return "redirect:/findLocation/location/" + locationId;
    }

    @PostMapping("/findLocation/location/{locationId}/review/{reviewId}/delete")
    public String deleteReview(HttpServletRequest request,
                               @PathVariable Long locationId,
                               @PathVariable Long reviewId) {

        if(sessionManager.getSession(request)==null) {
            return "/home";
        }

        try {
            reviewService.deleteReview(reviewId, sessionManager.getSession(request));
            return "redirect:/findLocation/location/" + locationId;
        } catch (IllegalArgumentException e) {

            return "redirect:/findLocation/location/" + locationId;
        }
    }


}



