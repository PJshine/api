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
public class ResponseCardPay {
    @Id
    private String mgntNo;		    //승인번호
    private String msgConts;        //전문내용
}