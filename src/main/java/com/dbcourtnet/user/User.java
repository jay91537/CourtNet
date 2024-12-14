package com.dbcourtnet.user;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String loginId;

    private String password;

    private String username;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public User(final Long id, final String loginId, final String password, final String username, Gender gender) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.gender = gender;
    }
}
