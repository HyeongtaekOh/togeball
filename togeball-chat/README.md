# 채팅 애플리케이션

아래 명령어를 통해 필요한 컨테이너 실행 후에 서버 구동
```
    docker-compose up -d
```

클라이언트 샘플 코드는 `sample.html` 참고

** 서버 구동시 `src/main/resources/application.yml` 파일에 AWS S3 관련 액세스 키와 비밀 키를 위해 환경변수 파일 `src/main/resources/env/s3.yml` 내용이 필요한데 원격 저장소에 올릴 수가 없어서 내용이 누락돼있습니다. 사용시에 키 요청하시면 파일 바로 드릴게요!

## 채팅 서버 API 명세서

현재는 본 API 서버와 분리되어 있어서 그냥 채팅 서버만 구동했을 때 테스트 해볼 수 있도록 별도의 인증이나 채팅방 참여 절차 없이 채팅방 접속(웹소켓 연결 + 이전 채팅 데이터 조회), 실시간 채팅 기능만 제공

### 현재 서버 포트는 8081번!

1) 채팅방 접속

### 채팅방 메시지 목록 조회

#### 요청

```
HTTP GET /chat-server/chats/{roomId}
Params: {
    userId: Integer,	    // user 식별자, 현재는 본 API서버와 분리되어 있음
    page: Integer,		// 페이지 번호, 0부터 시작
    size: Integer,		// 한 페이지 크기
}
```

#### 응답

```
{
    content: [
        {
            /*
                type에 따라 렌더링 방식 결정
                "TALK": 일반 텍스트 메시지
                "IMAGE": 이미지 URL, <img> 태그 src로 사용
                "NOTICE": 사용자 입/퇴장 등의 이벤트를 위한 메시지 
            */
            roomId: Integer, 
            senderId: Integer,
            type: String, 		// "TALK", "IMAGE", "NOTICE"
            content: String,
            timestamp: Date
        },
    ],
    numberOfElements: Integer, 	// 조회된 메시지 총 개수
    totalElements: 	  Integer,	// 전체 메시지 총 개수 (페이지 적용 없이 DB에 저장된 전체 개수)
    등등 (console.log로 확인하세요 ㅎㅎ)
}
```    


### 채팅방 WebSocket+STOMP 연결 (SockJS 이용)

(1) Connect

Javascript WebSocket API에서는 연결 요청에 헤더 추가가 안돼서 추후 인증 처리를 위해서는 쿼리 파라미터에 토큰이나 인증 관련 데이터를 담아야함.
현재 쿼리 파라미터 userId 값은 사용되지는 않으나 없으면 웹소켓 연결이 안되게 설정되어있어서 테스트시 url에 포함해야함.

```
var socket = new SockJS("http://{server-host}:{server-port}/chat-server/chat?userId={userId}");
// 위에서 설정한 웹소켓 연결을 STOMP 통신에 사용
var stompClient = Stomp.over(socket);
// 웹소켓 연결
stompClient.connect(
    /* 
        Connect 요청에 사용되는 헤더.
        이 헤더를 이용하면 쿼리 파라미터가 아니라 헤더를 통해 채팅하려는 유저의 인증 절차를 수행할 수 있을 것 같지만, 
        서버에서 해당 Connect 요청의 헤더를 체크하고 유효하지 않으면 연결을 거부하는 것이 현재까지 알아본 것으로는 불가능한 것 같다. 
        그래서 인증 절차는 앞서 다룬 쿼리 파라미터를 이용할 수 밖에 없는 듯
    */
	{},
    /*
        연결 성공시 실행되는 콜백 함수 
        인자는 서버가 전송하는 연결 완료 메시지(STOMP 통신에서는 메시지 단위를 frame이라고 부름)가 들어옴
    */
	function (frame) {} 
);
```

(2) Subscribe

앞선 절차는 서버와의 웹소켓 연결을 한거고, 이제 특정 채팅방에서의 실시간 채팅을 위해 채팅방의 STOMP 엔드포인트를 구독해줘야 한다. 아래 코드를 connect 메서드의 콜백 함수 안에 넣어주면 된다.

```
stompClient.subscribe(
    // 채팅방 구독 엔드포인트
	"/topic/room.{roomId}",
    /*
        subscribe 성공 후, 채널로부터 메시지가 올 때마다 실행될 콜백 함수. 
    	인자는 해당 메시지 프레임이 들어옴. 이 함수에서 메시지 받을 때마다 렌더링 해주면 된다.
    */
	function (message) {},
    // 메시지 큐 관련 옵션. 수정 X
	{'auto-delete': true, 'durable': false, 'exclusive': false}
);
```

2) subscribe 후 채팅

### 텍스트 전송

```
stompClient.send(
	"/pub/chat.{roomId}", 	// 채팅방 전송 엔드포인트
	{},						// 메시지 헤더
    message					// 전송할 메시지, JSON.stringify로 직렬화해서 보내야 함
);
```

### 이미지 전송

웹소켓으로 이미지를 직접 전송하는 것은 권장되지 않는다고 들어서, 이미지 전송 시에는 HTTP 요청을 보내고 서버에서는 이를 받아 이미지를 AWS S3에 업로드 한 뒤, 해당 image의 URL을 담은 메시지 프레임을 만들어 채널에 publish하는 방식을 채택함.

#### 요청

```
HTTP POST /chat-server/chats/{roomId}/images
Body: FormData
```

#### FormData 예시

```	
var formData = new FormData();
formData.append("file", selectedFile);
formData.append("roomId", roomId);
formData.append("senderId", sessionStorage.getItem("userId"));
formData.append("type", "IMAGE");
```

#### 응답

```
204 No Content
```