package com.microusers.bookservice.repository;

import com.microusers.bookservice.model.OderDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OderDetailsRepository extends JpaRepository<OderDetailsModel, UUID> {
    List<OderDetailsModel> findByUserId(UUID userId);
    Optional<OderDetailsModel> findByOrderId(Integer orderId);
}
