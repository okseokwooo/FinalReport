## FinalReport

## 목차

 1. 주제 선정 이유
 2. "갖다드림" 앱 소개
 3. 유사 앱 분석 및 차이점
 4. 앱 제작 툴 소개
 5. 앱 실행 환경 구축
 6. 각 클래스 기능별 설명 (일단 구분)
 7. 클래스 별 주요 코드 상세 설명 (일단 구분)
 8. "갖다드림" 어플리케이션 한계점
 9. 결론

___

## 1. 주제 선정 이유

- 교내에서 누군가 대신 해주었으면 하는 심부름이 필요한 상황을 종종 겪게됨
- 상명대학교 학생만을 위한 심부름 어플리케이션이 존재하지 않음
- 상명대학교 학생들끼리 서로 도움을 줄수 있겠다는 기대

___

## 2. 갖다드림 어플 소개

 -  상명대학교 학생들을 위한 심부름 서비스 Appliction이다.
 -  캠퍼스 내 시설 또는 캠퍼스 근방 학생들에게 심부름 서비스를 제공한다.
 -  심부름 시키는 사람은 수행하는 사람에게 일정한 대가 지급하며 어플이 순환한다.
 -  기숙사생이나 자취생들이 많이 사용할 것으로 예상된다.

___

## 3. 유사 앱 분석

![김집사](https://user-images.githubusercontent.com/79950095/120915782-abaabf00-c6e0-11eb-9247-d39d6b39a116.JPG)


##### 어플소개

사용자가 앱에서 심부름을 시키면 회사 소속 직원이 고객에게 서비스를 제공.

##### "갖다드림"과의 차이점

||김집사|갖다드림|
|------|---|---|
|서비스 제공|회사 직원|사용자|
|서비스 범위|전국|상명대학교|

출처 : https://play.google.com/store/apps/details?id=com.mybutler.android

___

## 4. 어플을 제작하기 위해 필요한 도구

 -  안드로이드 스튜디오 (.version)
 -  데이터베이스 (MySQL Workbench .version)
 -  서버 (Eclipse .vsersion)

___

## 5. 앱 실행 환경 구축

1. FinalURLIP.java  파일에서 public final static String adress = "My IP";

       AVD의 경우 My IP부분에 10.0.2.2:8080 입력
       개인Device의 경우 My IP부분에 자신의 IP입력
       자신 IP 확인방법 : (win + r) -> cmd -> ipconfig -> Ipv4주소

2. MySQL Workbench 실행

스키마 생성 (Schema name = gddl)
"갖다드림" SQL file Query 실행

3. Eclipse 실행

       GDDL_MobileServer -> src -> FinalDB.java
       ```java
       public class FinalDB {
          public final static String dbID = "My DB ID";
          public final static String dbPassword = "My DB Pass";
          public final static String dbProject = "gddl";
       }
       ```
       My DB ID 부분에 자신의 MySQL ID 입력

       My DB Pass 부분에 자신의 MySQL Password 입력
___

## 6. 각 클래스 기능별 설명

|클래스|기능|layout|
|------|---|---|
|AddressActivity.java|채팅방 GPS|activity_adress.xml|
|Board_Module.java|게시글작성내용서버전송|x|
|Chat_Module.java|채팅내용서버전송|x|
|ChatList_Module.java|채팅목록서버전송|x|
|Chatting_Fragment.java|채팅방 보기|chatting_fragment.xml|
|ChattingAdapter.java|채팅어댑터|x|
|ChattingContentData.java|채팅내용데이터|x|
|ChattingListData.java|채팅목록데이터|x|
|ChattingRecordMoudule.java|채팅기록모듈|x|
|CustomCareActivity.java|고객센터 Activity|activity_custom_care.xml|
|FinalURLIP.java|서버on/off IP주소설정|x|
|GMailSender.java|학번인증번호전송관련|x|
|GpsTracker.java|GPS관련|x|
|GuideActivity.java|전체 가이드라인 설정|activity_guide.xml|
|Guide_First.java|가이드라인1|guide_line_first.xml|
|Guide_second.java|가이드라인2|guide_line_second.xml|
|Guide_third.java|가이드라인3|guide_line_third.xml|
|Guide_Fourth.java|가이드라인4|guide_line_fourth.xml|
|Guide_Five.java|가이드라인5|guide_line_five.xml|
|InsetBoardActivity.java|게시판에 글쓰기 화면|activity_insert_board.xml|
|LoginActivity.java|로그인 화면|activity_login.xml|
|MainActivity.java|메인 화면|activity_main.xml|
|MainAdapter.java|메인어댑터|x|
|MainBoard_Fragment.java|게시판목록보기화면|mainboard_fragment.xml|
|MainData.java|메인데이터|x|
|MembershipActivity.java|회원가입 화면|activity_membership.xml|
|MenuList_Fragment.java|주문목록 화면|menulist_fragment.xml|
|MyInfo_Fragment.java|내정보 화면|myinfo_fragment.xml|
|MyService.java|서버연결|x|
|NoticeAdapter.java|공지어댑터|x|
|NoticeData.java|공지데이터|x|
|Notify_Module.java|공지모듈|x|
|NotifyActivity.java|공지사항 화면|activity_notify.xml|
|RequestHttpURLConnection.java|URL|x|
|SharedPrefsUtil.java|필요한거?|x|
|SignatureUtil.java|??|x|
|Splash.java|앱 실행시 Splash Animation|splash.xml|
|User_Module.java|유저모듈|x|

___

## 7. 클래스별 주요 코드 상세 설명

___

## 8. 어플리케이션 한계점

- 코로나19 바이러스로 인한 비대면수업 = 학교주변 인원 감소
- 방학기간 학교주변 인원 감소에 따른 사용자 감소
- 전문업체가 아닌 사용자끼리 서비스를 제공하는 형태이므로 Service Quality 상이할 가능성有

___

## 9. 결론

___
