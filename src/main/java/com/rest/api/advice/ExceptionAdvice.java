package com.rest.api.advice;

import com.rest.api.advice.exception.AlreadyCancelException;
import com.rest.api.advice.exception.CardPayException;
import com.rest.api.advice.exception.OverCanAmtException;
import com.rest.api.advice.exception.OverCanVatAmtException;
import com.rest.api.model.response.CommonResult;
import com.rest.api.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseService responseService;
    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
        return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.msg") + "(" + e.getMessage() + ")");
    }

    @ExceptionHandler(CardPayException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult notFoundException(HttpServletRequest request, CardPayException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("notFound.code")), getMessage("notFound.msg"));
    }

    @ExceptionHandler(AlreadyCancelException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult alreadyCan(HttpServletRequest request, AlreadyCancelException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("alreadyCan.code")), getMessage("alreadyCan.msg"));
    }

    @ExceptionHandler(OverCanAmtException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult overPayAmt(HttpServletRequest request, OverCanAmtException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("overPayAmt.code")), getMessage("overPayAmt.msg"));
    }

    @ExceptionHandler(OverCanVatAmtException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult overVatAmt(HttpServletRequest request, OverCanVatAmtException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("overVatAmt.code")), getMessage("overVatAmt.msg"));
    }

    // code정보에 해당하는 메시지를 조회합니다.
    private String getMessage(String code) {
        return getMessage(code, null);
    }

    // code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
