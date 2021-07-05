package com.microusers.bookservice.services;

import com.microusers.bookservice.model.WishList;
import com.microusers.bookservice.model.WishListItems;

import java.util.List;
import java.util.UUID;

public interface IWishListService {

    String addToWishList(UUID bookId, String token);
    List<WishListItems> fetchWishList(String token);
    String deleteBookFromWishList(UUID wishListId, String token);
    WishList setWish(String token);
}
