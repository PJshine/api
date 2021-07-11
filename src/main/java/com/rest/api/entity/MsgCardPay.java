package com.rest.api.entity;

import lombok.*;
import javax.persistence.*;

@Builder // builder를 사용할수 있게 합니다.
@Entity // jpa entity임을 알립니다.
@Getter // user 필드값의 getter를 자동으로 생성합니다.
@Setter
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
@Table(name = "MSG_BASE")
public class MsgCardPay {
    @Id // primaryKey임을 알립니다.
    private String mgntNo;		    //MGNT_NO
    private String msgConts;		//MSG_CONTS
    private String msgStCd;	        //MSG_ST_CD
    private String errCd;			//ERR_CD
    private String errConts;		//ERR_CONTS
}