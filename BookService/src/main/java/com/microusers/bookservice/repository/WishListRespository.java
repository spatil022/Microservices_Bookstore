package com.microusers.bookservice.repository;

import com.microusers.bookservice.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishListRespository extends JpaRepository<WishList, UUID> {

    Optional<WishList> findByUserId(UUID id);

}
