# MyVocaService
## 1. 아이템 선정
일본어의 단어장을 작성할 경우에 한자 히라가나 영어 한글뜻이 필요한데 여러 사이트를 찾아봐도 최적화 되어 있는 사이트를 찾기 힘들고 만약에 내가 만든 단어장이
직접 팔리게 되도록 하였다. 사고 팔때 포인트를 직접 충전하고 단어장을 게시판에 등록 할때 포인트를 직접 정하도록 하고 만약에 다른 사람들이 단어장을 산다고 하였을 때
내가 등록한 포인트 만큼 포인트가 들어오도록 하고 어느 정도 포인트가 모이면 환전할 수 있도록 하였다.

## 2. 개요
+ 프로젝트 이름: MyVocaSearvice
+ 개발 인원 : 1명
+ 개발 기간 : 2022.03.25 ~ 2022.04.31
+ 개발 언어 : Java 17
+ 프레임워크 : Spring
+ FrontEnd: Thymeleaf, Javascript, Bootstrap, Html5
+ BackEnd: Spring Boot(2.6.7), Spring Security, OAuth2.0, JPA(SpringData Jpa), Gradle, QueryDSL
+ 데이터베이스: MySQL
+ 형상관리퉁: Github
+ IDE: Intellij Ultimate

## 3. 주요기능
  + 회원 - Spring Security와 OAuth 2.0(구글)을 이용한 회원가입 및 로그인 처리
  + 충천 - CRUD기능을 이용하여 포인트 충전
  + 환전 - 사용자가 환전 정보를 등록하면 admin이 확인하여 환전하는 방식
  + 단어장 - 회원이 단어장을 직접 등록할 수 있다.
  + 게시판 - 다른 사용자가 등록한 단어장을 사고 팔 수 있도록 하고 한 번 사고 난 뒤에는 다시 살 수 없도록 해두었다.

## 4. 데이터베이스 설계(erdcloud)
![MPEtFMFmAnFkPgpbp](https://user-images.githubusercontent.com/22268579/165894074-0b6b22f8-c070-465a-a0d0-5e2becbb88fd.png)

## 5. 시연 영상

https://youtu.be/gNMBvpHEuHE
