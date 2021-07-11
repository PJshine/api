package com.rest.api.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder // builder를 사용할수 있게 합니다.
@Entity // jpa entity임을 알립니다.
@Getter // user 필드값의 getter를 자동으로 생성합니다.
@Setter
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
public class ResponseSearchCardPay {
    @Id
    private String mgntNo;	    	//관리번호
    private String trscDt;			//거래일시
    private String cardNo;	        //카드번호
    private String validDt;	    	//카드유효기간
    private String cvc; 			//cvc
    private String payTypCd;		//결제/취소구분
    private String paySdCd;		    //결제/취소/부분취소구분
    private int payAmt;				//PAY_AMT
    private int vatAmt;				//VAT_AMT
    private int canPayAmt;			//CAN_AMT
    private int canVatAmt;			//CAN_VAT_AMT
}