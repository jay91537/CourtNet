# 전체 flow
```mermaid
flowchart TD
	시작-->회원가입
	회원가입-->로그인
	시작-->로그인
	로그인-->메인화면
	
	메인화면-->농구장찾기
	농구장찾기-->농구장검색
	농구장검색-->코트정보
	
	메인화면-->현재내위치
  현재내위치-->농구장이라면?
  농구장이라면?-->매칭
	매칭-->알림생성
	
	메인화면-->내정보
```

# 회원가입 / 로그인
```mermaid
flowchart LR
	회원가입화면-->회원가입
```

```mermaid
sequenceDiagram
	participant client
	participant server
	participant db
	client->>server: 로그인 요청
	server->>db: 사용자 조회
	db->>server: 사용자 정보 응답
	server->>client: JWT 토큰 (액세스토큰, 리프레시 토큰) 응답
```
