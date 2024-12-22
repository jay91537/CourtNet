package com.dbcourtnet.review;

import com.dbcourtnet.court.CourtService;
import com.dbcourtnet.location.LocationService;
import com.dbcourtnet.review.dto.ReviewRequestDTO;
import com.dbcourtnet.user.UserService;
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

    @GetMapping(value = "/findLocation/location/{locationId}/review")
    public String createReviewPage(@CookieValue(name = "userId", required = true) Long userId,
                               @ModelAttribute ReviewRequestDTO reviewRequest, @PathVariable Long locationId, Model model) {


        reviewRequest.setLocationId(locationId);
        model.addAttribute("username", userService.findById(userId).get().getUsername());
        model.addAttribute("reviewRequest", reviewRequest);

        return "/review";
    }

    @PostMapping(value = "/findLocation/location/{locationId}/review")
    public String createReview(@CookieValue(name = "userId", required = true) Long userId, @ModelAttribute ReviewRequestDTO reviewRequest,
                               @PathVariable Long locationId, Model model) {

        reviewRequest.setUserId(userId);
        reviewRequest.setUsername(userService.findById(userId).get().getUsername());
        reviewService.join(reviewRequest);

        return "redirect:/findLocation/location/{locationId}";
    }

    @GetMapping(value = "/home/myReview")
    public String getMyReview(@CookieValue (name="userId", required = true) Long userId, Model model) {

        List<Review> reviewList = reviewService.findAllByUserId(userId);

        model.addAttribute("reviewList", reviewList);
        return "/myReview";
    }

    @GetMapping("/findLocation/location/{locationId}/review/{reviewId}/edit")
    public String editReviewPage(@CookieValue(name = "userId", required = true) Long userId,
                                 @PathVariable Long locationId,
                                 @PathVariable Long reviewId,
                                 Model model) {

        Review review = reviewService.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));


        if (!review.getUser().getId().equals(userId)) {
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
    public String editReview(@CookieValue(name = "userId", required = true) Long userId,
                             @PathVariable Long locationId,
                             @PathVariable Long reviewId,
                             @ModelAttribute ReviewRequestDTO reviewRequest) {
        reviewRequest.setUserId(userId);
        reviewService.updateReview(reviewId, reviewRequest);

        return "redirect:/findLocation/location/" + locationId;
    }

    @PostMapping("/findLocation/location/{locationId}/review/{reviewId}/delete")
    public String deleteReview(@CookieValue(name = "userId", required = true) Long userId,
                               @PathVariable Long locationId,
                               @PathVariable Long reviewId) {
        try {
            reviewService.deleteReview(reviewId, userId);
            return "redirect:/findLocation/location/" + locationId;
        } catch (IllegalArgumentException e) {

            return "redirect:/findLocation/location/" + locationId;
        }
    }


}



