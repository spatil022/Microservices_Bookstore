package com.microusers.bookservice.repository;

import com.microusers.bookservice.model.BookCartDetails;
import com.microusers.bookservice.model.CartDetails;
import com.microusers.bookservice.model.UserDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<CartDetails,UUID> {
    List<CartDetails> findByBookCartDetails(BookCartDetails bookCartDetails);
    Optional<CartDetails> findByUserId(UUID id);

}


