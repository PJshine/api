package com.rest.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class ParamMsgCardPay {
    @NotBlank(message = "Card No can not be empty")
    @Size(min = 10, max = 16)
    @ApiModelProperty(value = "관리번호", required = true)
    private String mgntNo;
    @NotBlank(message = "Valid Date can not be empty")
    @Size(min = 4, max = 4)
    @ApiModelProperty(value = "유효기간", required = true)
    private String validDt;
    @NotBlank(message = "Cvc can not be empty")
    @Size(min = 3, max = 3)
    @ApiModelProperty(value = "cvc", required = true)
    private String cvc;
    @NotBlank(message = "Installment Months can not be empty")
    @Size(min = 1, max = 2)
    @ApiModelProperty(value = "할수개월수", required = true)
    private String itlmMmsCnt;
    @NotNull(message = "Pay Amount can not be empty")
    @Range(min = 100, max = 100000000, message = "Pay Amount is from 100 to 1,000,000,000")
    @ApiModelProperty(value = "결제금액", required = true)
    private int payAmt;
    @Max(value = 100000000, message = "VAT can not be greater than 1,000,000,000")
    @ApiModelProperty(value = "부가가치세")
    private int vatAmt;
}