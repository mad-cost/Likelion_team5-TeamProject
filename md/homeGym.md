## 배포 과정 간략히 보기 👀
* 배포 과정
  1. 스프링부트 프로젝트에서 jar파일을 생성 (빌드하기)
  2. 생성한 jar파일을 도커 이미지 파일로 만든 후 도커 허브 리포지토리에 push해준다.
  3. EC2에서 도커 허브 리포지토리를 통해 이미지 파일을 pull받고
  4. pull받은 이미지 파일을 명령어를 통해 컨테이너로 실행한다.
     ![배포 과정](/img/num6.png)
<hr>

* Dockerfile 생성하기
  1. main(Master) 브랜치로 이동
  2. Dockerfile 생성
        ![Dockerfile 생성](/img/num13.png)
  3. 명령어를 통해서 jar파일 만들어주기
    > ./gradlew bootJar  

<hr>

* Docker 설치 <br>
  * `로컬 컴퓨터`와 `EC2` 각각에 도커를 설치해 줘야 한다
  >우분투에서 도커 설치하기
  >> * 업데이트 <br>
  >> sudo apt-get update -y  <br>
  >> * HTTP 패키지 설치  <br>
  >> sudo apt install apt-transport-https ca-certificates curl software-properties-common  <br>
  >> * docker repository 접근을 위한 gpg 키 설정  <br>
  >> curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -  <br>
  >> * docker repository 등록  <br>
  >> sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu focal stable"  <br>
  >> * 다시 업데이트  <br>
  >> sudo apt update  <br>
  >> * Docker 엔진 설치  <br>
  >> sudo apt install docker-ce  <br>
<hr>

* Docker Hub
  * https://hub.docker.com 에서 로그인 후 리포지토리 생성하기
  1. 도커 이미지를 중앙에서 관리하고 배포하기 위한 플랫폼
  2. Docker Hub를 통해 이미지를 push하고 pull해준다

<hr>

* Docker Image <br> 
  * 도커 이미지 만드는 명령어
    > docker build -t [도커아이디/리포지토리이름] <br>
     ex) docker build -t dockerjun123/homegym .
  * 생성된 이미지 확인   
    > docker images
<hr>

* Docker Hub에 Push하기
  * 도커 로그인 해주기
    > docker login
  * 이미지를 도커 허브 리포지토리에 push
    > docker push dockerjun123/homegym

    ![도커 허브](/img/num5.png)
<hr>

* EC2에서 이미지 pull 받기 <br>
  * EC2 ip에 접속하기
    > ssh -i {키페어 이름} ubuntu@<퍼블릭IPv4> <br>
    > ex) ssh -i aws_homeGym.pem ubuntu@3.34.195.158 <br>
  * [참고] NCP에서 SSH접속 방법 <br>
    > ssh root@{서버 접속용 공인 IP} -p <외부 포트> <br>
    > ex) ssh root@106.10.43.168 -p 1024
  
  * 도커 허브 리포지토리에 있는 이미지 pull받기
    > docker pull dockerjun123/homegym
  * 생성된 이미지로 컨테이너 실행
    > docker run -d -p 8080:8080 dockerjun123/homegym
  * 실행 중인 컨테이너 확인해보기
    * `mysql`과 `redis`도 함께 실행 중인 모습을 볼 수 있다 <br>
      > docker ps

      ![실행중인 컨테이너](/img/num14.png)
<hr>

* Nginx 설치 및 보안 그룹 설정
  * 동시 접속 처리에 특화된 웹서버
  * 클라이언트로부터 HTTP요청을 받아 서버로 요청을 전달한다
  * Nginx 설치
    > * nginx서버 프로그램 설치 <br>
    sudo apt-get install nginx <br>
    >* nginx 실행 <br>
    sudo systemctl start nginx <br>
  * nginx 실행 확인 <br>
    * [참고] Nginx의 기본 포트는 80번이다
    * active가 녹색으로 뜨면, nginx실행 성공 <br>
      > sudo systemctl status nginx <br>
  
    ![Nginx](/img/num15.png)
    
  * 주소창에 ip 주소를 넣으면 Nginx의 첫 페이지를 볼 수 있다 <br>
  ![Nginx](/img/num16.png)
  
* ### HTTP요청을 서버로 전달하기
###### Nginx 설정
```java
  server {
    listen 80;
    server_name <서버 ip 주소>;

    location = /favicon.ico { access_log off; log_not_found off; }

    location / {
        include proxy_params;
        proxy_pass http://localhost:8080;
    }

  }
```
  1. `<서버 ip 주소>` 로 보내지는 HTTP 요청을 컴퓨터 내의 `8080` 포트로 전달하는 설정 파일이다
  2. 여기의 <서버 ip 주소> 를 인스턴스의 IP 주소로 변경하고, `/etc/nginx/sites-enabled/` 폴더에 넣어준다
  3. 아래 명령을 실행하면 해당 폴더로 이동 <br>
  > cd /etc/nginx/sites-enabled/
  4. `vi` 명령을 이용하면 파일을 생성할 수 있다
  > sudo vi spring_boot
  5. Nginx 설정파일을 붙여넣고 `:wq`로 저장해주기
  6. systemctl을 이용해 Nginx를 재시작하기.
  > sudo systemctl restart nginx.service
  7. 이후 주소창에 `:8080 을 제외`하고 IP 주소를 입력하면 된다
<hr>

* Gabia 도메인 연결 https://www.gabia.com
  * 컴퓨터의 IP주소는 인간 친화적이지 않으므로, 인간 친화적인 DNS주소를 이용한다
    ![가비아](/img/num8.png)
  1. `My가비아`페이지로 이동
  2. DNS 관리툴에서 사용하고자 하는 도메인 설정으로 이동
  3. `레코드 수정`버튼을 클릭하여 레코드 추가
  4. 도메인이 실제로 어떤 IP주소를 나타내는지를 알려주는 `A타입 레코드`와, <br>
  다른 도메인과 동일한 도메인임을 나타내는 `CNAME타입 레코드`를 `추가`해 준다 <br>
     ![가비아DNS](/img/num17.png)
* Nginx 재설정  
  1. ip주소가 바꼈으므로 `server_name`부분을 `가비아에서 설정한 도메인`으로 변경해준다 [이동하기](#Nginx-설정)
  2. Nginx 재실행
    > sudo systemctl restart nginx.service

  ![가비아 도메인](/img/num9.png)
<hr>

* HTTPS 적용하기
  * HTTPS는 HTTP 통신을 하되, TLS를 이용해 그 데이터를 암호화 하여 주고받는 것을 의미한다
  * HTTPS 적용이 끝나기 전에는 사용자가 비밀번호를 제출하는 등의 기능은 공개적으로 제공하지 않는것이 안전하다
* CerBot
  * 무료 SSL인증서를 Nginx에 바로 적용시켜주는 Certbot 이라는 소프트웨어를 사용 
* Cerbot 설치하기
  * certbot은 apt가 아닌 `snap`이라는 다른 소프트웨어 관리 도구를 이용해 설치를 한다
    > * 명령어 순서대로 입력하기 <br> 
        sudo snap install core; sudo snap refresh core <br>
        sudo snap install --classic certbot <br>
        sudo ln -s /snap/bin/certbot /usr/bin/certbot # 바로가기를 생성 <br>
  * `--nginx` 옵션과 함깨 실행합니다.
    > sudo certbot --nginx
  * 이 후 사용자 동의를 구하는 것들
    1. 이용약관 : `y`
    2. 정기적 수신 동의 : `n`
    3. TLS를 적용할 도메인 : `1`
      > ex) 1: homegym.site
    4. ###### 인증서를 받아와서 적용하는 과정
  
  1. 실행이 끝나면 일시적으로 자신의 사이트에 접속이 불가해지는데,
  2. 이는 HTTPS가 80이 아닌 443 포트로 요청이 보내지기 때문이다 
  3. 80 과 마찬가지로 `443도 보안 규칙에 포함`시켜야 한다
  * `EC2 프리티어 용량초과`로 인해 NCP 회원가입시 10만원 무료 크레딧을 이용하였다
     ![NCP인스턴스](/img/num18.png)
  * 실행이 끝나면 브라우저로 접속해 HTTPS가 적용된 상태를 확인 가능하다.
    ![CerBot적용](/img/num10.png)
  <hr>
  
  ### 배포 성공!! 🙆
  ![CerBot적용](/img/num12.png) 
  ![CerBot적용](/img/num7.png)
