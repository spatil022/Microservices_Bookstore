package com.microusers.bookservice.services;

import com.microusers.bookservice.exception.BookStoreException;
import com.microusers.bookservice.model.*;
import com.microusers.bookservice.repository.*;
import com.microusers.bookservice.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService{

    @Autowired
    Token jwtToken;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Autowired
    BookCartRepository bookCartRepository;


    @Autowired
    CartRepository cartRepository;

    @Autowired
    CustomerDetailsRepository customerDetailsRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String placeAnOrder(Double totalPrice, String token) {
        UUID userId = jwtToken.decodeJWT(token);
        CartDetails cartDetails =getCart(token);
        List<BookCartDetails> cartBooks = bookCartRepository.findByCartDetailsCartIdAndOrderStatusIsFalse(cartDetails.getCartId());
        CustomerDetails customerDetails=customerDetailsRepository.findByUserId(cartDetails.getUserId()).get(0);
        Integer orderId = generateOrderId();
        OderDetailsModel oderDetailsModel =new OderDetailsModel(orderId,totalPrice,LocalDate.now(),cartDetails,cartDetails.getUserId(),
                customerDetails,cartBooks);
        OderDetailsModel saveOrder = orderDetailsRepository.save(oderDetailsModel);
        cartBooks.forEach(cartBook -> {
            cartBook.setOrderDetails(oderDetailsModel);
            cartBook.setOrderStatus(true);

            bookRepository.updateStock(cartBook.getQuantity(),cartBook.getBookDetailsModel().bookId);
        });

        cartBooks.forEach(cartBook -> {
            BookDetailsModel searchBook = bookRepository.
                    findById(cartBook.getBookDetailsModel().bookId).
                    orElseThrow(() -> new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));

            int bookQuantity = cartBook.getQuantity();
            int quantityCartOfBook = searchBook.getQuantity();
            searchBook.setQuantity(quantityCartOfBook-bookQuantity);
            searchBook.setAdded(false);
            bookRepository.save(searchBook);

        });
        bookCartRepository.updateOrderPlacedStatus(cartDetails.getCartId());
        return "hurray !!! your order of order id "+saveOrder.getOrderId()+ "is successfull";
    }

    private int generateOrderId(){
        boolean isUnique = false;
        Integer orderId = 0;

        while (!isUnique){
            orderId = (int) Math.floor(100000 + Math.random() * 999999);
            Optional<OderDetailsModel> detailsModel = orderDetailsRepository.findByOrderId(orderId);
            if( !detailsModel.isPresent())
                isUnique = true;

        }
        return orderId;

    }

    @Override
    public List<BookCartDetails> getAllOrders(String token) {
        UUID userId = jwtToken.decodeJWT(token);
        UserDetailsModel user = findByUserId(token);

        List<OderDetailsModel> oderDetailsModelByUser = orderDetailsRepository.findOderDetailsModelByUserId(user.getUserId());
        List detailsList = new ArrayList<>();
        List<BookCartDetails> detailsListOfOrder = new ArrayList<>();

        for (OderDetailsModel order : oderDetailsModelByUser){
            OderDetailsModel orderDetails = orderDetailsRepository.findByOrderId(order.orderId).get();

            detailsListOfOrder=bookCartRepository.findBookCartDetailsByOrderDetails(orderDetails);
            List<BookCartSummary> collectBookCartDetails = detailsListOfOrder.stream()
                    .map(summary -> new BookCartSummary(summary)).collect(Collectors.toList());
            detailsList.add(collectBookCartDetails);


        }
        System.out.println(detailsList);


        return detailsList;
    }

    private CartDetails getCart(String userId) {
        UserDetailsModel user = findByUserId(userId);
        UUID id = user.getUserId();


        CartDetails cartDetails= cartRepository.findByUserId(id).
                orElseThrow(() -> new BookStoreException(BookStoreException.ExceptionTypes.CART_NOT_PRESENT));

        System.out.println("cart details are "+cartDetails);

        return cartDetails;

    }



    private UserDetailsModel findByUserId(String userId) {
        UserDetailsModel userDetailsModel = restTemplate.
                getForObject("http://localhost:8081/user/getuser?userEmailToken= "+userId,
                        UserDetailsModel.class);

        if(userDetailsModel == null){
            throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND);
        }

        return userDetailsModel;
    }

}
