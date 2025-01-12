package com.dbcourtnet.controller;

import com.dbcourtnet.court.CourtService;
import com.dbcourtnet.dto.reviewdto.ReviewResponseDTO;
import com.dbcourtnet.dto.reviewdto.ReviewUpdateRequestDTO;
import com.dbcourtnet.location.LocationService;
import com.dbcourtnet.login.session.SessionConst;
import com.dbcourtnet.review.Review;
import com.dbcourtnet.review.ReviewService;
import com.dbcourtnet.dto.reviewdto.ReviewRequestDTO;
import com.dbcourtnet.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final LocationService locationService;
    private final UserService userService;
    private final CourtService courtService;

    // 리뷰 생성
    @PostMapping("/findLocation/{locationId}")
    public ResponseEntity<Void> createReview(
            @PathVariable Long locationId,
            @RequestBody ReviewRequestDTO reviewRequest,
            HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");

        if(userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        reviewRequest.setLocationId(locationId);
        reviewRequest.setUserId(userId);
        reviewRequest.setUsername(userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."))
                .getUsername());

        Long reviewId = reviewService.join(reviewRequest);
        return ResponseEntity.created(URI.create("/api/findLocation/" + locationId + "/" + reviewId)).build();

    }

    // 내 모든 리뷰 조회
    @GetMapping("/myReview")
    public ResponseEntity<List<ReviewResponseDTO>> getMyReviews(
            HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");

        if(userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        List<Review> reviews = reviewService.findAllByUserId(userId);

        List<ReviewResponseDTO> response = reviews.stream()
                .map(ReviewResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("findLocation/{locationId}/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long locationId,
            @PathVariable Long reviewId,
            HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");

        if(userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        reviewService.deleteReview(reviewId, userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/findLocation/{locationId}/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> updateReview(
            @PathVariable Long locationId,
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateRequestDTO reviewUpdateRequest,
            HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");

        if(userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        reviewUpdateRequest.setUserId(userId);
        reviewService.updateReview(reviewId, reviewUpdateRequest);

        return ResponseEntity.ok().build();
    }


}



