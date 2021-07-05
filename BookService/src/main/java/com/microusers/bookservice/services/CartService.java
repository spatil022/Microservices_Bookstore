package com.microusers.bookservice.services;

import com.microusers.bookservice.dto.CartDto;
import com.microusers.bookservice.dto.UpdateCartDetailDto;
import com.microusers.bookservice.exception.BookStoreException;
import com.microusers.bookservice.model.*;
import com.microusers.bookservice.repository.BookCartRepository;
import com.microusers.bookservice.repository.BookRepository;
import com.microusers.bookservice.repository.CartRepository;
import com.microusers.bookservice.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService {


    Token jwtToken= new Token();

//    @Autowired
//    UserDetailsRepository userDetailsRepository;

    @Autowired
    BookCartRepository bookCartRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    private RestTemplate restTemplate;



    @Override
    public String addToCart(CartDto cartDto, String token) {
        UUID userId = jwtToken.decodeJWT(token);

        BookDetailsModel bookById = bookRepository.
                findById(cartDto.getBookId()).
                orElseThrow(() -> new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));

        if(bookById.isAdded()==true){
            throw new BookStoreException(BookStoreException.ExceptionTypes.BOOK_AlREADY_PRESENT);
        }

        else {
            CartDetails cartDetails = getCart(token);
            BookCartDetails bookCartDetails = new BookCartDetails(cartDto);
            List<BookCartDetails> cartList = new ArrayList<>();

            cartList.add(bookCartDetails);
            cartDetails.getBookCartDetails().add(bookCartDetails);
            cartDetails.setBookCartDetails(cartList);
            cartRepository.save(cartDetails);
            bookCartDetails.setCartDetails(cartDetails);
            bookCartDetails.setBookDetailsModel(bookById);
            bookCartRepository.save(bookCartDetails);
            bookById.setAdded(true);
            bookRepository.save(bookById);
            return "Book Added To Cart Successfully";


        }
    }





    @Override
    public List<BookCartSummary> showAllBooksInCart(String Token) {
        UUID userId = jwtToken.decodeJWT(Token);
        System.out.println("the token present uuid "+userId);
        CartDetails cartDetails = getCart(Token);

        Optional<CartDetails> byUserId = cartRepository.findByUserId(cartDetails.getUserId());
        if(!byUserId.isPresent()){
            throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND);
        }
        System.out.println("cart details after fetching "+cartDetails);
        List<BookCartDetails> bookCartDetails = bookCartRepository.findByCartDetailsCartIdAndOrderStatusIsFalse(cartDetails.getCartId());
        bookCartDetails.stream().forEach(bookCartDetails1 -> System.out.println(bookCartDetails1.toString()));
        List<BookCartSummary> collectBookCartDetails = bookCartDetails.stream()
                .map(summary -> new BookCartSummary(summary)).collect(Collectors.toList());
        System.out.println(bookCartDetails);

        return collectBookCartDetails;
    }

    @Override
    public String updateQuantityAndPrice(UpdateCartDetailDto cartDto, String token) {


        UUID userId = jwtToken.decodeJWT(token);
//        Optional<UserDetailsModel> findTheExistedUser = userDetailsRepository.findById(userId);

        CartDetails cartDetails = getCart(token);

        Optional<CartDetails> byUserId = cartRepository.findByUserId(cartDetails.getUserId());
        if(!byUserId.isPresent()){
            throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND);
        }
        BookDetailsModel bookDetailsModel =new BookDetailsModel();
        BookCartDetails bookCartDetails = bookCartRepository.findByBookDetailsModelBookId(cartDto.getBookId()).
                orElseThrow(() -> new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));
        bookCartDetails.setQuantity(cartDto.getQuantity());
        bookCartDetails.setTotalPrice(cartDto.getTotalPrice());
        bookCartRepository.save(bookCartDetails);
        return "Quantity of book and its price has updated";


    }

    @Override
    public String deleteCartItem(UUID id, String token) {
        UUID userId = jwtToken.decodeJWT(token);
        System.out.println("the token present uuid "+userId);
        CartDetails cartDetails = getCart(token);

        Optional<CartDetails> byUserId = cartRepository.findByUserId(cartDetails.getUserId());
        if(!byUserId.isPresent()){
            throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND);
        }

        Optional<BookCartDetails> findbookById = bookCartRepository.findByBookDetailsModelBookId(id);
        if (!findbookById.isPresent()){
            throw new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND);
        }

        BookDetailsModel bookById = bookRepository.
                findById(id).
                orElseThrow(() -> new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));

        bookById.setAdded(false);
        bookRepository.save(bookById);
        bookCartRepository.deleteById(findbookById.get().BookCartDetailsId);



        return "book is removed from cart";
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


    @Override
    public CartDetails setCart(String token) {
        CartDetails cartDetails = new CartDetails();
        UserDetailsModel userDetailsModel = restTemplate.
                getForObject("http://localhost:8081/user/getuser?userEmailToken= "+token,
                        UserDetailsModel.class);
        System.out.println("user id "+userDetailsModel.userId);
        cartDetails.setUserId(userDetailsModel.userId);

        cartRepository.save(cartDetails);
        return cartDetails;
    }


}