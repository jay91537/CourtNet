package com.dbcourtnet.login;

import com.dbcourtnet.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<User, Long> {


    boolean existsByLoginId(String loginId);


}
