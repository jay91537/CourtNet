package com.dbcourtnet.location.dto;

import com.dbcourtnet.location.Location;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationRequestDTO {

    private String locationName;

    private String address;

    private Long courtNum;

    public Location toEntity() {
        return Location.builder()
                .locationName(this.locationName)
                .address(this.address)
                .courtNum(this.courtNum)
                .build();
    }

}
