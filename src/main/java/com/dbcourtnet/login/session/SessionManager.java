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

    // store는 세션 DB의 역할 수행
    private static Map<String, Long> store = new ConcurrentHashMap<>();

    public void createSession(Long value, HttpServletResponse response) {

        String token = UUID.randomUUID().toString();
        store.put(token ,value);
        Cookie cookie = new Cookie(SessionConst.sessionId, token);
        response.addCookie(cookie);

    }

    public Long getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request);

        if(sessionCookie == null) {
            return null;
        }

        return store.get(sessionCookie.getValue());
    }

    public Cookie findCookie(HttpServletRequest request) {
        if(request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(SessionConst.sessionId))
                .findFirst()
                .orElse(null);
    }

    public void sessionExpire(HttpServletRequest request) {

        Cookie sessionCookie = findCookie(request);
        if(sessionCookie != null) {
            store.remove(sessionCookie.getValue());
        }

    }
}
