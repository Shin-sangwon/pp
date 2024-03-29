## HTTP 리퀘스트 메시지를 작성한다

탐험 여행은 URL 입력부터 시작한다  
URL은 HTTP: // ... 로만 시작하는 것이 아니라 file, mailto 등 여러가지가 존재함 -> 브라우저는 몇 개의 클라이언트 기능을 겸비한 복합적인 클라이언트 소프트웨어 이기 때문.  
URL에 엑세스 대상에 따라서 여러 정보를 담는데, 웹 서버 요청일 경우 파일 경로, 포트 번호 등을 담아서 보낼 수 있다. 모든 URL에서는 앞에 액세스 대상에 따라서 http, ftp등을 명시해야 한다.  
<br>
## 1. 브라우저는 먼저 URL을 해독한다  
프로토콜에 따라 URL을 해독하는 방식이 다르다. 우선 웹 서버에 엑세스 하는 경우를 예시로 설명한다.  
- URL에 **파일 경로**가 포함될 수 있다. www.sangwon.com/home/index.html 과 같은 경우에, 웹 서버 이후 부분부터 생각해보면 home이라는 디렉토리 아래에 index.html에 접근하겠다 라는 의미이다.
- www.sangwon.com/home/ 과 같이 **파일명을 명시하지 않을 수도 있다.** 이는 기본 파일명을 서버에 설정해놓은 경우 생략 가능하다. (보통 index.html ...)
- www.sangwon.com/ 과 같이도 사용할 수 있는데, 이는 루트 디렉토리의 기본 파일을 읽는 것이다.
- www.sangwon.com과 같이 전부 생략할 수도 있는데, 이 경우에는 보통 루트 디렉토리 의 기본 파일을 읽는다. 하지만 www.sangwon.com/introduce 와 같은 경우에는 introduce가 파일 명인지 디렉토리인지 알 수 없기 때문에 파일이 있으면 파일명으로 디렉토리가 있으면 디렉토리 명으로 판단한다.
<br>
## 2. HTTP의 기본 개념
위와 같은 과정을 거쳐 URL을 해독하면, 어디에 액세스해야 하는지가 판명된다. 그 후에 명시된 프로토콜에 따라 액세스를 진행한다.  

---
### HTTP 프로토콜  
클라이언트와 서버가 주고받는 메세지의 내용이나 순서를 정한 것이다.  
**클라이언트 -> 서버** : 리퀘스트(요청), 무엇을(URI) 어떻게(METHOD)를 포함
**서버 -> 클라이언트** : 리스폰스(응답), 결과 데이터와 STATUS CODE를 포함  
URI -> URL or 파일명  
METHOD -> GET, POST, PUT, DELETE ...  
STATUS CODE -> 2XX successful, 3XX Redirection..
---
<br>

## 3. HTTP 리퀘스트 메세지를 만든다  
URL을 해독해 웹 서버와 파일명을 판단하면 브라우저는 이것을 바탕으로 리퀘스트 메세지를 만듦. 포맷이 정해져있음  
1. 첫번째 행 -> 리퀘스트 라인
   - METHOD
   - URI
   - HTTP VERSION
2. 두번째 행 부터 ~ -> 메시지 헤더(Header)
    - 한 행에 한 개의 헤더 필드
    - 리퀘스트의 부가적인 정보
    - 공백 행 전까지가 모두 메시지 헤더
3. 공백 행 이후 -> 메세지 본문(Body)
    - 송신 할 데이터
    - POST인 경우에는 폼에 입력한 데이터 등을 메시지 본문 부분에 씀  

<br>

## 4. 리퀘스트 메시지를 보내면 응답이 되돌아온다.
응답 메세지의 기본적인 포맷도 요청 메세지와 비슷한데, 첫 번째 행이 다르다.  
- 첫 번째 행 -> 요청 실행 결과(Status Code로 작성, 프로그램 등에 실행 결과 전달), 응답 문구(문장으로 작성, 사람에게 실행 결과 전달)  
응답이 온 이후에는 메세지를 추출해서 화면에 표시한다.  
응답이 문장으로만 되어 있다면 이것으로 끝이지만, 영상 같은 것을 담고 있다면 응답 문장에 태그를 추가하여 명시한다.  
태그가 존재하면 일단 공백으로 표시하고, 다시 한 번 웹서버에 액세스 하여 태그에 쓰여있는 영상 파일을 웹 서버에서 읽어와서 공백에 표시한다. (태그에 쓰여있는 경로로 리퀘스트 메세지를 만들어서 보낸다)  
리퀘스트 메세지에 쓰는 URI는 한 개만으로 결정되어 있으므로 여러 파일이 필요하다면 요청을 그에 맞게 매번 보내야 한다.

<br>

## 웹 서버의 IP주소를 DNS서버에 조회한다

### 1. IP 주소의 기본  
HTTP의 메시지를 만들면 다음에는 이것을 OS에 의뢰하여 액세스 대상의 웹 서버에게 송신한다. 브라우저는 URL을 해독하고 HTTP 메시지를 만들지만, 메시지를 네트워크에 송출하는 기능은 없으므로 OS에 의뢰하여 송신한다.  
이때 URL 안에 쓰여있는 서버의 도메인명에서 IP주소를 조사해야 한다. OS에 송신을 의뢰할 때는 도메인명이 아니라 IP주소로 메시지를 받을 상대를 지정해야 하기 때문이다.  
따라서, HTTP 메시지를 만드는 동작의 다음은 도메인명에서 IP 주소를 조사하는 동작이다.  
---
### TCP/IP  
TCP/IP는 **서브넷**이라는 작은 네트워크를 **라우터**로 접속하여 전체 네트워크가 만들어지는 것이라고 생각할 수 있음.  
서브넷 -> 여러 PC가 하나의 허브에 연결되어 있는 단위  
라우터 -> 패킷을 중계하는 장치의 일종  
허브 -> 패킷을 중계하는 장치의 일종  
여기에 'XX동 XX번지' 라는 형태로 네트워크의 주소를 할당한다. 동에 해당하는 번호 -> 서브넷에 할당, 번지에 해당하는 번호 -> 컴퓨터에 할당  
동에 해당하는 번호를 **네트워크 번호**라 하고, 번지에 해당하는 번호를 **호스트 번호**라 하며 이 두 주소를 합쳐서 IP 주소라고 한다.  
라우터가 주소를 보고 이것이 어느 방향에 있는지를 조사하여 그 방향으로 데이터를 중계하는데, 이 중계 동작을 반복하면 애겟스 대상에 데이터가 도착함.  
**요약**  
송신측이 메세지를 보냄 -> 서브넷 안에 있는 허브가 운반(패킷 형태) -> 송신측에서 가장 가까운 라우터까지 도착 -> 이 과정을 반복하여 최종적으로 도착  
### IP  
- 32비트의 디지털 데이터
- 8비트(1바이트)씩 점으로 구분하여 10진수로 표기
- 위의 것 만으로는 어느 부분이 네트워크 번호인지 호스트 번호인지 알 수 없음. 단순히 네트워크 번호와 호스트 번호 두 가지를 합쳐서 32비트로 한다는 것만 결정되어 있기 때문
- 그러므로 이 내역을 나타내는 정보를 필요에 따라 IP주소에 덧붙임 --> 이 정보를 '넷마스크' 라고 함
- 넷 마스크 번호도 32비트 부분의 디지털 데이터, 왼쪽에 1이 나열되고 오른쪽에 0이 나열된 값
- 넷마스크가 1인 부분은 네트워크 번호, 0인 부분은 호스트 번호
- 이렇게 표기하면 너무 길어지므로, 한 부분의 비트 수를 10진수로 나타내고 IP주소에 오른쪽에 병기할 수도 있음. EX) 10.11.12.0/24
- 호스트 번호가 모두 0이거나 1인 경우는 특별한 의미이다. 모두 0 : 서브넷 자체, 모두 1 : 서브넷 기기 전체에 패킷을 보내는 '브로드캐스트'를 나타냄  
---
### 2. 도메인 명과 IP 주소를 구분하여 사용하는 이유  
TCP/IP의 네트워크를 IP 주소로 통신 상대를 지정하므로 IP 주소를 모르면 상대에게 메세지를 전달할 수 없음. 하지만 숫자로 이루어진 IP 주소는 기억하기가 어려워 도메인 명을 사용함.  
--> IP 주소를 입력해도 정상적으로 동작함  
도메인명 만으로 통신을 하게 된다면, 주소에 대한 바이트가 일정하지 않고 지나치게 커질 가능성도 존재함 : 데이터를 운반하는 동작에 더 많은 시간이 걸리면서 네트워크의 속도가 느려짐  
따라서 DNS를 사용하는데, DNS는 IP <-> 도메인 간의 변환을 제공해주는 것임. (서버명과 IP 주소를 대응)  

### 3. Socket 라이브러리가 IP 주소를 찾는 기능을 제공한다.
IP 주소를 조사하기 위해 DNS 서버에 리퀘스트를 보내고, 리스폰스를 받으면 된다.  
이 것을 수행하기 위해, Socket 라이브러리에 있는 DNS 리졸버에 요청하여 IP 주소를 조회할 수 있음.  
**Socket 라이브러리**  
네트워크의 기능을 호출하기 위한 프로그램 라이브러리.  

### 4. 리졸버 내부의 작동  
1. 네트워크 어플리케이션이 리졸버를 호출하면 제어가 리졸버의 내부로 넘어감(메소드를 타고 들어가는 과정이라고 생각?).
2. 제어가 넘어가면, DNS 서버에 문의하기 위한 메세지를 만들어서 보냄.
3. 이 때, 메세지 송신 동작은 리졸버가 수행하는 것이 아니라 OS 내부에 포함된 **프로토콜 스택**을 호출하여 실행을 의뢰함.
4. LAN 어댑터를 통해 메세지가 DNS 서버를 향해서 송신됨.
5. 조회 메세지가 DNS 서버에 도착하고, DNS 서버는 메세지 내용을 토대로 내용을 조사하여 답을 찾음.
6. 답을 찾았다면, 응답 메세지에 써서 클라이언트에게 반송함.
7. 응답이 오면, 프로토콜 스택을 경유하여 리졸버에 건네져서 리졸버가 내용을 해독한 후 어플리케이션에 주소를 전달함.
8. 이로써 리졸버의 동작이 끝나고, 제어가 돌아옴.  
DNS 서버에 메세지를 송신할 때도 DNS 서버의 IP 주소가 필요한데, 이것은 TCP/IP 설정 항목의 하나하나로 컴퓨터에 미리 설정되어 있음.  

<br>

## 전 세계의 DNS 서버가 연대한다.  

### 1. DNS 서버의 기본 동작  
DNS 서버의 기본 동작은 클라이언트에서 조회 메세지를 받고 조회의 내용에 응답하는 형태로 정보를 회답하는 일이다. 조회 메세지에는 3개의 정보가 포함되어 있다.  
- 이름 : 서버나 메일 배송 목적지(메일 주소에서 @ 뒷부분의 이름)와 같은 이름
- 클래스 : 인터넷 의외의 네트워크까지 고려하여 만든 기능이지만, 지금은 인터넷 의외에 네트워크는 없으므로 항상 인터넷을 나타내는 'IN'이 표기
- 타입 : 어떤 종류의 정보가 지원되는지를 나타냄. ex ) A -> 이름에 ip 주소가 지원, mx : 이름에 메일 배송 목적지가 지원. 이 타입에 따라 클라이언트에 회답하는 정보의 내용이 달라짐.  
<br>
클라이언트가 위의 정보를 포함한 조회 메세지를 DNS에 보내면, DNS 서버는 등록된 정보를 찾아서 모두 일치하는 것을 찾는다. 모두 일치하는 것을 찾았다면, 여기에 등록되어 있는 IP 주소 등을 회신함.  
--> 웹 서버에는 www를 붙인 것이 많지만, 그냥 최초의 구조여서 많이 사용하는 것일 뿐 강제/필수는 아니다. webserver라고 해도 되고 myweb이라 해도 되고... 마음에 드는 이름을 붙이고 A타입으로 dns 서버에 등록하면 그것이 웹 서버의 이름.  
--> dnszi의 A레코드가 이거였구나 ..!!  
IP 주소를 조회할 때는 A(address)라는 타입을 사용하지만, 메일 배송 목적지는 MX라는 타입을 사용한다. 이에 대한 응답은 메일 서버의 우선 순위 + 메일 서버의 이름, 해당 메일 서버의 IP 주소이다.  
DNS 서버는 이 뿐만 아니라 다양한 타입이 존재한다.  
- PTR : 이름을 조사할 때 사용
- CNAME : 이름에 닉네임을 붙이기 위함
- SOA : 도메인 자체의 속성 정보를 등록  

### 2. 도메인의 계층  
인터넷에는 막대한 수의 서버가 있기에 1대의 DNS 서버에서 이것을 관리하는 것은 불가능하다.  
따라서 정보를 분산시켜서 다수의 DNS 서버에 등록하고, 다수의 DNS 서버가 연대하여 어디에 정보가 등록되어 있는지를 찾는 구조이다.  
DNS 서버에 등록된 정보에는 모든 **도메인명** 이라는 계층적 구조를 가진 이름이 붙여져 있음. 계층을 구분하는 기준은 . (점) 이다.  
www.sangwon.co.kr인 도메인이 있다고 가정해본다면, www, sangwon, co, kr이 모두 계층을 가지고 있음.  
이 경우에는 , kr 도메인 하위에 co 도메인 하위에 sangwon 도메인 하위에 www 도메인이 있는 것이다(서브 도메인)  
하나에 도메인에 대한 정보들은 하나의 DNS 서버에서 모두 관리함 --> DNS 서버는 다수의 도메인을 관리할 수 있지만, 같은 도메인인 정보들은 모두 한대의 DNS 서버에 존재함.  
가장 먼저 출연하는 모데인이 서버의 이름, 최하위 서브 도메인이다. (위의 경우 www)  

### 3. 담당 DNS 서버를 찾아 IP 주소를 가져온다  
수 많은 DNS 서버가 존재하기 때문에, 어느 DNS에 내가 원하는 정보가 있는지를 판단해야 함.  
따라서 상위 도메인에 하위 도메인을 등록하는 구조로 이루어져 있음.  
--> www.sangwon.com의 경우 www.sangwon.com이라는 도메인을 담당하는 dns 서버를 sangwon.com의 dns 서버에 등록하고. sangwon.com의 dns 서버를 com 도메인의 dns 서버에 저장하는 방식. 
com이나 kr과 같은 도메인은 최상위 도메인이 아님, 사실 **루트 도메인**이라는 도메인이 그 위에 존재하여 거이에 com, kr과 같은 도메인이 등록되어 있음.

**루트 도메인** 
- 최상위 도메인으로 할당된 IP 주소는 13개이고, 좀처럼 변경되지 않음.
- 따라서, 모든 루트 도메인의 정보를 모든 DNS 서버에 등록할 수 있고, 기본으로 DNS 서버 설정 파일에 등록되어 있음.  
- 가까운 어떤 DNS 서버에서도 루트 도메인 서버로 이동할 수 있음.

www.sangwon.com의 IP 주소 구해보기
1. 설정되어 있는 가장 가까운 DNS 서버로 조회를 요청, 없다면 루트 도메인 서버로 조회 요청
2. 루트 도메인으로 이동, 존재하지 않다면 다음 상위 도메인인 COM 도메인의 DNS 서버의 IP 주소 반송
3. COM 도메인 DNS 서버에 조회 요청
4. 위의 과정을 반복하여 원하는 DNS 서버에 도달
5. IP 주소를 응답받음
6. 가장 가까운 DNS 서버에서 클라이언트에게 응답

### 4. DNS 서버는 캐시 기능으로 빠르게 회답할 수 있다.
현실에 인터넷에서는 한 대의 DNS 서버에 복수 도메인의 정보를 등록할 수 있으므로 반드시 위와 같인 동작한다고 단정할 수 없음.  
현실에는 상위와 하위의 도메인을 같은 DNS 서버에 등록하는 경우도 존재함.  
DNS 서버는 한 번 조사한 이름을 캐시에 기록할 수 있는데, 조회한 이름에 해당하는 정보가 있으면 캐시에서 그 정보를 회답하기 때문에, 역순으로도 찾을 수 있음. (이 방법이 더 편리함)  
존재하지 않는 도메인인 경우도 캐싱하여 빠르게 회답할 수 있음.  
캐시에 정보를 저장한 후 등록 정보가 변경되는 경우도 있으므로, 캐시 안에 저장된 정보는 올바르다고 단언할 수 없음. 따라서 DNS 서버에 등록하는 정보에는 유효 기간을 설정하고, 캐시에 저장한 데이터에 유효 기간이 지나면 캐시에서 삭제한다.

## 프로토콜 스택에 메시지 송신을 의뢰한다.  

### 1. 데이터 송수신 동작의 개요  
IP 주소를 조사했으면 IP 주소의 상대에 메세지를 송신하도록 OS의 프로토콜 스택에 의뢰(위임)한다.  
책에서의 예시인 웹 요청 뿐만이 아니라, 모든 네트워크 동작의 공통인 부분임.  
이러한 데이터 송수신을 할 때에도, Socket 라이브러리를 사용함.  

데이터 송수신 작업의 4단계 요약 (프로토콜 스택)
1. 소켓을 생성함(서버와 클라이언트가 각각 생성) : 데이터가 흐르는 통로의 입구 생성
2. 서버측에 소켓의 파이프를 연결함(접속)
3. 데이터를 송수신
4. 완료 후에 파이프를 분리하고 소켓을 말소(close, 어느 쪽이 해도 무방하지만 보통 정해놓음)
-- > Socket 라이브러리는 어플리케이션으로 부터 위 단계를 실행하도록 위임받고 그대로 프로토콜 스택에 중계하는 역할

### 2. 소켓의 작성 단계  

1. socket을 호출하여 socket을 생성. 반환값으로 디스크립터를 받음(소켓 식별 번호) --> 데이터 송 수신이 동시에 이루어 질 때 여러대의 소켓이 있어야 하는데, 그 때 식별하기 위함
2. connect를 호출하여 서버측의 socket에 접속(디스크립터, 서버의 IP주소, 포트 번호가 있어야 함)
3. write로 데이터 송신
4. read로 데이터 수신
5. close로 소켓을 말소

### 3. 파이프를 연결하는 접속 단계

connect라는 메소드의 인자로(디스크립터, 서버의 IP주소, 포트 번호) 이것들이 필요.  
IP 주소는 네트워크에 존재하는 컴퓨터를 식별할 수 있지만, 컴퓨터에 존재하는 여러 소켓 중에서 어느 것에 연결해야할지 정하기 위해 포트번호가 필요함.  
--> 디스크립터를 사용하면 되는거 아닌가요? 라고 생각할 수 있지만, 디스크립터는 소켓을 만들도록 의뢰한 어플리케이션에 건네주는 것이지 접속 상대에 건네주는 것이 아니므로 상대측에서는 그 값을 모름.  
즉, 디스크립터 : 컴퓨터 한 대의 내부에서 소켓을 식별하기 위해 사용 / 포트번호 : 접속 상대측에서 소켓을 식별하기 위해 사용  
포트 번호는 미리 지정한 규칙에 의해서 서로 알고있는 값임. (웹 : 80, 메일 : 25 ...등등)  
포트 번호가 접속 상대측에서 소켓을 지정하기 위해 사용하는 것이라면, 서버에서도 클라이언트측의 소켓의 번호가 필요할 텐데 이 부분은?  
클라이언트측의 소켓의 포트 번호는 소켓을 만들 때 프로토콜 스택이 적당한 값을 골라서 할당함.  
그리고 이 값을 프로토콜 스택이 접속 동작을 실행할 때 서버측에 통지(2장에서 설명)  

**요약**  
connect를 호출하면 프로토콜 스택이 접속 동작을 실행함. 그리고 상대와 연결되면 프로토콜 스택은 연결된 상대의 IP주소나 포트번호 등의 정보를 소켓에 기록. 이로써 데이터 송수신이 가능해짐  
디스크립터 : 애플리케이션이 소켓을 식별하는 것 
IP주소와 포트번호 : 클라이언트와 서버 간에 상대의 소켓을 식별  

### 4. 메세지를 주고받은 송수신 단계  
소켓이 연결되면 그 다음은 간단하다. 소켓에서 데이터를 송신하면 상대측의 소켓에서 수신한다. 그러면 애플리케이션은 소켓을 직접 다룰 수 있으므로 socket 라이브러리르 통해 프로토콜 스택에 그 일을 의뢰한다.  
이 때, write를 사용한다.  
1. 송신측에서 메모리에 송신 데이터를 준비한다(http request)
2. write를 호출하고, 인자로 디스크립터와 송신 데이터를 지정한다
3. 프로토콜 스택이 송신 데이터를 서버에게 송신한다(소켓에는 연결된 상대가 기록되어 있으므로 디스크립터로 소켓을 지정하면 연결된 상대가 판명되어 그곳을 향해 송신한다)
4. 서버는 수신 동작을 실행하여 받은 데이터의 내용을 조사하고 적절한 처리를 실행하여 응답 메세지를 반송한다.

이번에는 수신이다. 
1. socket 라이브러리의 read를 통해 프로토콜 스택에 수신 동작을 의뢰한다.
2. 수신한 응답 메세지를 저장하기 위해 메모리 영역을 지정하는데, 이 메모리 영역을 **수신 버퍼**라고 부른다.
3. 응답 메세지가 돌아올 때, read가 받아서 수신 버퍼에 저장한다.
4. 수신 버퍼는 애플리케이션 프로그램의 내부에 마련된 메모리 영역이므로 수신 버퍼에 메세지를 저장한 시점에서 메세지를 어플리케이션에 건네준다.

### 5. 연결 끊기 단계에서 송수신이 종료된다.
브라우저가 데이터 수신을 완료하면 송수신 동작은 끝난다. 그 후 Socket 라이브러리의 close를 호출하여 연결 끊기 단계로 들어가도록 의뢰한다. 그러면 소켓 사이를 연결한 파이프와 같은 것이 분리되고 소켓도 말소된다.  

1. 웹에서 사용하는 http 프로토콜에서는 응답 메세지의 송신을 완료했을 때 웹 서버측에서 연결 끊기 동작을 실행하므로(어느 측에서 해도 상관 없음) 먼저 웹 서버측에서 close를 호출하여 연결을 끊음  
2. 그러면 이것이 클라이언트측에 전달되어 클라이언트의 소켓은 연결 끊기 단계로 들어감
3. 그리고 브라우저가 read로 수신 동작을 의뢰했을 때 read는 수신한 데이터를 건네주는 대신 송수신이 완료되어 끊겼다는 사실을 브라우저에 통지
4. 이로써 송 수신이 종료되었다는 사실을 인지했으므로 브라우저에서도 close를 호출
<br>
이것이 HTTP의 본래 동작이다. HTTP 프로토콜은 HTML 문서나 영상데이터를 하나하나 별도의 것으로 취급하여 1개의 데이터를 읽을 때 마다 접속, 송신, 수신, 끊기를 반복한다.  
따라서 하나의 웹 페이지에 영상이 많이 포함되어 있으면 이 과정을 여러 번 반복하는데, 너무 비효율적이어서 HTTP 1.1에서 개선된 방식이 나왔다.  
keep-alive 방식이라고 부르며, 더 이상 송수신할 데이터가 없는 경우에만 소켓을 말소하는 단계에 들어간다.  


