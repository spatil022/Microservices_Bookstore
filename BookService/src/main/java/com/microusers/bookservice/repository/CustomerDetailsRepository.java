package com.microusers.bookservice.repository;

import com.microusers.bookservice.model.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, UUID> {
    List<CustomerDetails> findByUserId(UUID userId);

}
