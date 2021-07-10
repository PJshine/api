package com.rest.api.controller;

import com.rest.api.entity.CardPay;
import com.rest.api.exception.CardPayException;
import com.rest.api.model.*;
import com.rest.api.repository.CardPayRepo;
import com.rest.api.service.CardPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Api(tags = {"CardPay"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/pay")
public class CardPayController {
    private final CardPayRepo cardPayRepo;
    private final CardPayService cardPayService; // 결과를 처리할 Service

    @ApiOperation(value = "카드결제정보조회", notes = "카드결제정보를 조회한다")
    @GetMapping(value = "/cardList")
    public ListResult<CardPay> searchCardPayAll() {
        // 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
        return cardPayService.getListResult(cardPayRepo.findAll());
    }

    @ApiOperation(value = "카드결제정보조회", notes = "카드결제정보를 조회한다")
    @GetMapping(value = "/card")
    public SingleResult<CardPay> searchCardPay(@ApiParam(value = "거래관리번호", required = true) @RequestParam String trscMgntNo) {
        // 결과데이터가 단일건인경우 getBasicResult를 이용해서 결과를 출력한다.
        return cardPayService.getSingleResult(cardPayRepo.findByTrscMgntNo(trscMgntNo).orElseThrow(CardPayException::new));
    }

    @ApiOperation(value = "카드결제입력", notes = "카드결제를 입력한다")
    @PostMapping(value = "/card")
    public CardPay cardPay(@ApiParam(value = "거래관리번호", required = true) @RequestParam String trscMgntNo,
                        @ApiParam(value = "거래일시") @RequestParam Timestamp trscDt,
                        @ApiParam(value = "원거래관리번호") @RequestParam String ognTrscMgntNo,
                        @ApiParam(value = "거래점번호") @RequestParam String mercNo,
                        @ApiParam(value = "단말ID번호") @RequestParam String tmlIdNo,
                        @ApiParam(value = "카드정보", required = true) @RequestParam String cardInfo,
                        @ApiParam(value = "결제유형코드") @RequestParam String payTypCd,
                        @ApiParam(value = "결제상태코드") @RequestParam String payStCd,
                        @ApiParam(value = "카드할부개월수", required = true) @RequestParam String itlmMmsCnt,
                        @ApiParam(value = "결제금액") @RequestParam int payAmt,
                        @ApiParam(value = "부가가치세") @RequestParam int vatAmt,
                        @ApiParam(value = "취소금액") @RequestParam int canAmt,
                        @ApiParam(value = "취소부가가치세") @RequestParam int canVatAmt) {
        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        System.out.println("Current Time Stamp: "+timestamp);
        CardPay cardPay = CardPay.builder()
                .trscMgntNo("1234567890")
                .trscDt(timestamp)
                .ognTrscMgntNo("11111")
                .mercNo("22222")
                .tmlIdNo("3333")
                .cardInfo("cardinfo")
                .payTypCd("1")
                .payStCd("01")
                .itlmMmsCnt("0")
                .payAmt(5)
                .vatAmt(1)
                .canAmt(5)
                .canVatAmt(1)
                .build();
        return cardPayRepo.save(cardPay);
    }

    @ApiOperation(value = "카드결제취소", notes = "카드결제를 취소한다")
    @PutMapping(value = "/card")
    public SingleResult<CardPay> cancelCardPay(@ApiParam(value = "원거래관리번호", required = true) @RequestParam String ognTrscMgntNo,
                                               @ApiParam(value = "취소금액", required = true) @RequestParam int canAmt,
                                               @ApiParam(value = "취소부가가치세") @RequestParam int canVatAmt) {
        System.out.println("ognTrscMgntNo >>>" + ognTrscMgntNo);
        System.out.println("canAmt >>>" + canAmt);
        System.out.println("canVatAmt >>>" + canVatAmt);

        if (canVatAmt == 0) {
            canVatAmt = Math.round(canAmt / 11);
        }

        //가맹점번호 및 단말ID번호는 추후 처리
        String mercNo = "1111111111";
        String tmlIdNo = "2222222222";

        CardPay cardPay = CardPay.builder()
                .trscMgntNo("1234567890")
                .ognTrscMgntNo(ognTrscMgntNo)
                .canAmt(canAmt)
                .canVatAmt(canVatAmt)
                .mercNo(mercNo)
                .tmlIdNo(tmlIdNo)
                .build();
        return cardPayService.getSingleResult(cardPayRepo.save(cardPay));
    }

    public CardPay insertCardPay(@ApiParam(value = "거래관리번호", required = true) @RequestParam String trscMgntNo,
                                 @ApiParam(value = "거래일시") @RequestParam Timestamp trscDt,
                                 @ApiParam(value = "원거래관리번호") @RequestParam String ognTrscMgntNo,
                                 @ApiParam(value = "거래점번호") @RequestParam String mercNo,
                                 @ApiParam(value = "단말ID번호") @RequestParam String tmlIdNo,
                                 @ApiParam(value = "카드정보", required = true) @RequestParam String cardInfo,
                                 @ApiParam(value = "결제유형코드") @RequestParam String payTypCd,
                                 @ApiParam(value = "결제상태코드") @RequestParam String payStCd,
                                 @ApiParam(value = "카드할부개월수", required = true) @RequestParam String itlmMmsCnt,
                                 @ApiParam(value = "결제금액") @RequestParam int payAmt,
                                 @ApiParam(value = "부가가치세") @RequestParam int vatAmt,
                                 @ApiParam(value = "취소금액") @RequestParam int canAmt,
                                 @ApiParam(value = "취소부가가치세") @RequestParam int canVatAmt) {
        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        System.out.println("Current Time Stamp: "+timestamp);
        CardPay cardPay = CardPay.builder()
                .trscMgntNo("1234567890")
                .trscDt(timestamp)
                .ognTrscMgntNo("11111")
                .mercNo("22222")
                .tmlIdNo("3333")
                .cardInfo("cardinfo")
                .payTypCd("1")
                .payStCd("01")
                .itlmMmsCnt("0")
                .payAmt(5)
                .vatAmt(1)
                .canAmt(5)
                .canVatAmt(1)
                .build();
        return cardPayRepo.save(cardPay);
    }
}