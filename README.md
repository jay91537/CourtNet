# CourtNet
전국의 농구인들이 농구를 할 수 있는 구장을 찾아보고 리뷰할 수 있는 서비스 입니다.

Java 21, SpringBoot 3.4.0, Mysql 9.0.1, Spring Data JPA

Thymeleaf를 이용해 클라이언트, 서버 통합 개발

---

### 2025.01.01

Thymeleaf를 이용한 Server-Side Rendering에서 협업 및 배포를 위해 rest api를 만족하도록 리팩토링 (branch이름 : rest/api)

## 로그인 기능
1. 쿠키 + 세션 이용
- 서버에 세션DB를 직접 생성하고 이를 sessionManager를 통해 관리, 세션ID를 쿠키에 담아 Servlet객체가 이를 전달
- 위 과정을 간소화 -> Httpsession을 이용해 세션DB를 직접 생성하지 않고, Spring에서 제공하는 @SessionAttribute 어노테이션을 통해 다른 페이지에서 사용자에 대한 인증과정을 간소화

2. Jwt 이용
- Spring Security를 사용하지 않고 jjwt라이브러리만 이용

