package com.microusers.bookservice.repository;

import com.microusers.bookservice.model.WishListItems;
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
public interface WishListItemsRepository extends JpaRepository<WishListItems, UUID> {


      List<WishListItems> findAllByWishListWishId(UUID wishListId);
      List<WishListItems> findByWishListWishId(UUID wishListId);
      Optional<WishListItems> findByBookBookId(UUID bookId);

      @Transactional
      @Modifying
      @Query(value = "delete from wishlistitems where book_id = :bookId and  wish_list_id = :wishListId", nativeQuery = true)
      Integer deleteWishItems(@Param("bookId") UUID bookId, @Param("wishListId") UUID wishListId);

//    List<WishListItems> findByBookIdAndWishListWishId(UUID bookId, UUID wishListId);

}
