# smallshop
Spring과 JPA로 직접 구현한 개인 프로젝트 쇼핑몰 smallshop

배포 주소:http://43.201.246.54:8080/ 관리자 아이디:admin 관리자 비밀번호:qwerty

개발 기간: 2023.04 ~2023.05

# 목차

  <ul>
    <li><a href="#개발-환경">개발 환경</a></li>
    <li><a href="#사용-기술">사용 기술</a></li>
    <li><a href="#시스템-아키텍처">시스템 아키텍처</a></li>
    <li><a href="#er-다이어그램">er 다이어그램</a></li>
    <li><a href="#기능-구현">기능 구현</a></li>
    <li><a href="#문제-해결">문제 해결</a></li>
  </ul>



## 개발 환경
 <ul>
   <li>IntelliJ</li>
   <li>Github</li>
   <li>Mysql Workbench</li>
 </ul>


## 사용 기술

### 백엔드

<ul>
  <li>Java 11</li>
  <li>Spring Boot 2.7.10</li>
  <li>JPA</li>
  <li>Thymeleaf</li>
  <li>Lombok</li>
</ul>

### 프론트엔드

<ul>
  <li>Css3</li>
  <li>Bootstrap5</li>
</ul>

### 인프라

<ul>
  <li>AWS EC2</li>
  <li>AWS S3</li>
  <li>AWS RDS(Mysql)</li>
  <li>AWS CodeDeploy</li>
  <li>Travis CI</li>
</ul>



## 시스템 아키텍처

<img src='https://github.com/pkdie/smallshop/assets/112155469/2370ae0a-07ee-4cee-94f1-3ca8fc4123d0'>

## ER 다이어그램

<img src='https://github.com/pkdie/smallshop/assets/112155469/4240fa46-289f-4d4c-9ed0-f2cc5108dcb4'>

## 기능 구현

### AWS RDS, S3 환경 구현

프로젝트 초기엔 DB나 이미지 파일을 로컬 환경에 저장하였으나 동적 로딩을 지원하지 않는 문제와 배포 편의성을 위해
AWS RDS, S3을 사용하였습니다.

### 메인 화면

<img src='https://github.com/pkdie/smallshop/assets/112155469/3d06425e-f623-4670-9a40-b7bd3e4bf171'>

### 결제 api 구현
<img src='https://github.com/pkdie/smallshop/assets/112155469/6a093730-7c3f-4b3d-9995-83a6c76f6001'>

### CI/CD 배포

배포 환경 구축을 위해 Travis CI, AWS CodeDeploy을 사용하였습니다.

#### 배포 과정

1. 깃헙에 push된 프로젝트는 .travis.yml 설정에 따라 자동 테스트/빌드되며 jar는 AWS S3에 저장됩니다.
2. Travis CI에게 배포요청을 받은 CodeDeploy는 S3에서 jar를 넘겨받아 appspec.yml 설정에 따라 EC2에 파일을 넘겨줍니다.
3. EC2에서 jar를 실행하면서 설정된 쉘 스크립트 파일을 실행합니다.

### 기타 사항

- 관리자 페이지 구현, 상품 CRUD 구현, 쿠폰 관리 구현
- 상품이 등록되면 AWS S3에 업로드되며 업로드 이미지 파일의 URL를 리턴하게끔 구현
- 회원 기능, 쿠폰, 장바구니, 상품 후기 기능 구현 

  

## 문제 해결

### 로그인 시, 불필요한 쿼리 발생

<img src="https://github.com/pkdie/smallshop/assets/112155469/f1949d88-90ca-451b-9000-f5d03be0b0b1"> 로그인 시 발생하는 쿼리

로그인 시, 회원 쿼리 뿐만 아니라 로그인에 사용되지 않는 회원의 카트 쿼리까지 발생하는 문제가 발생하였습니다.

1:1 양방향 연관관계인 회원-카트 엔티티에서 회원 -> 카트 연관관계를 끊어 회원이 카트를 참조하지않게 수정하였습니다.

### 상품 등록 시 이미지 문제

<img src="https://github.com/pkdie/smallshop/assets/112155469/09fd9805-2398-4a0a-aeab-8694ae74dc22"> static에 이미지 저장 시 이미지 로드 x

상품 등록 시 이미지파일을 static 폴더에 저장되게 구현하였는데 프로젝트를 재시작하기 전까지 이미지가 나오지 않는 오류가 발생하였습니다.
static 폴더는 정적 리소스를 관리하는 곳으로 동적 로딩을 지원하지 않습니다.

AWS S3와 서버를 연동하여 S3 버킷에 이미지를 저장하여 이를 해결하였습니다.

### EC2 인스턴스 문제

AWS 프리티어를 사용하면서 인스턴스가 버벅이고 빌드가 완료되지않고 무한로딩현상이 발생하였습니다.

이는 메모리가 부족해서 발생하는 현상으로 EC2에 가상메모리를 설정으로 해결하였습니다.
