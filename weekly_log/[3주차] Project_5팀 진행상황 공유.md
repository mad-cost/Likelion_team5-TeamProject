## 팀 구성원, 개인 별 역할

---

프로젝트 팀 구성원을 기재해 주시고, 그 주의 팀원이 어떤 역할을 맡아서 개발을 진행했는지 구체적으로 작성해 주세요. 🙂 

- 해당 역할을 그대로 따를 필요는 없습니다!

<aside>
💫 **역할 분담**

| 정동은 | 강사 - 승인, 정산 |
| --- | --- |
| 고기욱 | 회원, 즐겨 찾기, 후기 |
| 김주홍 | 프로그램, 코멘트, 강사 스케줄 |
| 서병준 | 관리자 - 관리, 승인, 정산 |
| 이윤배 | 인증, 카카오 로그인, 회원 가입 |
| 이시은 | Toss Payments 시스템 |
</aside>

## 팀 내부 회의 진행 회차 및 일자

---

- 8회차(2024.04.22) - 디스코드 회의
- 9회차(2024.04.23) - 디스코드 회의
- 10회차(2024.04.24) - 디스코드 회의
- 11회차(2024.04.25) - 디스코드 회의
- 12회차(2024.04.26) - 디스코드 회의

## 현재까지 개발 과정 요약 (최소 500자 이상)

---

- 정동은
    1. 강사 리뷰 페이지(90%)
        - 유저의 리뷰와 강사의 답글 조회 가능
        - 유저 리뷰 작성 시 강사의 평점 업데이트 기능 구현해야함
    2. 강사 수업 페이지(100%)
        - 진행중인 프로그램과 진행중이 아닌 프로그램 나눠서 보여줌
        - 프로그램 클릭해서 상세 페이지로 이동
    3. 강사 프로그램 상세 페이지(100%)
        - 프로그램 정보 보여준 뒤 등록한 회원 조회 가능
        - 종료된 회원도 조회 가능하도록 추가함
        - 회원 눌러서 상세 페이지로 이동
    4. 강사 프로그램 회원 상세 페이지, 강사 일지 기능(100%)
        - 강사가 회원 일지 작성 가능한 페이지
        - 회원 일지 수정 버튼 누르면 숨겨진 폼 나와서 수정하는 기능 구현
        - 일지 작성 시 회원의 회차권 소모 기능 구현
        - 나중에 일지 작성 시 사진 추가 기능 구현 예정
    5. 강사 수락/거절 기능(100%)
        - 강사가 프로그램에 신청한 유저 정보를 보고 수락하거나 거절 가능
        - User Email을 보여주어 카카오톡으로 연락 가능하도록 함
        - 수락하면 진행중 상태, 거절하면 DB에서 apply 지워버림
    6. 카테고리 수정(100%)
        - 카테고리를 enum으로 관리하려고 했지만 문제가 생겨서 sub_category 구현이 안됨. 어쩔 수 없이 main_category와 sub_category 두 테이블 만들어서 사용함.
        - main_category를 선택하면 main에 딸린 sub_category가 표시되는 기능 구현. 오래 걸림..
        - 프로그램 관련 페이지에서 category 부분 모두 수정
    
    7.  지역 정보 추가(20%)
    
    - 지역 정보를 구현 안 한 것을 뒤늦게 깨닳음
    - 시군구/읍면동 데이터를 db에 저장하는 방법으로 구현하기 위해 검색중
    
- 고기욱
    
    후기 게시판 (100%)
    
    유저들은 자신이 수강했던 과목에 대하여 후기를 쓸 수 있다.
    
    - 사진을 총 5장 올릴 수 있다
    - 사진을 첨부하면 이미지 컨테이너에 올린 사진이 미리보기로 보인다
    - 후기는 이미지를 포함하여 수정할 수 있다
    - 후기를 삭제하면 첨부했던 파일도 같이 삭제된다
    - 평점은 1점부터 5점까지 줄 수 있다
    
    메일 서비스 (100%)
    
    처음에는 Java mail sender를 이용하여 단순하게 메일을 보내는 것을 구현
    
    - 메일과 관련된 의존성 및 프로퍼티 추가
    
    그 후 메일 서비스 고도화 작업을 실시
    
    - 메일 인증을 redis에 저장하기로 결정
    - 강사 회원가입 시, 이메일을 중복 확인 후 이메일로 인증번호 발송
    - 인증 번호 발송시, lifetim은 3분, 인증번호는 6자리의 알파벳과 숫자로 이루어진 랜덤한 문자를 발송
    
    성과자체 평가
    
    - 파일 첨부에서 이미지를 조금 더 이쁜 방향으로 올리게 하는 방법을 고려하다가 많은 고생을 하였음
    - 후기 수정 시, 기존에 후기에 존재하였던 이미지 파일을 다시 불러와서 저장하는 방법에서 고생하였음
    - 후기 수정 시, 기존에 있던 사진과 현재 사진(새로 올린)의 비교방법이 모호하여 기존의 사진은 삭제 후, 새로운 사진을 저장하는 방법으로 채택
    - 메일 전송 시, redis에 key → Email, Value → 인증번호 형식으로 저장을 하고, 유지 시간을 두기위해서 잊고 있었던 redis에 대하여 다시 공부를 할 수 있었음.
- 이시은
- 서병준
    - 관리자 페이지 전부 완성
    - 메인 페이지 작업중
        - 인기강사 - 메달이 Gold인 강사 3명 랜덤으로 프론트에 뛰워주기 (완료)
        - 리뷰 - 리뷰 및  사진을 가져오는 작업은 완료, Pageable처리해주기 완료, 리뷰를 적은 고객의 이름을 가져오는 부분은 작업중
- 김주홍
    - Program(100%)
        - 프로그램 CRUD 완료
    - Schedule(100%)
        - 스케줄 CRUD 완료 / 요일순, 시간순으로 볼 수 있게 하였고 하나의 페이지 내에서 생성 수정 삭제할 수 있게 구현
    - Comment(100%)
        - Comment CRUD 완료
    - 어려웠던 점
        - Program 코드 작업 중에 강사 로그인이 필요해서 팀원의 branch 작업물과 merge 하던 중 다른 작업물은 다 merge가 됐는데 강사 로그인 코드만 안되고 팀원들의 branch는 dev에 merge가 되는데 내 branch는 안 되며 다른 branch와 merge가 안 되는 상황이 생겼다 처음에 내 작업물에서 다른 branch를 생성해서 dev와 merge도 해보고 문제가 생긴 시점의 commit으로 돌아가서 새 branch도 만들어보고 해도 merge가 안 되어서 병합하는 방법 중 squash를 문제가 있던 commit들을 squash를 하여 dev와 merge 하였더니 해결이 되었다 아직 정확한 원인을 파악은 못했다
- 이윤배

## 개발 과정에서 나왔던 질문 (최소 200자 이상)

---


### 카테고리 구현

카테고리가 많지 않아 enum 타입으로 구현해두었다가 sub_category 구현 과정에서 힘들어서 각각의 테이블로 나누어서 만들었다.

카테고리를 구현하면서 카테고리를 만드는 두 가지 방법에 대해 정리했다.

1. **계층형 카테고리 구현(자기 참조)**

```sql
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;

    @Column(length = 30, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category parent;

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }
}
```

- 이런 식으로 카테고리를 구현하여 자기 자신을 참조하면 무한한 깊이의 카테고리를 구현할 수 있다.
- 그러나 직관적으로 카테고리를 볼 수 없고 테이블을 조회하기 어렵다.

1. **level별 카테고리**

```sql
public class MainCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "mainCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SubCategory> subCategories;
}

public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainCategoryId")
    private MainCategory mainCategory;  // 객체 참조로 변경
}
```

- 직관적으로 카테고리를 찾을 수 있다.
- 나중에 깊이가 늘어날 경우에 추가 테이블을 생성해야 한다.
- 우리 프로젝트의 카테고리는 단순한 구조이므로 카테고리 level별로 테이블을 만들어 main_category와 sub_category로 구현하였다.

## 개발 결과물 공유

---

Github Repository URL: 
