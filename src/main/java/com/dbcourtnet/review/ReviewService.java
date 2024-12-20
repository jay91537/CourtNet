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

    @Transactional
    public void createReview(ReviewRequestDTO reviewRequest) {

        Optional<User> user = Optional.ofNullable(userRepository.findById(reviewRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 유저가 없습니다.")));

        Optional<Location> location = Optional.ofNullable(locationRepository.findById(reviewRequest.getLocationId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 구장이 없습니다.")));

        Review review = reviewRequest.toEntity(user, location);

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
    public List<Review> getAllReviewsByLocation (ReviewRequestDTO reviewRequest) {

        if (reviewRequest.getLocationId() == null) {
            throw new IllegalArgumentException("장소가 존재하지 않습니다.");
        }

        return reviewRepository.findAllByLocationId(reviewRequest.getLocationId());
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

}
