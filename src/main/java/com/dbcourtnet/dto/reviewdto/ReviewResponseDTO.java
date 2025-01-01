package com.dbcourtnet.dto.reviewdto;

import com.dbcourtnet.review.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDTO {

    private Long reviewId;
    private String username;
    private Long rating;
    private String text;
    private String date;

    public ReviewResponseDTO(Review review) {
        this.reviewId = review.getId();
        this.username = review.getUsername();
        this.rating = review.getRating();
        this.text = review.getText();
        this.date = review.getDate();
    }

}
