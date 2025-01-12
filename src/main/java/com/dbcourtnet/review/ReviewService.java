package com.dbcourtnet.review;

import com.dbcourtnet.dto.reviewdto.ReviewUpdateRequestDTO;
import com.dbcourtnet.location.Location;
import com.dbcourtnet.location.LocationRepository;
import com.dbcourtnet.dto.reviewdto.ReviewRequestDTO;
import com.dbcourtnet.user.User;
import com.dbcourtnet.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    // 리뷰 생성
    @Transactional
    public Long join(ReviewRequestDTO reviewRequest) {

        User user = userRepository.findById(reviewRequest.getUserId()).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        Location location = locationRepository.findById(reviewRequest.getLocationId()).orElseThrow(() -> new IllegalArgumentException("구장이 존재하지 않습니다."));

        Review review = reviewRequest.toEntity(user,location);
        reviewRepository.save(review);
        return review.getId();
    }

    // 어떤 구장에 대한 모든 리뷰
    @Transactional
    public List<Review> findAllByLocationId (Long id) {

        if (id == null) {
            throw new IllegalArgumentException("장소가 존재하지 않습니다.");
        }

        return reviewRepository.findAllByLocationId(id);
    }

    @Transactional
    public List<Review> findAllByUserId(Long userId) {

        if (userId == null) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }

        return reviewRepository.findAllByUserId(userId);
    }

    @Transactional
    public Optional<Review> findById(Long reviewId) {
        return reviewRepository.findById(reviewId);
    }

    @Transactional
    public void updateReview(Long reviewId, ReviewUpdateRequestDTO reviewRequest) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));


        if (!review.getUser().getId().equals(reviewRequest.getUserId())) {
            throw new IllegalArgumentException("리뷰 수정 권한이 없습니다.");
        }


        review.setRating(reviewRequest.getRating());
        review.setText(reviewRequest.getText());
    }

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));


        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("리뷰 삭제 권한이 없습니다.");
        }


        reviewRepository.delete(review);
    }

}
