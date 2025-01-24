package com.dbcourtnet.login.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    private static Map<String, Long> sessionDB = new ConcurrentHashMap<>();

    public void createSession(Long userId, HttpServletResponse response) {

        String token = UUID.randomUUID().toString();
        sessionDB.put(token ,userId);
        Cookie cookie = new Cookie("LOGIN_USER", token);
        response.addCookie(cookie);

    }

    public Long getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request);

        if(sessionCookie == null || !sessionDB.containsKey(sessionCookie.getValue())) {
            return null;
        }

        return sessionDB.get(sessionCookie.getValue());
    }

    public Cookie findCookie(HttpServletRequest request) {
        if(request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("LOGIN_USER"))
                .findFirst()
                .orElse(null);
    }

    public void sessionExpire(HttpServletRequest request) {

        Cookie sessionCookie = findCookie(request);
        if(sessionCookie != null) {
            sessionDB.remove(sessionCookie.getValue());
        }

    }
}
