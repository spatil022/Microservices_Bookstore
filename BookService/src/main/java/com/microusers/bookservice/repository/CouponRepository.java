package com.microusers.bookservice.repository;


import com.microusers.bookservice.model.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CouponRepository extends JpaRepository<Coupons, UUID> {
    Optional<Coupons> findByCouponsType(String coupons);
    Optional<Coupons> findByCouponsTypeAndAndMinimumPrice(String couponType, Double minimumPrice);
}