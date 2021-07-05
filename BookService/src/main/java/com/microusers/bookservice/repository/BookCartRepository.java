package com.microusers.bookservice.repository;


import com.microusers.bookservice.model.BookCartDetails;
import com.microusers.bookservice.model.CartDetails;
import com.microusers.bookservice.model.OderDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookCartRepository extends JpaRepository<BookCartDetails, UUID> {

   @Query(nativeQuery = true,value = "select * from{h-schema} book_cart_details where cart_details_id = :cartDetailsId and order_status =false")
   List<BookCartDetails> getCartItems(UUID cartDetailsId);
   List<BookCartDetails> findByCartDetailsCartIdAndOrderStatusIsFalse(UUID cartDetailsId);

   List<BookCartDetails> findByCartDetails(CartDetails cartDetails);

   Optional<BookCartDetails> findByBookDetailsModelBookId(UUID bookId);

   @Transactional
   @Modifying
   @Query(value = "update book_cart_details set order_status = true where cart_details_id = :cartDetailsId and order_status = false ", nativeQuery = true)
   int updateOrderPlacedStatus(@Param("cartDetailsId") UUID cartDetailsId);

   List<BookCartDetails> findBookCartDetailsByOrderDetails(OderDetailsModel oderDetailsModel);

}
