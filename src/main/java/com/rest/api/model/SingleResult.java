package com.rest.api.model;

import lombok.*;

@Getter
@Setter
public class SingleResult<T> extends CommonResult {
    private T data;
}
