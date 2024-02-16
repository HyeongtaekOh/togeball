

# ⚾️ Togeball - 야구 직관 메이트 매칭 플랫폼

<img src="/uploads/6dcb2dbf8e1efed220d0c5e307b3e6ba/togeball_logo1.png" width="500" height="300">


## 목차
- 팀소개
- 역할 분담
- 프로젝트 소개
- 기술스택
- 메인페이지
- 핵심 기능
- 역할별 상세 보기

## 팀소개


    안녕하세요? 5명의 I와 1명의 E가 모인 E와 I들 입니다.

    팀장 및 프론트 : 이운재
    프론트리더: 윤진아
    백엔드리더: 오형택
    인프라리더 및 프론트: 조권호
    백엔드 : 양유경
    백엔드 : 조아영

## 역할 분담

### 각자 맡은 핵심 기능
- 이운재 : 매칭 페이지, 마이페이지
- 윤진아 : 채팅 페이지, 알림페이지
- 조권호 : 인프라 구성, 프로필 수정페이지
- 오형택 : 알림 시스템, 채팅 시스템
- 양유경 : 매칭 알고리즘, 유저 API
- 조아영 : 매칭 시스템

## 프로젝트 소개

    함께 직관하고 싶은 사람들을 위한 직관 메이트 매칭 서비스 투게볼 !  
    
    같이보는 재미를 제공하기 위해 저희 투게볼이 딱 맞는 직관 메이트를 구해 드립니다. 

    '야구, 같이 보면 더 재밌잖아요'  슬로건 아래  매칭을 기반으로 하는 서비스를 제공합니다.

## 기술스택

### 프론트엔드
| TypeScript |    React   |  StyeldComponent   |  Zustand |
| :--------: | :--------: | :------: | :-----: |
|   <img src="/uploads/296ca86b204eac0daf04aad55a5e6b26/image.png" width="100" height="100">    |   <img src="/uploads/3f21f61ff5941103ebeb216c83c65607/image-1.png" width="100" height="100">    | <img src="/uploads/bc6ee5a8d68f33b8bd193abf623aa3bf/image-2.png" width="100" height="100"> | <img src="/uploads/4e3e91384015b03b8bf66ab093380af7/image-3.png" width="100" height="100"> |

### 백엔드

| SpringBoot |   MariaDB  |  MongoDB   |  Redis | RabbitMQ | 
| :--------: | :--------: | :------: | :-----: | :--------: |
|   <img src="/uploads/b4791806e1d8ca4a32c5f32cd89d0b65/image-4.png" width="100" height="100">    |   <img src="/uploads/296d4d790582241605ecb42ff06b15a8/image-5.png" width="100" height="100">    | <img src="/uploads/233835c6fc35d9f748f5ec72ae86102d/image-7.png" width="100" height="100"> | <img src="/uploads/58867c6d0179bc526726ae7626d83fa8/image-9.png" width="100" height="100"> | <img src="/uploads/c5eccc59c9595aef959e6a69cfc653c6/image-10.png" width="100" height="100"> |

### 인프라


| Jenkins |   Docker  |  nginx   |  prometheus | Grafana | 
| :--------: | :--------: | :------: | :-----: | :--------: |
|   <img src="/uploads/777add1f8b2af7aea06f37755d9a8415/image-11.png" width="100" height="100">   | <img src="/uploads/c22474e95549177dfd16db36243fc789/image-12.png" width="100" height="100">    | <img src="/uploads/9627973f6e2be541052ef266c2bf8020/image-13.png" width="100" height="100"> | <img src="/uploads/7f43b163bf59e392b1214722252c86d3/image-14.png" width="100" height="100"> | <img src="/uploads/fed859911e53bde4872b8100670da8ae/image-15.png" width="100" height="100"> |


## 메인페이지

<img src="/uploads/ce57c18f8bec52f29146afa5cc47acba/image-16.png" width="600" height="400">


## 핵심 기능

### 매칭 시스템
<img src="/uploads/cf0590e512a68efb63b540256f82d50e/image-17.png" width="300" height="200">
<img src="/uploads/3eaff9326fbb81c6223be7de04f80df6/image-18.png" width="300" height="200">

- 매칭 
    - 프론트: 웹소켓을 이용한 통신, d3를 이용한 데이터 시각화 및 인터랙티브웹 구현
    - 백 : 유사도를 이용한 매칭 알고리즘 설계


### 채팅 시스템

<img src="/uploads/14b3dee7bcadfd97d29e99271a3ab7c1/%EB%AA%A8%EC%A7%91%EC%B1%84%ED%8C%85%EB%B0%A9%EC%B0%B8%EC%97%AC.gif" width="500" height="300">

- 채팅
    - 프론트: 웹소켓과 stomp 프로토콜을 이용한 통신, 이미지 전송 가능
    - 백: 채팅 서버 구현


## 역할별 상세 보기

### 프론트

#### 매칭 시스템


<img src="/uploads/4ca196cdfb56e3e9fdbcee893f2759ba/matching.PNG" width="500" height="300">

- 매칭 클릭 시 매칭을 시도하는 사람들과의 사용자 태그 기반 매칭 적용
- 상위 6개의 태그 애니메이션으로 표시
- 스케줄링 시간에 따라 2~6인으로 구성된 채팅방 생성

<img src="/uploads/2ab94611d4bb994e1bf37a266563ad14/matchingmodal.PNG" width="500" height="300">
<img src="/uploads/a648637416590e434a7c2ae498485719/matchingchat.PNG" width="500" height="300">

- Chat GPT 이용하여 태그들 조합의 적절한 채팅방 이름 생성

#### 모집 채팅방


<img src="/uploads/0593ddf2a94c15d6457d2f692984d5f0/image.png" width="500" height="300">

- 현재 활성화 된 채팅방 목록 제공
- 경기 일자, 응원팀에 맞는 채팅방 찾는 필터 기능 제공
- 페이지네이션 활용

#### 채팅방 생성

<img src="/uploads/8a38c24551313b9dc6dd579ad23232be/recruitChatroom.gif" width="500" height="300">

- 커스텀 주별 달력 활용하여 경기 선택
- 경기에 맞는 응원팀 항목 제한
- 사용자에 맞는 태그 입력 가능

#### 채팅방

<img src="/uploads/5322f26a04c751b744dcf69d1441e952/gameChatroom.gif" width="500" height="300">

- 대화 참가자 모두 표시

#### 나의 채팅방 확인
<img src="/uploads/4680a18e1f3990b21d7d7687d4eeda74/alarm.gif" width="300" height="400">

- 나의 채팅방 목록 확인 및 채팅방 개별 알림
- 최근 메시지 확인, 클릭 시 채팅방 이동


### 백엔드

#### Swagger API 확인

- 채팅방 API
<img src="/uploads/39b925c9230cf4218184faea4646d67b/image__7_.png" width="400" height="200">

- 매칭, 태그 API
<img src="/uploads/1842a2aeb09d29ec8141fd9e94efd7c8/image__8_.png" width="400" height="200">

#### Postman API 확인

- 게시판, 리그, 매칭, 알림 API
<img src="/uploads/c89016184ee7c1c269ee4c6e4be3a4a4/post1.PNG" width="200" height="300">

- 유저 API
<img src="/uploads/e5fecafd100cdf4bdd914cfb9fb18350/post2.PNG" width="200" height="300">

- 인증 API
<img src="/uploads/45d2f1225ace9f34aab8a5d0576562ca/post3.PNG" width="300" height="200">

- 채팅방 API
<img src="/uploads/17a2d71501b2298330aeefcbefe0028e/post4.PNG" width="200" height="400">

- 태그 API
<img src="/uploads/1e338a96f1e9770ad113cc5d962b7c2d/post5.PNG" width="300" height="200">

### 인프라

#### 아키텍처 설계

<img src="/uploads/9af32a0aadb0bc9f343859956785f79c/archi.png" width="500" height="300">

#### 모니터링 구현

- 시스템 모니터링
<img src="/uploads/04a5e0fea5de942bf477bb977934309b/node.PNG" width="500" height="300">


- 젠킨스 모니터링
<img src="/uploads/c22c5c25991da292e8dd3aedbb24c9fe/jen.PNG" width="500" height="300">


- 엔진엑스 모니터링
<img src="/uploads/e2d4b44087215aa45301455605559f5e/ngix.PNG" width="500" height="300">


