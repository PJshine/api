package com.rest.api.model;

import com.rest.api.model.response.CommonResult;
import lombok.*;

@Getter
@Setter
public class SingleResult<T> extends CommonResult {
    private T data;
}
