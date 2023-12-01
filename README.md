# MyVocaService
## 1. 아이템 선정
- v1: 일본어의 단어장을 작성할 경우에 한자 히라가나 영어 한글뜻이 필요한데 여러 사이트를 찾아봐도 최적화 되어 있는 사이트를 찾기 힘들고 만약에 내가 만든 단어장이
직접 팔리게 되도록 하였다. 사고 팔때 포인트를 직접 충전하고 단어장을 게시판에 등록 할때 포인트를 직접 정하도록 하고 만약에 다른 사람들이 단어장을 산다고 하였을 때
내가 등록한 포인트 만큼 포인트가 들어오도록 하고 어느 정도 포인트가 모이면 환전할 수 있도록 하였다.
- v2: 환전기능 고려중, DB구조 모두 수정, 코드 로직 entity 제외한 모든 프로세스 수정, frontend 모두 react로 변경, KAKAO와 Toss 결제 추가

## 2. 개요
+ 프로젝트 이름: MyVocaSearvice
+ 개발 인원 : 1명
+ 개발 기간 : v1 -> 2022.03.25 ~ 2022.04.31 v2 -> 2023.11.22 -> 2023.12.01
+ 개발 언어 : Java 17
+ FrontEnd: React.js, Bootstrap
+ BackEnd: Spring Boot(2.6.7), Spring Security(JWT), Jpa, Gradle, QueryDSL
+ 데이터베이스: MySQL
+ Git: Github

## 3. 주요기능
  + 회원 - Spring Security, JWT를 이용하여 Token 방식의 회원 로직 구현
  + 충천 - KAKAO와 TOSS 결제 모듈을 통하여 결제
  + 환전 - 사용자가 환전 정보를 등록하면 admin이 확인하여 환전하는 방식 (추후 추가 예정)
  + 단어장 - 회원의 컬럼 수를 정하여 단어장을 등록하고 동적으로 데이터를 반영할 수 있다.
  + 거래소 - 거래소에 단어장을 포인트를 지정해서 올릴 수 있도록 하고 단어장을 구매를 해도 read의 기능만 있다.

## 4. DB ERD
![msv](https://github.com/dlqudals12/msv1/assets/22268579/1ed689ae-574d-4e01-beb5-ca06870194dc)

