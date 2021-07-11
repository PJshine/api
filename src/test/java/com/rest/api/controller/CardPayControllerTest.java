package com.rest.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.model.RequestCardPay;
import com.rest.api.model.RequestCardPayCancel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CardPayControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    public void searchCardPay() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("mgntNo", "20212611182615827001");

        final ResultActions resultActions = mockMvc.perform(get("/pay/card").params(params))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void cardPay() throws Exception {
        RequestCardPay requestCardPay = new RequestCardPay();
        requestCardPay.setCardNo("1111222233334444");
        requestCardPay.setCvc("111");
        requestCardPay.setItlmMmsCnt("0");
        requestCardPay.setValidDt("1025");
        requestCardPay.setPayAmt(11000);
        requestCardPay.setVatAmt(0);

        final ResultActions resultActions = mockMvc.perform(post("/pay/card")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(requestCardPay)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void cancelCardPay() throws Exception {
        RequestCardPayCancel requestCardPayCancel = new RequestCardPayCancel();
        requestCardPayCancel.setMgntNo("20212611182615827001");
        requestCardPayCancel.setPayAmt(11000);
        requestCardPayCancel.setVatAmt(0);

        final ResultActions resultActions = mockMvc.perform(put("/pay/card")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(requestCardPayCancel)))
                .andDo(print())
                .andExpect(status().isOk());
        }
}