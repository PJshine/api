package com.rest.api.repository;

import com.rest.api.entity.MsgCardPay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MsgCardPayRepo extends JpaRepository<MsgCardPay, String> {
    MsgCardPay findByMgntNo(String mgntNo);
}
