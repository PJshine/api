# 결제시스템 API

## 0. 개요
### 카드결제/결제취소/결제정보 조회 REST API

#### 1. 개발환경
* Java
* SpringBoot
* JPA, H2
* Intellij Community

#### 2. 프로젝트 실행
* H2 database 설치
* intellij lombok 플러그인 설치
* Setting - Enable annotation processing 체크

실행
* Run -> SpringBootApiApplication
* Swagger
* http://localhost:8080/swagger-ui.html

#### 3. DDL
* 카드결제기본
> CREATE TABLE "PUBLIC"."CARD_PAY_BASE"("MGNT_NO" VARCHAR(20) NOT NULL,"TRSC_DT" TIMESTAMP NOT NULL,"CARD_INFO" VARCHAR(300) NOT NULL,"OGN_MGNT_NO" VARCHAR(20),"MERC_NO" VARCHAR(10),"TML_ID_NO" VARCHAR(10),"PAY_TYP_CD" VARCHAR(1),"PAY_ST_CD" VARCHAR(2),"ITLM_MMS_CNT" VARCHAR(2),"PAY_AMT" DECIMAL(10, 0),"VAT_AMT" DECIMAL(10, 0),"CAN_PAY_AMT" DECIMAL(10, 0),"CAN_VAT_AMT" DECIMAL(10, 0));

* 전문기본
> CREATE CACHED TABLE "PUBLIC"."MSG_BASE"("MGNT_NO" VARCHAR(20) NOT NULL,"MSG_CONTS" VARCHAR(450) NOT NULL,"MSG_ST_CD" VARCHAR(1),"ERR_CD" VARCHAR(10),"ERR_CONTS" VARCHAR(500));

#### 4. 문제해결1
1. 테이블
    - 통계를 위해 거래일자 추가
    - 취소 연관 거래 조회를 고려하여 원관리번호 컬럼 추가
    - 결제상태금액 기준으로 컬럼을 생성하여야 했으나, 취소 기준으로 컬럼을 생성함. 처리하는 데는 지장이 없으나 재설계시 변경해야 함
    - 유효기간 및 cvc는 요건 문서상 테이터 타입이 숫자로 되어 있으나, 0으로 시작할 경우가 있으므로 (예. 0125, 010 등) 문자로 변경함
2. 구현
    - 관리번호 생성의 기준이 없으므로 현재시각 기준으로 생성함
    - 부분취소 API는 결제 API내에 구현함
    - 전문내용은 암호화 요건이 없어서 평문으로 저장
