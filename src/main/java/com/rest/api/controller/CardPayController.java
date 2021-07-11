package com.rest.api.controller;

import com.rest.api.entity.CardPay;
import com.rest.api.model.*;
import com.rest.api.entity.MsgCardPay;
import com.rest.api.model.response.ListResult;
import com.rest.api.repository.CardPayRepo;
import com.rest.api.repository.MsgCardPayRepo;
import com.rest.api.service.CardPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Api(tags = {"CardPay"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/pay")
public class CardPayController {
    private final CardPayRepo cardPayRepo;
    private final MsgCardPayRepo msgCardPayRepo;
    private final CardPayService cardPayService; // 결과를 처리할 Service

    /*
    @ApiOperation(value = "카드결제정보조회", notes = "카드결제정보를 조회한다")
    @GetMapping(value = "/cardList")
    public ListResult<CardPay> searchCardPayAll() {
        // 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
        return cardPayService.getListResult(cardPayRepo.findAll());
    }
    */

    @ApiOperation(value = "카드결제정보조회", notes = "카드결제정보를 조회한다")
    @GetMapping(value = "/card")
    @ResponseBody
    public ResponseSearchCardPay searchCardPay(RequestSearchCardPay requestSearchCardPay) {
        CardPay cardPayInfo = cardPayService.searchCardPay(requestSearchCardPay.getMgntNo());

        SimpleDateFormat sdf = new SimpleDateFormat("YY/MM/dd HH:mm:ss");
        String trscDt = sdf.format(cardPayInfo.getTrscDt());
        System.out.println("trscDt >>> " + trscDt);

        String[] cardInfo = cardPayInfo.getCardInfo().split("#");
        System.out.println("cardInfo >>> " + Arrays.toString(cardInfo));

        int len = cardInfo[0].length();
        String sTemp = "";
        for(int i = 0 ; i < len - 9 ; i++) {
            sTemp = sTemp + "*";
        }

        String cardNo = cardInfo[0].substring(0, 6) + sTemp + cardInfo[0].substring(len-3, len);
        if(cardNo.length() == 16) {
            cardNo = cardNo.substring(0, 4) + "-" + cardNo.substring(4, 8) + "-" + cardNo.substring(8, 12) + "-" + cardNo.substring(12);
            System.out.println("searchCardPay cardNo >>> " + cardNo);
        }

        String payTypCd = "";
        if("1".equals(cardPayInfo.getPayTypCd())) {
            payTypCd = "결제";
        } else {
            payTypCd = "취소";
        }

        String paySdCd = "";
        if("01".equals(cardPayInfo.getPayStCd())) {
            paySdCd = "정상";
        } else if("02".equals(cardPayInfo.getPayStCd())) {
            paySdCd = "전체취소";
        } else if("03".equals(cardPayInfo.getPayStCd())) {
            paySdCd = "부분취소";
        }
        
        Date nowDate = new Date();
        System.out.println("포맷 지정 전 : " + nowDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmddHHmmssSSS"); //원하는 데이터 포맷 지정
        String strNowDate = simpleDateFormat.format(nowDate); //지정한 포맷으로 변환
        System.out.println("포맷 지정 후 : " + strNowDate);

        ResponseSearchCardPay responseSearchCardPay = new ResponseSearchCardPay();
        responseSearchCardPay.setMgntNo(cardPayInfo.getMgntNo());  //승인번호
        responseSearchCardPay.setTrscDt(trscDt);  //거래일시
        responseSearchCardPay.setCardNo(cardNo);  //카드번호
        responseSearchCardPay.setValidDt(cardInfo[1]);   //카드유효기간
        responseSearchCardPay.setCvc(cardInfo[2]); //cvc
        responseSearchCardPay.setPayTypCd(payTypCd);    //결제/취소구분
        responseSearchCardPay.setPaySdCd(paySdCd);      //결제/취소/부분취소
        responseSearchCardPay.setPayAmt(cardPayInfo.getPayAmt());  //결제금액
        responseSearchCardPay.setVatAmt(cardPayInfo.getVatAmt());  //부가가치세
        responseSearchCardPay.setCanPayAmt(cardPayInfo.getCanPayAmt());  //취소금액
        responseSearchCardPay.setCanVatAmt(cardPayInfo.getCanVatAmt());  //취소부가가치세

        return responseSearchCardPay;
    }

    @ApiOperation(value = "카드결제입력", notes = "카드결제를 입력한다")
    @PostMapping(value = "/card")
    @ResponseBody
    public ResponseCardPay cardPay(@RequestBody @Valid RequestCardPay requestCardPay) {
        ResponseCardPay responseCardPay = new ResponseCardPay();

        int vatAmt = 0;
        if(requestCardPay.getVatAmt() == 0) {
            vatAmt = Math.round(requestCardPay.getPayAmt() / 11);
        } else {
            vatAmt = requestCardPay.getVatAmt();
        }

        //카드전문전송처리 => 무조건 성공
        responseCardPay = makeMsg(requestCardPay, "1"); //결제전문생성

        //가맹점번호 및 단말ID번호는 추후 처리
        String mercNo = "720194883"; //가맹점번호
        String tmlIdNo = "001"; //단말ID번호
        String cardInfo = requestCardPay.getCardNo() + "#" + requestCardPay.getValidDt() + "#" + requestCardPay.getCvc(); //카드정보

        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        System.out.println("Current Time Stamp: "+timestamp);

        CardPay cardPay = CardPay.builder()
                .mgntNo(responseCardPay.getMgntNo())
                .trscDt(timestamp)
                .mercNo(mercNo)
                .tmlIdNo(tmlIdNo)
                .cardInfo(cardInfo)
                .payTypCd("1")  //결제
                .payStCd("01")  //정상
                .itlmMmsCnt(requestCardPay.getItlmMmsCnt())
                .payAmt(requestCardPay.getPayAmt())
                .vatAmt(vatAmt)
                .build();
        cardPayRepo.save(cardPay);

        return responseCardPay;
    }

    @ApiOperation(value = "카드결제취소", notes = "카드결제를 취소한다")
    @PutMapping(value = "/card")
    @ResponseBody
    public ResponseCardPay cancelCardPay(@RequestBody @Valid RequestCardPayCancel requestCardPayCancel) {
        ResponseCardPay responseCardPay = new ResponseCardPay();
        RequestCardPay requestCardPay = new RequestCardPay();

        //결제정보 읽기
        CardPay cardPayInfo = cardPayService.searchCardPay(requestCardPayCancel.getMgntNo());

        int canVatAmt = 0;
        if(requestCardPayCancel.getVatAmt() == 0) {
            canVatAmt = Math.round(requestCardPayCancel.getPayAmt() / 11);
            requestCardPayCancel.setVatAmt(canVatAmt);
        } else {
            canVatAmt = requestCardPay.getVatAmt();
        }

        //기 취소된 금액과 해당 거래에서 취소하려는 금액 합이 결제금액과 같으면 부가가치세는 기존에 남아 있는 금액으로 처리함
        if(cardPayInfo.getPayAmt() == (cardPayInfo.getCanPayAmt() + requestCardPayCancel.getPayAmt())) {
            canVatAmt = cardPayInfo.getVatAmt() - cardPayInfo.getCanVatAmt();
            requestCardPayCancel.setVatAmt(canVatAmt);
        }

        System.out.println("getMgntNo >>> " + requestCardPayCancel.getMgntNo());

        //취소전문생성
        String[] cardInfo = cardPayInfo.getCardInfo().split("#");

        System.out.println("cardPayInfo >>> " + Arrays.toString(cardInfo));

        requestCardPay.setCardNo(cardInfo[0]);  //카드번호
        requestCardPay.setValidDt(cardInfo[1]);  //카드번호
        requestCardPay.setCvc(cardInfo[2]);  //카드번호
        requestCardPay.setPayAmt(requestCardPayCancel.getPayAmt()); //취소금액
        requestCardPay.setVatAmt(canVatAmt); //부가가치세
        requestCardPay.setItlmMmsCnt("00"); //할부개월수

        //전문은 무조건 성공
        responseCardPay = makeMsg(requestCardPay, "2"); //취소전문생성

        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        System.out.println("Current Time Stamp: "+timestamp);

        CardPay cardPay = CardPay.builder()
                .mgntNo(responseCardPay.getMgntNo())
                .trscDt(timestamp)
                .ognMgntNo(requestCardPayCancel.getMgntNo())
                .mercNo(cardPayInfo.getMercNo())
                .tmlIdNo(cardPayInfo.getTmlIdNo())
                .cardInfo(cardPayInfo.getCardInfo())
                .payTypCd("2")  //취소
                .payStCd("01")  //정상
                .itlmMmsCnt("00")
                .payAmt(requestCardPayCancel.getPayAmt())
                .vatAmt(canVatAmt)
                .build();
        cardPayRepo.save(cardPay);


        //취소 이후 원거래 업데이트
        cardPayService.cancelcardPay(cardPayInfo, requestCardPayCancel);

        return responseCardPay;
    }

    private ResponseCardPay makeMsg(RequestCardPay requestCardPay, String payTypCd) {
        ResponseCardPay responseCardPay = new ResponseCardPay();
        
        int vatAmt = 0;
        if(requestCardPay.getPayAmt() == 0) {
            vatAmt = Math.round(requestCardPay.getPayAmt() / 11);
        } else {
            vatAmt = requestCardPay.getVatAmt();
        }

        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmddHHmmssSSS");
        String strNowDate   = simpleDateFormat.format(nowDate);
        String mgntNo       = strNowDate + "001";   //관리번호를 현재시간 + 001로 처리

        String cardInfo = requestCardPay.getCardNo() + "#" + requestCardPay.getValidDt() + "#" + requestCardPay.getCvc(); //카드정보

        System.out.println("makeMsg cardNo >>> " + requestCardPay.getCardNo());

        //카드사 전문
        //전문전송 데이터는 String이므로 모든 데이터타입을 String으로 처리후 합침
        String msgConts = "";

        //결제, 취소 구분에 따른 데이터 값 설정
        String dataType = "";
        if("1".equals(payTypCd)) {
            dataType = "PAYMENT"; //결제
        } else {
            dataType = "CANCEL"; //취소
        }

        //공통헤더부문
        msgConts = setLPad("446", 4, " ")   //데이터길이
                +   setRPad(dataType, 10, " ")      //데이터구분
                +   mgntNo;                             //관리번호를 현재시간 + 001로 처리한다

        //데이터부문
        msgConts = msgConts
                +   setRPad(requestCardPay.getCardNo(), 20, " ")    //카드번호
                +   setLPad(requestCardPay.getItlmMmsCnt(), 2, "0") //할부개월수
                +   setRPad(requestCardPay.getValidDt(), 2, " ")
                +   setRPad(requestCardPay.getCvc(), 2, " ")
                +   setLPad(Integer.toString(requestCardPay.getPayAmt()), 10, " ")  //거래금액
                +   setLPad(Integer.toString(vatAmt), 10, "0")  //부가가치세
                +   setRPad("", 20, " ")    //원거래관리번호
                +   setRPad(cardInfo, 300, " ")      //카드정보
                +   setRPad("", 47, " ");   //예비필드

        System.out.println("msgCard >>> " + msgConts);
        System.out.println("msgCard.length >>> " + msgConts.length());

        //전문저장
        MsgCardPay msgCardPay = MsgCardPay.builder()
                .mgntNo(mgntNo)
                .msgConts(msgConts)
                .msgStCd("1")  //정상
                .errCd("")
                .errConts("")
                .build();
        msgCardPayRepo.save(msgCardPay);

        responseCardPay.setMgntNo(mgntNo);
        responseCardPay.setMsgConts(msgConts);

        return responseCardPay;
    }

    // LPAD
    private static String setLPad(String strContext, int iLen, String strChar) {
        String strResult = "";
        StringBuilder sbAddChar = new StringBuilder();

        for( int i = strContext.length(); i < iLen; i++ ) {
            sbAddChar.append(strChar);
            strResult = sbAddChar + strContext;
        }
        return strResult;
    }

    // RPAD
    private static String setRPad(String strContext, int iLen, String strChar) {
        String strResult = "";
        StringBuilder sbAddChar = new StringBuilder();

        for( int i = strContext.length(); i < iLen; i++ ) {
            sbAddChar.append(strChar);
            strResult = strContext + sbAddChar;
        }
        return strResult;
    }
}