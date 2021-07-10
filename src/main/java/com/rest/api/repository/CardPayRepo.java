package com.rest.api.repository;

import com.rest.api.entity.CardPay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardPayRepo  extends JpaRepository<CardPay, String> {
    Optional<CardPay> findByTrscMgntNo(String trscMgntNo);
}