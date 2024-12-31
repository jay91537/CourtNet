# CourtNet
전국의 농구인들이 농구를 할 수 있는 구장을 찾아보고 리뷰할 수 있는 서비스 입니다.

## 로그인 기능
1. 쿠키 + 세션 이용
- 서버에 세션DB를 직접 생성하고 이를 sessionManager를 통해 관리, 세션ID를 쿠키에 담아 Servlet객체가 이를 전달
- 위 과정을 간소화 -> Httpsession을 이용해 세션DB를 직접 생성하지 않음
- 2차 간소화 -> Spring에서 제공하는 @SessionAttribute 어노테이션을 통해 다른 페이지에서 사용자에 대한 인증과정을 간소화
