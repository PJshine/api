package com.rest.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class RequestSearchCardPay {
    @NotBlank(message = "Manage No can not be empty")
    @Size(min = 20, max = 20)
    @ApiModelProperty(value = "관리번호", required = true)
    private String mgntNo;
}