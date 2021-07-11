package com.rest.api.entity;

import com.rest.api.crypto.CryptoConverter;
import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;

@Builder // builder를 사용할수 있게 합니다.
@Entity // jpa entity임을 알립니다.
@Getter // user 필드값의 getter를 자동으로 생성합니다.
@Setter
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
@Table(name = "CARD_PAY_BASE")
public class CardPay {
    @Id // primaryKey임을 알립니다.
    private String mgntNo;		    //MGNT_NO
    private Timestamp trscDt;		//TRSC_DT
    private String ognMgntNo;	    //OGN_MGNT_NO
    private String mercNo;			//MERC_NO
    private String tmlIdNo;			//TML_ID_NO
    @Convert(converter = CryptoConverter.class)
    private String cardInfo;		//CARD_INFO
    private String payTypCd;		//PAY_TYP_CD
    private String payStCd;			//PAY_ST_CD
    private String itlmMmsCnt;		//ITLM_MMS_CNT
    private int payAmt;				//PAY_AMT
    private int vatAmt;				//VAT_AMT
    private int canPayAmt;	        //CAN_AMT
    private int canVatAmt;			//CAN_VAT_AMT

    //취소
    public CardPay setCancel(String ognMgntNo, String payStCd, int canAmt, int canVatAmt) {
        this.ognMgntNo  = ognMgntNo;
        this.payStCd    = payStCd;
        this.canPayAmt  = canAmt;
        this.canVatAmt  = canVatAmt;
        return this;
    }
}