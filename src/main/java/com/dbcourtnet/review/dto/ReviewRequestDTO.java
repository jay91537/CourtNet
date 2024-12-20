package com.dbcourtnet.review.dto;

import com.dbcourtnet.location.Location;
import com.dbcourtnet.review.Review;
import com.dbcourtnet.user.User;
import lombok.Getter;

import java.util.Optional;

@Getter
public class ReviewRequestDTO {

    private Long userId;

    private Long locationId;

    private String username;

    private Long rating;

    private String text;

    private String date;

    private User user;

    private Location location;

    public Review toEntity(Optional<User> user, Optional<Location> location) {
        return Review.builder()
                .username(this.username)
                .rating(this.rating)
                .text(this.text)
                .date(this.date)
                .user(this.user)
                .location(this.location)
                .build();
    }

}
