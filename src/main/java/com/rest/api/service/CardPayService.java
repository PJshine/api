package com.rest.api.service;

import com.rest.api.advice.exception.AlreadyCancelException;
import com.rest.api.advice.exception.OverCanAmtException;
import com.rest.api.advice.exception.OverCanVatAmtException;
import com.rest.api.entity.CardPay;
import com.rest.api.advice.exception.CardPayException;
import com.rest.api.model.response.CommonResult;
import com.rest.api.model.response.ListResult;
import com.rest.api.model.RequestCardPayCancel;
import com.rest.api.model.SingleResult;
import com.rest.api.repository.CardPayRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CardPayService {
    private final CardPayRepo cardPayRepo;

    //관리번호로 결제정보 읽기
    public CardPay searchCardPay(String mgntNo) {
        return Optional.ofNullable(cardPayRepo.findByMgntNo(mgntNo)).orElseThrow(CardPayException::new);
    }

    //결제정보 취소
    public CardPay cancelcardPay(CardPay cardPay, RequestCardPayCancel requestCardPayCancel) {
        String payStCd  = ""; //취소
        
        //이미 취소 됨
        if("02".equals(cardPay.getPayStCd())) {
            throw new AlreadyCancelException("1003");
        }
        
        //남은 결제금액보다 취소 금액이 크면 에러
        if(cardPay.getPayAmt() - cardPay.getCanPayAmt() < requestCardPayCancel.getPayAmt()) {
            throw new OverCanAmtException("1001");
        } else if(cardPay.getPayAmt() - cardPay.getCanPayAmt() == requestCardPayCancel.getPayAmt()) {
            payStCd = "02"; //전체취소
        } else if(cardPay.getPayAmt() - cardPay.getCanPayAmt() > requestCardPayCancel.getPayAmt()) {
            payStCd = "03"; //부분취소
        }

        if(cardPay.getVatAmt() - cardPay.getCanVatAmt() < requestCardPayCancel.getVatAmt()) {
            throw new OverCanVatAmtException("1002");
        }

        int canPayAmt = cardPay.getCanPayAmt() + requestCardPayCancel.getPayAmt();
        int canVatAmt = cardPay.getCanVatAmt() + requestCardPayCancel.getVatAmt();

        cardPay.setCancel(requestCardPayCancel.getMgntNo(), payStCd, canPayAmt, canVatAmt);
        return  cardPay;
    }
}