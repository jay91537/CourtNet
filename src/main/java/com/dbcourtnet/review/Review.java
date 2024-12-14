package com.dbcourtnet.review;
import com.dbcourtnet.location.Location;
import com.dbcourtnet.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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

    public Review(final Long id, final String username, final Long rating, final String date, final String text, final User user, final Location location) {
        this.id = id;
        this.username = username;
        this.rating = rating;
        this.date = date;
        this.text = text;
        this.user = user;
        this.location = location;
    }
}
