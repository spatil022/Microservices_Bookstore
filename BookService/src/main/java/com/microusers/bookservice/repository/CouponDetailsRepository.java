package com.microusers.bookservice.repository;

import com.microusers.bookservice.model.CouponsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface CouponDetailsRepository extends JpaRepository<CouponsDetails, UUID> {
    List<CouponsDetails> findByUserId(UUID UserId);
}

