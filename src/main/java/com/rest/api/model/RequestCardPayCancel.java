package com.rest.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class RequestCardPayCancel {
    @NotBlank(message = "Manage No can not be empty")
    @Size(min = 20, max = 20)
    @ApiModelProperty(value = "관리번호", required = true)
    private String mgntNo;
    @Range(min = 100, max = 100000000, message = "Pay Amount is from 100 to 1,000,000,000")
    @ApiModelProperty(value = "결제금액", required = true)
    private int payAmt;
    @Max(value = 100000000, message = "VAT can not be greater than 1,000,000,000")
    @ApiModelProperty(value = "부가가치세")
    private int vatAmt;
}