## 📝전문가 방문 PT 서비스 만들기
homeGym은 외부에서의 운동이 어려운 상황이거나(노인, 임산부 등..) 바쁜 일상 속에서 시간을 절약하고 싶은 직장인, <br>
헬스를 결제하고도 잘나가지 않는 고객분들을 위해서 정해진 시간, 정해진 곳으로 전문가가 직접 가정에 방문하여 <br>
건강하고 꾸준한 운동습관을 만들어주는 고객 맞춤형 방문 PT를 제공해 주는 서비스입니다.

## 스택 ⚙
* Spting Boot 3.2.1
* Spring Data JPA
* Spring Security
* Jwt
* Oauth2
* Nginx
* CertBot
* Thymeleaf
* MySql
* Docker
* Redis
* AWS EC2
* NCP (네이버 클라우드 플랫폼)

## 팀원 및 파트 분배 🙋
<img src="img/num1.png">

## 주요 기능 
<img src="img/num2.png">

## ERD
<img src="img/num4.png">

## 내가 만든 페이지 및 기능 정리 💻
`/main` 메인페이지 <br>
> * 인기 강사 - 강사의 상태가 ACTIVE이고, 메달이 Gold인 강사들 중에서 랜덤으로 3명 가져오기 <br>
> * 회원 후기 - 별점이 5점인 리뷰만 가져와서 보여주기 (Pageable 사용)
>```java
>    @Transactional
>    public Page<Review> findAllStarIsFive(int page, int pageSize){
>        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending()); // id를 기준으로 역순으로 정렬
>        return reviewRepository.findAllStarIsFive(pageable);
>    }
>```
`/admin` 관리자 메인 페이지 <br>
> * 유저 목록 - 서비스를 이용중인 모든 유저를 보여주고 유저의 이름, 이메일, 성별, 상태를 알려준다 <br>
> * 강사 목록 - 서비스를 이용중인 모든 강사를 보여주고 강사의 이름, 이메일, 전화번호, 성별, 상태를 알려준다 <br>

`/admin/user/{userId}` 유저 상세 페이지 <br>
> * 유저가 수강 중인 수업을 전부 볼 수 있다<br>
> * 전액환불, 회차환불, 수정하기 버튼 만들어주기
> * 전액 환불 - 유저 프로그램의 `state`를 환불 상태(CANCEL)로 바꿔준다 <br>
> * 회차 환불 - 결제 금액에서 1회당 금액을 계산하여 환불해주고, `state`와 `LocalDateTime`을 수정해준다     
>```java
>    public void refund(Long userProgramId){
>        UserProgram userProgram = userProgramRepository.findById(userProgramId).orElseThrow();
>        // 프로그램 결제 금액
>        int amount = userProgram.getAmount(); 
>        int maxCount = userProgram.getMaxCount(); //최종 회차
>        int count = userProgram.getCount(); // 진행 중인 회차 
>        // 1회당 금액
>        int result = amount/maxCount;
>        // 진행 횟수에 따른 환불 금액
>        result = result * count;
>        userProgram.setAmount(result);
>        userProgram.setState(UserProgram.UserProgramState.FINISH);
>        userProgram.setEndTime(LocalDateTime.now());
>        userProgramRepository.save(userProgram);
>        UserProgramDto.fromEntity(userProgram);
>    }
>```
> * 수정하기 -

`/admin/instructor/accept` 강사 승인 페이지 <br>
> 강사의 `state`가 신청 대기 중(REGISTRATION_PENDING)인 강사를 모두 가져오고, 승인, 거절 버튼 만들어주기 <br>
> * 승인 - 강사의 `roles`를 ROLE_INSTRUCTOR로 바꿔주고, `state`를 ACTIVE로 바꿔준다 <br>
> * 거절 - DB에서 데이터 삭제 <br>

`/admin/instructor/withdraw` 강사 회원 탈퇴 페이지 <br>
> 강사의 `state`가 탈퇴 대기 중(WITHDRAWAL_PENDING)인 강사를 모두 가져오고, 탈퇴 사유 및 승인, 거절 버튼 만들어주기 <br>
> * 승인 - 강사의 `state`를 탈퇴 완료된 강사(WITHDRAWAL_COMPLETE)로 바꿔준다 <br>
> * 거절 - DB에서 삭제되는 것이 아닌, 강사의 데이터 유지 <br>

`/admin/program/creation` 프로그램 신청 페이지 <br>
> 프로그램의 `state`가 생성 대기 중(CREATION_PENDING)인 프로그램을 모두 가져오고, 강사가 어떤 프로그램을 신청했는지 알 수 있다 <br>
> 프로그램 수락하기, 거절하기 버튼 만들어주기 <br>
> * 수락하기 - 프로그림의 `state`를 프로그램 진행 중(IN_PROGRESS)으로 바꿔준다 <br>
> * 거절하기 - DB에서 데이터 삭제 <br>

`/admin/program/modification` 프로그램 수정 페이지 <br>
> 프로그램의 `state`가 수정 대기 중(MODIFICATION_PENDING)인 프로그램을 모두 가져오고, 수정하기 버튼 만들어주기 <br>
> * 수정하기 - 프로그램의 `state`를 프로그램 진행 중(IN_PROGRESS)으로 바꿔준다 <br>

`/admin/program/deletion` 프로그램 삭제 페이지 <br>
> 프로그램의 `state`가 삭제 대기 중(DELETION_PENDING)인 프로그램을 모두 가져오고, 삭제하기 버튼 만들어주기 <br>
> * 삭제하기 - 프로그램의 `state`를 프로그램 삭제 완료(DELETION_COMPLETE)로 바꿔준다 <br>

관리자 페이지의 프론트는 `반응형 웹 템플릿`을 이용하였다






## 간략히 보기 👀
[여기를 눌러 주세요](https://github.com/mad-cost/Likelion_team6/blob/main/md/sixsenses.md "Click")

## 회고 🤔
첫 팀 프로젝트를 하면서 가장 고민했던 점은 '역할 분담과 협업' 이었던 것 같다. <br>
- 역할 <br>
  파트를 분배함에 있어, 기능을 중심으로 나눌지, 페이지를 중심으로 나눌지, 혹은 구현이 어려운 순서부터 차근차근 해결해 나아갈지 갈피를 잡지 못했던 것 같다. <br>
- 협업 <br>
  깃 브랜치를 처음 사용하다 보니 시간을 많이 잡아먹었고, 충돌이 일어날 때마다 당황을 했었지만, 결국엔 하면 할수록 익숙해지는게 깃(Git)인거 같다. <br>
  팀원의 구현하지 못한, 공지사항의 검색 기능을 대신 구현해 줬는데 새로운 기능을 완성했을 때 느끼는 만족감이 나를 성장하는 개발자로 만들어 주는 것 같다.
