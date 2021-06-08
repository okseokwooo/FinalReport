# FinalReport
<p align="center">
<img width="700px" src="https://user-images.githubusercontent.com/79950095/120919672-c50a3600-c6f5-11eb-9ed7-95359f927284.png">
<br>
<img src="https://img.shields.io/badge/license-mit-green">
<img src="https://img.shields.io/github/issues/hongjin4790/SYE-project">
<img src="https://img.shields.io/badge/tag-v1.0.0-blue">
<br>
<img src="https://img.shields.io/badge/Android Studio-3DDC84?style=flat-square&logo=Android&logoColor=white"/>
<img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"/>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>
<br>
<img src="https://img.shields.io/badge/Eclipse-2C2255?style=flat-square&logo=Eclipse&logoColor=white"/>
<img src="https://img.shields.io/badge/Tomcat9.0-F8DC75?style=flat-square&logo=Apache-Tomcat&logoColor=black"/>
<br>
<br><br><br><br><br>
 </p>

# Project Name = 갖다드림(bring you)
# Team Member : 김준혁, 김형진, 마건우, 옥석우, 차형석
___
## 목차

 1. 주제 선정 이유
 2. "갖다드림" 앱 소개
 3. 유사 앱 분석 및 차이점
 4. 파일 다운로드 유의점
 5. 앱 제작 툴 소개
 6. 앱 실행 환경 구축
 7. 각 클래스 기능별 설명
 8. 개발 
 9. "갖다드림" 어플리케이션 한계점
 10. 결론

___

## 1. 주제 선정 이유

- 교내에서 누군가 대신 해주었으면 하는 심부름이 필요한 상황을 종종 겪게됨
- 기숙사 편의점 폐쇄
- 상명대학교 학생만을 위한 심부름 어플리케이션이 존재하지 않음
- 상명대학교 학생들끼리 서로 도움을 줄수 있겠다는 기대

___

## 2. "갖다드림" 앱 소개

 -  상명대학교 학생들을 위한 심부름 서비스 Appliction이다.
 -  캠퍼스 내 시설 또는 캠퍼스 근방 학생들에게 심부름 서비스를 제공한다.
 -  심부름 시키는 사람은 수행하는 사람에게 일정한 대가 지급하며 어플이 순환한다.
 -  기숙사생이나 자취생들이 많이 사용할 것으로 예상된다.

___

## 3. 유사 앱 분석 및 차이점

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

## 4. 파일 다운로드 유의점

아래 폴더에서 
    GDDL_MoblieServer, Admin_Notify.zip, GDDL DB File.sql을 따로 빼서 
    ![folder](https://user-images.githubusercontent.com/29851990/121128207-fefa4a00-c865-11eb-8f72-8ab9de4a1f43.PNG)


___

## 5. 앱 제작 툴 소개

 -  안드로이드 스튜디오 (android studio 4.2.1) https://developer.android.com/studio?gclid=EAIaIQobChMI9InWvMeC8QIVx1VgCh3c0gDYEAAYASAAEgI3PPD_BwE&gclsrc=aw.ds
 -  데이터베이스 (MySQL Workbench 8.0.25) https://dev.mysql.com/downloads/mysql/
 -  서버 (Eclipse IDE 2021-03 , Tomcat 9.0) https://www.eclipse.org/downloads/ https://tomcat.apache.org/download-90.cgi

___

## 6. 앱 실행 환경 구축

1. JSP 개발환경 구축

       (1) Eclipse Java EE 다운로드 https://www.eclipse.org/downloads/packages/ 에서 Eclipse IDE for Enterprise Java and Web Developers 설치
       (2) Eclipse -> New -> Open File -> GDDL_MobileServer
       (3) Tomcat 다운로드 https://shinysblog.tistory.com/5
       (4) GDDL_MobileServer 우클릭 -> Properties -> Java Build Path -> Libaries -> gson과 mysql-connect가 없을때만 Add JARs.. -> GDDL_MobileServer -> WebContent -> WEB-INF -> gson, mysql-connect 둘다 Add && Apply
       (5) GDDL_MobileServer 우클릭 -> Properties -> WebProjectSetting -> Context root : / 입력 -> Apply
       (6) GDDL_MobileServer 우클릭 -> Properties -> Server -> Tomcat9.0 -> Apply
       (7) Eclipse -> Project -> Properties -> Targeted Runtime -> New -> Tomcat9.0 -> Finish -> Apply

2. Mysql 설치환경 구축 

       https://m.blog.naver.com/bjh7007/221829548634 참고
3. Android Studio 실행

       File -> Open -> GajDaDeuLim
       FinalURLIP.java  파일에서 public final static String adress = "My IP";
       AVD의 경우 My IP부분에 10.0.2.2:8080 입력
       개인Device의 경우 My IP부분에 자신의 IP입력
       자신 IP 확인방법 : (win + r) -> cmd -> ipconfig -> Ipv4주소

4. MySQL Workbench 실행

       스키마 생성 (Schema name = gddl)
       "갖다드림" SQL file Query 실행

5. Eclipse 실행

```java
public class FinalDB {
   public final static String dbID = "My DB ID"; //본인의 데이터베이스 아이디입력
   public final static String dbPassword = "My DB Pass"; // 본인의 데이터베이스 패스워드입력
   public final static String dbProject = "gddl"; // "gddl" 입력
}
```
       
       GDDL_MobileServer -> src -> FinalDB.java
       
       My DB ID 부분에 자신의 MySQL ID 입력

       My DB Pass 부분에 자신의 MySQL Password 입력 후
       
       GDDL_MobileServer 패키지 우클릭 -> Run As -> Run on Server
5. 구글맵 API 활성화

      https://console.cloud.google.com/apis/credentials?project=handy-amplifier-311401
       
       위 링크로 들어가 자신의 API Key 를 추가 pakage name = com.example.gajdadeulim

6. 앱 실행
       
       Android Studio 에서 Run
 

7. 문의
       
       질문 사항이 있거나 원활히 진행되지 않는다면 Ecampus 해당분반 "차형석" 학생에게 메시지 보내주시면 감사하겠습니다.
___

## 7. 각 클래스 기능별 설명

|클래스|기능|layout|
|------|---|---|
|AddressActivity.java|구글맵 api를 사용한 위치확인|activity_adress.xml|
|Board_Module.java|게시글관련 데이터들의 클래스화|x|
|Chat_Module.java|채팅관련 데이터들의 클래스화|x|
|Chatting_Fragment.java|채팅방 보기|chatting_fragment.xml|
|ChattingAdapter.java|Chatting_Fragment RecyclerView에 들어갈 어댑터|x|
|ChattingContentData.java|ChattingAdapter 들어갈 컴포넌트 클래스화|x|
|ChattingRecordMoudule.java|채팅기록관련 데이터들의 클래스화|x|
|CustomCareActivity.java|고객센터 Activity|activity_custom_care.xml|
|FinalURLIP.java|서버 ,port주소설정|x|
|GMailSender.java|학번 인증번호 전송관련 라이브러리|x|
|GpsTracker.java|GPS관련 라이브러리|x|
|GuideActivity.java|전체 가이드라인 설정|activity_guide.xml|
|Guide_First.java|가이드라인1|guide_line_first.xml|
|Guide_second.java|가이드라인2|guide_line_second.xml|
|Guide_third.java|가이드라인3|guide_line_third.xml|
|Guide_Fourth.java|가이드라인4|guide_line_fourth.xml|
|Guide_Five.java|가이드라인5|guide_line_five.xml|
|InsetBoardActivity.java|게시판에 글쓰기 화면|activity_insert_board.xml|
|LoginActivity.java|로그인 화면|activity_login.xml|
|MainActivity.java|메인 화면|activity_main.xml|
|MainAdapter.java|MainActivity RecyclerView에 들어갈 어댑터|x|
|MainBoard_Fragment.java|게시판목록보기화면|mainboard_fragment.xml|
|MainData.java|MainAdapter에 들어갈 컴포넌트 클래스화|x|
|MembershipActivity.java|회원가입 화면|activity_membership.xml|
|MenuList_Fragment.java|주문목록 화면|menulist_fragment.xml|
|MyInfo_Fragment.java|내정보 화면|myinfo_fragment.xml|
|MyService.java|양방향 통신을 하기위한 안드로이드 Service|x|
|NoticeAdapter.java|NotifyActivity RecyclerView에 들어갈 어댑터|x|
|NoticeData.java|NoticeAdapter에 들어갈 컴포넌트 클래스화|x|
|Notify_Module.java|공지관련 데이터들의 클래스화|x|
|NotifyActivity.java|공지사항 화면|activity_notify.xml|
|RequestHttpURLConnection.java|단방향통신을 하기위한 라이브러리|x|
|SignatureUtil.java|해시코드로 변환하기위한 라이브러리|x|
|Splash.java|앱 실행시 Splash Animation|splash.xml|
|User_Module.java|유저관련 데이터들의 클래스화|x|

___

## 8. 개발 과정

     Week5.DB - 초기 데이터베이스 스키마 설계, 서버/안스 회원가입 및 로그인 환경 구축,
           - 구글맵활용 GPS로 현위치 찾기
           
     Week6.서버 - 게시글 관련 서블릿 ,
           -  게시글 관련된 main adapter 구현-차형석, 게시글 등록 -마건우, 메인 화면 구축(로그아웃, 회원정보 변경)-김준혁, 게시글 새로고침 기능 - 옥석우 
           
     Week7.서버 - 주문 수락 및 취소,불러오기 서블릿  ,
           - 게시글 펼쳐보기/수락 기능구현 - 김준혁, 주문 취소 구현 - 옥석우, 주문목록 구현- 마건우, 프래그먼트 구현 - 차형석
           
     Week8.서버 - 고객센터 신고접수, 공지사항 불러오기/등록하기 , 
           - 고객센터 신고접수 기능 구현 - 김준혁, 가이드라인 기능 구현 - 옥석우, 게시글 세부 분류 - 마건우, 공지사항 추가 및 게시 - 차형석
           
     Week9.서버 - 채팅관련 서블릿, 안스 - 스플래쉬스크린 - 김준혁,
           - 채팅 액티비티, 채팅프래그먼트(채팅목록) - 옥석우,차형석 , 주문목록 변경, 메인게시판 정리
           
     Week10.DB - 채팅기능에서 필요한 데이터 검토, 서버 - 비동기 서버 구현을 위한 학습 및 제작진행, 
          안스 - 채팅방 레이아웃-김준혁 , 채팅 액티비티 - 마건우 ,안드로이드 서비스 구현- 차형석
          
     Week11.서버 - Servlet과는 독립적으로 동작하는 서버소켓 및 쓰레드 비동기 구현 중
          안스 - 서비스 안에 쓰레드 구현 - 차형석, 디자인 - 김/마/옥 
          
     Week12.서버 - 비동기 채팅서버 구현 및 비동기 서버와 기존 단방향 통신 서버 병합 작업
          안스 - 채팅방 어댑터 구현-마건우, 공지사항 툴바 및 내용 디자인-옥석우,고객센터 디자인- 김준혁, 글쓰기 디자인 및 안드로이드 서비스내 소켓통신 관련 동작 재정의-차형석
          
     Week13. 
         김형진
        - LoginActivity 회원가입 버튼 및 margin 관련 디자인 보완
        - MainActivity 의 글쓰기 Button 디자인 / Fragment 관련 UI 수정, fragment button을 삭제하고 bottomNavigationView를 추가

        김준혁
        - LoginActivty 로고 사이즈 조절 및 UI수정
        - MembershipActivty UI 수정 및 성별 선택란 EditText -> RadioButton으로 수정
        - myinfo_fragment UI 수정

        마건우
        -Chattingactivity의 content크기 조절및 위치조정/말풍선크기 조절, 지도와 연결된 네비게이션 버튼 구현
        -말풍선을 wrapcontent로 설정후 min 값을 설정하여 텍스트가 작은상황에서 말풍선의 최소크기값을 설정
        -Adapter에서 말풍선이 send인지 receive인지 구분하기위해 조건에따라 출력되는 채팅 아이템의 layout을 다르게 설정
        -Chattingactivity에서 네비게이션 버튼을 이용하여 구글맵과 연관된 Addressactivity로의 이동을 인텐트으로 구현

        옥석우
        - activity_notice.xml의 전체적인 디자인 수정 (notice 로고를 넣고, 시간 .으로 세분화 및 우측 정렬과 공지사항에 각 number부여 
         공지사항의 제목은 center정렬)
        - activity_custom_care.xml의 전체적인 디자인 수정 (체크 박스를 전체적으로 가운데로 모으고, textcolor는 black으로 통일,
         신고 제목과 신고하려는 상대 아이디를 입력할 수 있도록 수정)

        차형석 
        - 안드로이드, 서버단에 강제 종료시 상황 구현
        - global server를 위해 도메인과 서버연동중
        - ChattingFragment를 없애고 ChattingActivity로 변경
        - 실시간 채팅과 채팅방 대화기록유지, 채팅상대가 없을때 상황등 각각의 채팅상황에 대한 UI, UX 구현

___

## 9. "갖다드림" 어플리케이션 한계점

- 코로나19 바이러스로 인한 비대면수업 = 학교주변 인원 감소
- 방학기간 학교주변 인원 감소에 따른 사용자 감소
- 전문업체가 아닌 사용자끼리 서비스를 제공하는 형태이므로 Service Quality 상이할 가능성有

___

## 10. 결론

___
