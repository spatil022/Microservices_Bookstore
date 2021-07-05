package com.microusers.bookservice.repository;


import com.microusers.bookservice.model.OderDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OderDetailsModel, UUID> {
     List<OderDetailsModel> findOderDetailsModelByUserId(UUID userId);
     Optional<OderDetailsModel> findByOrderId(Integer orderId);
}

