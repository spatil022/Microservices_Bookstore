package com.microusers.bookservice.services;


import com.microusers.bookservice.dto.CartDto;
import com.microusers.bookservice.dto.UpdateCartDetailDto;
import com.microusers.bookservice.model.BookCartSummary;
import com.microusers.bookservice.model.CartDetails;

import java.util.List;
import java.util.UUID;

public interface ICartService {
    String addToCart(CartDto cartDto, String token);

    List<BookCartSummary> showAllBooksInCart(String Token);

    String updateQuantityAndPrice(UpdateCartDetailDto cartDto, String token);

    String deleteCartItem(UUID id, String token);

    CartDetails setCart(String token);

}
