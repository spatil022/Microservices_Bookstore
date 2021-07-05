package com.microusers.bookservice.services;


import com.microusers.bookservice.model.BookCartDetails;

import java.util.List;

public interface IOrderService {

    String placeAnOrder(Double totalPrice, String token);

    List<BookCartDetails> getAllOrders(String token);
}
