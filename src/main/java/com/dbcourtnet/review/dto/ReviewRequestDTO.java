package com.dbcourtnet.review.dto;

import com.dbcourtnet.location.Location;
import com.dbcourtnet.review.Review;
import com.dbcourtnet.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class ReviewRequestDTO {

    private Long userId;

    private Long locationId;

    private String username;

    private Long rating;

    private String text;

    private String date;

    public Review toEntity(User user, Location location) {
        return Review.builder()
                .username(this.username)
                .date(this.date)
                .rating(this.rating)
                .text(this.text)
                .user(user)
                .location(location)
                .build();
    }

}
