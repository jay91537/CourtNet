package com.dbcourtnet.location;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Id
    @GeneratedValue
    private Long id;

    private String locationName;

    private String address;

    public Location(final Long id, final String locationName, final String address) {
        this.id = id;
        this.locationName = locationName;
        this.address = address;
    }
}
