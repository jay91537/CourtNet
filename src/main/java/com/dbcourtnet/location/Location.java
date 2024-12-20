package com.dbcourtnet.location;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue
    private Long id;

    private String locationName;

    private String address;
}
