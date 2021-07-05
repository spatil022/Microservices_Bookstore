package com.microusers.userservice.repository;

import com.microusers.userservice.model.UserDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsModel, UUID> {
    Optional<UserDetailsModel> findByEmailID(String emailID);
}
