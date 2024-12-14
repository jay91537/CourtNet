package com.dbcourtnet.court;
import com.dbcourtnet.location.Location;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Court {

    @Id
    @GeneratedValue
    private Long id;

    private Long courtNum;

    @Enumerated(EnumType.STRING)
    private CourtTexture courtTexture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locationId")
    private Location location;

    public Court(final Long id, final Long courtNum, final CourtTexture courtTexture, final Location location) {
        this.id = id;
        this.courtNum = courtNum;
        this.courtTexture = courtTexture;
        this.location = location;
    }
}
