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
>    // Pageable 사용하기
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
> * 수정하기 - 유저 프로그램의 `count`를 입력한 숫자로 수정해준다<br>

`/admin/instructor/{instructorId}` 강사 상세 페이지 <br>
> 강사가 수강 중인 수업을 전부 볼 수 있다 <br>
> 강사가 모든 프로그램으로부터 벌이들인 총 금액을 볼 수 있다 <br>
> 강사가 각 각의 프로그램으로 벌어들인 총 수익, 이번 달 수익을 볼 수 있다 <br>
> 강사에게 메달을 수여할 수 있다 <br>
> * 메달 수여하기 - Gold, Sliver, Bronze, Unranked 버튼을 눌러서 메달 수여
> ```java
>   // 하나의 form요소에서 '여러가지 input btn'에 대하여 선택한 btn의 값 보내기
>     <form class="d-flex align-items-center justify-content-between" th:action="@{'/admin/instructor/'+${instructor.id}+'/medal'} " method="post">
>         <p class="mb-0 d-flex align-items-center justify-content-between">
>           <code>메달 수여하기</code><a class="me-2"></a>
>             <input type="submit" class="btn btn-warning" value="Gold" name="Gold"><a class="me-1"></a>
>             <input type="submit" class="btn btn-secondary" value="Silver" name="Silver"><a class="me-1"></a>
>             <input type="submit" class="btn btn-brown" value="Bronze" name="Bronze"><a class="me-1"></a>
>             <input type="submit" class="btn btn-primary" value="Unranked" name="Unranked"><a class="me-1"></a>
>         </p>
>     </form>
>     
>   // URL을 통해 보낸 값을 Controller에서 받아주는 방법      
>     @PostMapping("/{instructorId}/medal")
>        public String medal(
>           @PathVariable("instructorId")
>              Long instructorId,
>              @RequestParam(value = "Gold", required = false) String Gold,
>              @RequestParam(value = "Silver", required = false) String Silver,
>              @RequestParam(value = "Bronze", required = false) String Bronze,
>              @RequestParam(value = "Unranked", required = false) String Unranked
>        )
>```
> #### 금액 표기하기
> * 금액 표기하기 - 가독성 향상을 위해 `Integer`로 선언된 값을 프론트에서 1000단위 마다 콤마 찍어주기 ex) 50000 -> 50,000 <br>
> * 금액 표기하기 - `NumberUtils`클래스를 만들어서 사용 / 단, 콤마(,)는 문자열이기 때문에 String타입에 저장 
> ```java
>   // 금액 표기하기
>     @Component
>       public class NumberUtils {
>          public String addCommasToNumber(int number) {
>            DecimalFormat formatter = new DecimalFormat("#,###");
>            return formatter.format(number);
>          }
>       }
>```
            
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

`/admin/settlement` 정산하기 <br>
> 강사의 정산 신청 금액과 정산 신청 날짜를 보여주고, 정산하기를 클릭하면 정산 완료로 바꿔준다<br>
> * 정산하기 - `SettlementState`를 정산 대기 중(SETTLEMENT_PENDING)에서 정산 완료(COMPLETE)로 바꿔준다.
> * 금액 표기하기 - [이동하기](#금액-표기하기)
> * 날짜 표기하기 - 가독성 향상을 위해 `LocalDateTime`로 선언된 값을 프론트에서 yyyy-mm-dd로 만들어주기  
>```java
>   // 날짜를 yyyy-mm-dd 모습으로 바꿔주기 ex) 2024-05-23 16:33:12.000000 -> 2024-05-23  
>     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
>       log.info("GetCompleteTime 수정 전 : {} ", settlement.getCompleteTime()); // 2024-05-23 16:33:12.000000
>       String dateCompleteTime = settlement.getCompleteTime().format(formatter);
>
>       log.info("GetCompleteTime 수정 후 : {} ", dateCompleteTime); // 2024-05-23
>       settlement.setDateCompleteTime(dateCompleteTime);
>```

관리자 페이지의 프론트는 `반응형 웹 템플릿`을 이용하였다

## 배포 과정 간략히 보기 👀
[여기를 눌러 주세요](https://github.com/mad-cost/Likelion_team5/blob/main/md/homeGym.md "Click")

<img src="img/num12.png">

## 회고 🤔
첫 팀 프로젝트를 하면서 가장 고민했던 점은 '역할 분담과 협업' 이었던 것 같다. <br>
- 역할 <br>
  파트를 분배함에 있어, 기능을 중심으로 나눌지, 페이지를 중심으로 나눌지, 혹은 구현이 어려운 순서부터 차근차근 해결해 나아갈지 갈피를 잡지 못했던 것 같다. <br>
- 협업 <br>
  깃 브랜치를 처음 사용하다 보니 시간을 많이 잡아먹었고, 충돌이 일어날 때마다 당황을 했었지만, 결국엔 하면 할수록 익숙해지는게 깃(Git)인거 같다. <br>
  팀원의 구현하지 못한, 공지사항의 검색 기능을 대신 구현해 줬는데 새로운 기능을 완성했을 때 느끼는 만족감이 나를 성장하는 개발자로 만들어 주는 것 같다.
