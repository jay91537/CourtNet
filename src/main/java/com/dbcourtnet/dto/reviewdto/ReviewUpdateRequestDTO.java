package com.dbcourtnet.dto.reviewdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUpdateRequestDTO {

    private Long rating;
    private String text;
    private Long userId;

}
