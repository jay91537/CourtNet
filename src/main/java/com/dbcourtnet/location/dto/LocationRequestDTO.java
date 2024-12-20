package com.dbcourtnet.location.dto;

import com.dbcourtnet.location.Location;
import lombok.Getter;

@Getter
public class LocationRequestDTO {

    private String locationName;

    private String address;

    public Location toEntity() {
        return Location.builder()
                .locationName(this.locationName)
                .address(this.address)
                .build();
    }

}
