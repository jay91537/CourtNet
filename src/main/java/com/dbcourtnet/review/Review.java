package com.dbcourtnet.review;
import com.dbcourtnet.location.Location;
import com.dbcourtnet.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private Long rating;

    private String text;

    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locationId")
    private Location location;

}
