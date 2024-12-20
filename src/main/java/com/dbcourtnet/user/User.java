package com.dbcourtnet.user;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String loginId;

    private String password;

    private String username;

    @Enumerated(EnumType.STRING)
    private Gender gender;

}
