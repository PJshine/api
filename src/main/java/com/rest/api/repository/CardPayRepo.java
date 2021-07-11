package com.rest.api.repository;

import com.rest.api.entity.CardPay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardPayRepo extends JpaRepository<CardPay, String> {
    CardPay findByMgntNo(String mgntNo);
}