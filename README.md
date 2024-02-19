

# ⚾️ Togeball - 야구 직관 메이트 매칭 플랫폼

<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/7c125c0d-01d6-4352-82f1-9656910d1318" width="500">


## 목차
- 팀소개
- 역할 분담
- 프로젝트 소개
- 기술스택
- 핵심 기능
- 역할별 상세 보기
- 기타 산출물

## 팀소개

```
    안녕하세요? 5명의 I와 1명의 E가 모인 E와 I들 입니다.

    팀장 및 프론트 : 이운재
    프론트리더: 윤진아
    백엔드리더: 오형택
    인프라리더 및 프론트: 조권호
    백엔드 : 양유경
    백엔드 : 조아영
```

## 역할 분담

### 각자 맡은 핵심 기능
- 이운재 : 매칭 페이지, 마이페이지
- 윤진아 : 채팅 페이지, 알림페이지
- 조권호 : 인프라 구성, 프로필 수정페이지
- 오형택 : 알림 시스템, 채팅 시스템
- 양유경 : 매칭 알고리즘, 유저 API
- 조아영 : 매칭 시스템

## 프로젝트 소개

```
    함께 직관하고 싶은 사람들을 위한 직관 메이트 매칭 서비스 투게볼 !  
    
    같이보는 재미를 제공하기 위해 저희 투게볼이 딱 맞는 직관 메이트를 구해 드립니다. 

    '야구, 같이 보면 더 재밌잖아요'  슬로건 아래  매칭을 기반으로 하는 서비스를 제공합니다.
```

## 기술스택

### 프론트엔드
| TypeScript |    React   |  StyeldComponent   |  Zustand |
| :--------: | :--------: | :------: | :-----: |
|   <img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/65be6d99-38d9-4c5c-8378-e5264979db90" width="100" height="100">    |   <img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/45501075-a1af-44a8-8f3b-e720fd6c5850" width="100" height="100">    | <img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/da406de6-1b84-4c37-a772-13d9ec46b380" width="100" height="100"> | <img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/277a29c1-bd8f-44e3-ba0a-20a7e1d4b1f4" width="100" height="100"> |

### 백엔드

| SpringBoot |   MariaDB  |  MongoDB   |  Redis | RabbitMQ | 
| :--------: | :--------: | :------: | :-----: | :--------: |
|   <img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/08b644fe-ced1-476e-b8de-95f5b6a96d71" width="100" height="100">    |   <img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/2d634556-065a-4933-94f0-d7f91bcdae47" width="100" height="100">    | <img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/b0b78ab1-5bf9-47de-b86e-72a21f9941c7" width="100" height="100"> | <img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/41f79e49-d7f0-41a0-80b5-cf33c2cd623c" width="100" height="100"> | <img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/686ccff0-c169-4d66-9d81-484706632ef7" width="100" height="100"> |

### 인프라


| Jenkins |   Docker  |  nginx   |  prometheus | Grafana | 
| :--------: | :--------: | :------: | :-----: | :--------: |
|   <img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/dbd32f39-b91a-4731-8b77-62a58b5c1360" width="100" height="100">   | <img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/9b62f9ed-2ff2-419e-8038-0da3ff34112f" width="100" height="100">    | <img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/c3804c67-0589-4de9-acc2-814fc10bf15c" width="100" height="100"> | <img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/a2bc922a-a409-4668-8311-339d81ee2c32" width="100" height="100"> | <img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/b2f01ead-540f-4f68-a412-54344447d436" width="100" height="100"> |



## 핵심 기능

### 매칭 시스템
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/7be63b2a-89cc-4560-b9a8-a4be1cab0325" width="1000">

- 매칭 
    - 프론트: 웹소켓을 이용한 통신, d3를 이용한 데이터 시각화 및 인터랙티브웹 구현
    - 백 : 유사도를 이용한 매칭 알고리즘 설계


### 채팅 시스템

<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/33d7e6bd-f4a6-46c8-beba-8b53771632cf" width="1000">

- 채팅
    - 프론트: 웹소켓과 stomp 프로토콜을 이용한 통신, 이미지 전송 가능
    - 백: 채팅 서버 구현


## 역할별 상세 보기

### 프론트

#### 매칭 시스템


<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/001bece4-b48f-4b59-9681-506651dde4f0" width="1000">

- 매칭 클릭 시 매칭을 시도하는 사람들과의 사용자 태그 기반 매칭 적용
- 상위 6개의 태그 애니메이션으로 표시
- 스케줄링 시간에 따라 2~6인으로 구성된 채팅방 생성

<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/78273af4-8eb0-4f23-a8bc-777931f929cc" width="500">
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/cd6e3825-51a3-4956-9509-abf10430648e" width="500">

- Chat GPT 이용하여 태그들 조합의 적절한 채팅방 이름 생성

#### 모집 채팅방

<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/5bca04ca-fff7-4c55-95a1-ba7a8a574f6f" width="1000">

- 현재 활성화 된 채팅방 목록 제공
- 경기 일자, 응원팀에 맞는 채팅방 찾는 필터 기능 제공
- 페이지네이션 활용

#### 채팅방 생성

<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/5bfa9617-e369-4aac-8dbc-fb7fb83ca260" width="1000">

- 커스텀 주별 달력 활용하여 경기 선택
- 경기에 맞는 응원팀 항목 제한
- 사용자에 맞는 태그 입력 가능

#### 채팅방

##### 게임 채팅방 참여
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/cda34057-3b63-4673-afc0-d49b39446fd6" width="1000">

- 대화 참가자 모두 표시

#### 나의 채팅방 확인
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/47f7d101-c302-43b6-9106-63c735192659" width="1000">

- 나의 채팅방 목록 확인 및 채팅방 개별 알림
- 최근 메시지 확인, 클릭 시 채팅방 이동

#### 로그인 및 회원가입

- 회원가입
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/025d77aa-5f2c-4646-8ac4-0f053a71dedd" width="1000">

- 로그인
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/4f778ef4-a455-46a8-959a-99175d8e3917" width="400">
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/778a30ce-01ff-45b2-8bd7-0e8b9490f690" width="400">


#### 경기 캘린더

- 월별/주별 캘린더
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/bf2fadb0-468b-46e8-92ad-97afe4dc1b4b" width="1000">


### 백엔드

#### Swagger API 확인

- 채팅방 API
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/2e039d8d-fec4-4c3d-add7-930a7552a234" width="800">

- 매칭, 태그 API
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/0a2bec64-12c3-471b-8550-a2675b3c5b7d" width="800">

#### Postman API 확인

- 게시판, 리그, 매칭, 알림 API
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/825eaa21-bf5f-446c-aded-e3fb3fc156ad" width="250">

- 유저 API
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/6dee6b5f-f061-46e1-b397-b104a6026d25" width="250">

- 인증 API
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/50cb85ef-e4bd-4505-944e-a57dd1256707" width="250">

- 채팅방 API
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/d4513bfc-54fe-496c-91f1-eb0c4eae616b" width="250">

- 태그 API
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/fa931c2c-7931-4094-9387-d850908cf3ae" width="250">

### 인프라

#### 아키텍처 설계

<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/878ae586-2a06-4467-bdc8-9df15e5cceff" height="400">

#### 모니터링 구현

- 시스템 모니터링
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/489af0d6-d4d6-48c9-97f2-77d54eb54ca7" height="600">


- 젠킨스 모니터링
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/872946bd-ba96-42ae-b5ae-0d8e2a384c32" height="600">


- 엔진엑스 모니터링
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/0e050532-7f65-40af-860b-829ec19d6ff9" height="600">

## 기타 산출물


#### ERD
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/badddecc-1f75-4931-9ed8-fb31bd09961c" width="1000">

#### Figma 화면 설계서
<img src="https://github.com/HyeongtaekOh/togeball/assets/86342741/4731059a-6787-4b7d-8dbe-9c39fe4ce188" width="1000">


#### 회고

이운재: 좋은 팀원들들 만나 프로젝트 할 수 있어서 좋았습니다. 개인적으로 팀원 덕분에 많은 성장을 했으며,  다음에도 기회가 된다면 같이 프로젝트를 하고 싶습니다. 좋은 경험이였습니다.

오형택: 처음 도전해보는 것들이 많았는데 많은 성장을 했다는 생각이 들면서도 아쉬움도 많이 남는 건 어쩔 수 없는 것 같네요,,
정신없었지만 즐거웠고 보람찼습니다!

윤진아: 좋은 팀원들과 짧은 기간 안에 기획부터 개발까지 경험할 수 있어 좋았습니다. 백엔드와 협업하면서 부족함도 많이 느꼈지만 성장도 많이 할 수 있었던 프로젝트 였습니다.

양유경: 실력있고 열정있는 팀원들과 함께 하게 되어 영광이었습니다! 시간이 더 있었으면 어땠을까 하는 아쉬움도 있지만 많이 성장할 수 있는 좋은 프로젝트 경험이었습니다.

조권호: 훌륭한 팀원들과 함께 프로젝트를 할 수 있어서 많이 성장할 수 있었습니다. 인프라를 구성하면서 발생하는 오류들에 대해 프론트엔드와 백엔드와의 협업이 정말 중요하다고 생각하였고 좋은 경험이었습니다.

조아영: 멋진 팀원분들 덕분에 많은 것을 배우고 성장할 수 있었던 뜻깊고 알찬 시간이었습니다!
