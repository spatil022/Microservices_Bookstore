package com.microusers.bookservice.repository;

import com.microusers.bookservice.model.BookDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<BookDetailsModel,UUID> {
  Optional<BookDetailsModel> findByBookName(String bookName);
  Optional<BookDetailsModel> findByBookId(UUID bookId);
//  Optional<BookDetailsModel>  findByAdded(boolean isAdded);

  @Modifying
  @Transactional
  @Query(value = "update book_details_model set quantity = quantity - :quantity where book_id = :bookId", nativeQuery = true)
  void updateStock(@Param("quantity") int quantity, @Param("bookId")UUID bookId);
}