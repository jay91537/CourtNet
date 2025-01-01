package com.dbcourtnet.dto.locationdto;

import com.dbcourtnet.court.CourtTexture;
import com.dbcourtnet.dto.reviewdto.ReviewResponseDTO;
import com.dbcourtnet.location.Location;
import com.dbcourtnet.review.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class LocationResponseDTO {

    private String locationName;

    private Long locationId;

    private String address;

    private List<CourtTexture> courtTextures;

    private List<ReviewResponseDTO> reviews;

    public LocationResponseDTO(Location location) {
        this.locationId = location.getId();
        this.locationName = location.getLocationName();
        this.address = location.getAddress();
    }

    public LocationResponseDTO(Location location, List<CourtTexture> courtTextures, List<Review> reviews) {
        this.locationId = location.getId();
        this.locationName = location.getLocationName();
        this.address = location.getAddress();
        this.courtTextures = courtTextures;
        this.reviews = reviews.stream()
                .map(ReviewResponseDTO::new)
                .collect(Collectors.toList());
    }
}
