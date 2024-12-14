package com.dbcourtnet.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    private Long id;

    private String loginId;

    private String password;

    private String username;


    public User(final Long id, final String loginId, final String password, final String username) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.username = username;
    }
}
