## 📝전문가 방문 PT 서비스 만들기
외출이 제한되는 노인, 임산부, 바쁜 직장인들을 위해 운동 전문가가 직접 가정에 방문하여 건강하고 꾸준한 운동습관을 만들어주는 고객 맞춤형 방문 PT를 제공해 주는 서비스입니다.

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

## 담당한 기능 정리 💻
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

## 배포 과정 정리 👀
[여기를 눌러 주세요](https://github.com/mad-cost/Likelion_team5/blob/main/md/homeGym.md "Click")

### HTTPS 적용 성공!! 🙆
<img src="img/num12.png">

## 회고 🤔
관리자 페이지는 유저와 강사 페이지를 다 완성하고 만드는 것이 아니기 때문에, 앞선 각 파트를 맡은 팀원이 어떤 방식으로 로직을 만드는지에 대하여 팀원과의 의사소통을 중요시하는 역할이었던 것 같습니다. 그만큼 팀원들의 성격을 빨리 파악할 수 있고, 팀의 분위기를 내가 먼저 리드하고 함께 만들어 갈 수 있어서 좋았다고 생각합니다.
<br>

- 트러블 슈팅 <br>
배포를 진행하면서 aws ec2에서 사용 중 자꾸 멈추는 현상이 발생하였습니다. 이 문제를 해결하기 위해 많이 이유를 찾아보고, 혹여 배포 과정 중에 문제가 있던 건 아닌지 고민하게 되었습니다.
<br>

- 해결과정 <br>
알고보니 도커에서 컨테이너 3개를 동시에 실행시켜서, ec2 프리티어가 용량을 감당하지 못해  멈추는 문제였습니다. 이 문제를 해결하기 위해 클라우드를 aws에서 ncp(네이버 클라우드 플랫폼)로 바꾸게 되었고 멈추는 문제는 해결되었습니다. 해결책이 너무 단순해서 약간의 허무함과 허탈함을 느끼기도 하였지만 그래도 촉박한 시간에 HTTPS까지 적용하여 성공적으로 배포하였습니다.


