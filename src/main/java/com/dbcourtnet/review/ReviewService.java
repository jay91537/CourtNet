package com.dbcourtnet.review;

import com.dbcourtnet.location.Location;
import com.dbcourtnet.location.LocationRepository;
import com.dbcourtnet.review.dto.ReviewRequestDTO;
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
    public void join(ReviewRequestDTO reviewRequest) {

        if(reviewRequest.getUserId()==null){
            throw new IllegalArgumentException("유저가 존재하지 않습니다.");
        }
        if(reviewRequest.getLocationId()==null){
            throw new IllegalArgumentException("구장이 존재하지 않습니다.");
        }

        User user = userRepository.findById(reviewRequest.getUserId()).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        Location location = locationRepository.findById(reviewRequest.getLocationId()).orElseThrow(() -> new IllegalArgumentException("구장이 존재하지 않습니다."));

        Review review = reviewRequest.toEntity(user,location);
        reviewRepository.save(review);
    }

    // 어떤 유저가 쓴 모든 리뷰
    @Transactional
    public List<Review> getAllReviewsByUser (ReviewRequestDTO reviewRequest) {

        if (reviewRequest.getUserId() == null) {
            throw new IllegalArgumentException("유저가 존재하지 않습니다.");
        }

        return reviewRepository.findAllByUserId(reviewRequest.getUserId());
    }


    // 어떤 구장에 대한 모든 리뷰
    @Transactional
    public List<Review> findAllByLocationId (Long id) {

        if (id == null) {
            throw new IllegalArgumentException("장소가 존재하지 않습니다.");
        }

        return reviewRepository.findAllByLocationId(id);
    }

    // 어떤 유저의 특정 구장 리뷰 업데이트
    @Transactional
    public void updateReviewByUser (ReviewRequestDTO reviewRequest) {

        if (reviewRequest.getUserId() == null) {
            throw new IllegalArgumentException("유저가 존재하지 않습니다.");
        }



    }

    // 어떤 유저의 특정 구장 리뷰 삭제 기능
    @Transactional
    public void deleteReviewByUser (ReviewRequestDTO reviewRequest) {

        if (reviewRequest.getUserId() == null) {
            throw new IllegalArgumentException("유저가 존재하지 않습니다.");
        }



    }

    // 어떤 유저의 모든 구장 리뷰 삭제 기능
    @Transactional
    public void deleteAllReviewsByUser (ReviewRequestDTO reviewRequest) {

        if (reviewRequest.getUserId() == null) {
            throw new IllegalArgumentException("유저가 존재하지 않습니다.");
        }

    }


    public List<Review> findAllByUserId(Long userId) {

        if(userId == null){
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }

        return reviewRepository.findAllByUserId(userId);
    }

    @Transactional
    public Optional<Review> findById(Long reviewId) {
        return reviewRepository.findById(reviewId);
    }

    @Transactional
    public void updateReview(Long reviewId, ReviewRequestDTO reviewRequest) {
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
