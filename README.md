# 결제시스템 API

0. 개요
카드결제/결제취소/결제정보 조회 REST API

1. 개발환경
Java
SpringBoot
JPA, H2
Intellij Community

2. 프로젝트 실행
H2 database 설치
intellij lombok 플러그인 설치
Enable annotation processing
Preferences - Annotation Procesors - Enable annotation processing 체크

실행
Run -> SpringBootApiApplication
Swagger
http://localhost:8080/swagger-ui.html

3. DDL
-카드결제기본
CREATE CACHED TABLE "PUBLIC"."CARD_PAY_BASE"(
    "MGNT_NO" VARCHAR(20) NOT NULL,
    "TRSC_DT" TIMESTAMP NOT NULL,
    "CARD_INFO" VARCHAR(300) NOT NULL,
    "OGN_MGNT_NO" VARCHAR(20),
    "MERC_NO" VARCHAR(10),
    "TML_ID_NO" VARCHAR(10),
    "PAY_TYP_CD" VARCHAR(1),
    "PAY_ST_CD" VARCHAR(2),
    "ITLM_MMS_CNT" VARCHAR(2),
    "PAY_AMT" DECIMAL(10, 0),
    "VAT_AMT" DECIMAL(10, 0),
    "CAN_PAY_AMT" DECIMAL(10, 0),
    "CAN_VAT_AMT" DECIMAL(10, 0)
);

-전문기본
CREATE CACHED TABLE "PUBLIC"."MSG_BASE"(
    "MGNT_NO" VARCHAR(20) NOT NULL,
    "MSG_CONTS" VARCHAR(450) NOT NULL,
    "MSG_ST_CD" VARCHAR(1),
    "ERR_CD" VARCHAR(10),
    "ERR_CONTS" VARCHAR(500)
);

4. 목차
