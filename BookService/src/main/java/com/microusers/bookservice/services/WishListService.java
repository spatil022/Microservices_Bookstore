package com.microusers.bookservice.services;


import com.microusers.bookservice.exception.BookStoreException;
import com.microusers.bookservice.model.BookDetailsModel;
import com.microusers.bookservice.model.UserDetailsModel;
import com.microusers.bookservice.model.WishList;
import com.microusers.bookservice.model.WishListItems;
import com.microusers.bookservice.repository.BookCartRepository;
import com.microusers.bookservice.repository.BookRepository;
import com.microusers.bookservice.repository.WishListItemsRepository;
import com.microusers.bookservice.repository.WishListRespository;
import com.microusers.bookservice.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WishListService implements IWishListService {

    @Autowired
    private WishListRespository wishListRespository;

    @Autowired
    private WishListItemsRepository wishListItemsRepository;

    @Autowired
    Token jwtToken;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookCartRepository bookCartRepository;

    @Autowired
    private RestTemplate restTemplate;



    @Override
    public String addToWishList(UUID book, String token) {
        UUID userId = jwtToken.decodeJWT(token);
        BookDetailsModel bookById = bookRepository.
                findById(book).
                orElseThrow(() -> new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));

        if(bookById.isAdded()==true){
            throw new BookStoreException(BookStoreException.ExceptionTypes.BOOK_ALREADY_PRESENT_IN_CART);
        }

        else {
            if (bookById.isAddedToWish() == true) {
                throw new BookStoreException(BookStoreException.ExceptionTypes.BOOK_AlREADY_PRESENT);
            } else {

                WishList wishList = getWish(token);
                WishListItems wishListItems = new WishListItems(book);
                List<WishListItems> wishListItemsList = new ArrayList<>();
                wishListItemsList.add(wishListItems);
                wishList.getWishListItems().add(wishListItems);
                wishList.setWishListItems(wishListItemsList);
                wishListRespository.save(wishList);
                wishListItems.setWishList(wishList);
                wishListItems.setBook(bookById);
                // wishListItems.setAddedToWishtDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy")));
                wishListItemsRepository.save(wishListItems);
                bookById.setAddedToWish(true);
                bookRepository.save(bookById);

                return "Book Added To Wish List Successfully";
            }
        }
    }


    private WishList getWish(String userId) {
        UserDetailsModel user = findByUserId(userId);
        UUID id = user.getUserId();


        WishList wishListDetails= wishListRespository.findByUserId(id).
                orElseThrow(() -> new BookStoreException(BookStoreException.ExceptionTypes.WISH_LIST_IS_NOT_PRESENT));

        System.out.println("cart details are "+wishListDetails);

        return wishListDetails;

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
    public WishList setWish (String token) {
        WishList wishList= new WishList();
        UserDetailsModel userDetailsModel = restTemplate.
                getForObject("http://localhost:8081/user/getuser?userEmailToken= "+token,
                        UserDetailsModel.class);
        wishList.setUserId(userDetailsModel.userId);
        wishListRespository.save(wishList);
        return wishList;
    }

    @Override
    public List<WishListItems> fetchWishList(String token) {
        UUID userId = jwtToken.decodeJWT(token);
        WishList wishList = getWish(token);
        Optional<WishList> byUserId = wishListRespository.findByUserId(wishList.getUserId());
        if(!byUserId.isPresent()){
            throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND);
        }
        List<WishListItems> wishListItemsList =wishListItemsRepository.findByWishListWishId(wishList.getWishId());
        return wishListItemsList;
    }

    @Override
    public String deleteBookFromWishList(UUID bookId, String token) {
        UUID userId = jwtToken.decodeJWT(token);
        WishList wishList = getWish(token);
        Optional<WishList> byUserId = wishListRespository.findByUserId(wishList.getUserId());
        if(!byUserId.isPresent()){
            throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND);
        }

        Optional<WishListItems> wishListItems = wishListItemsRepository.findByBookBookId(bookId);
        if(!wishListItems.isPresent()){
            throw new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND);
        }
        BookDetailsModel bookById = bookRepository.
                findById(bookId).
                orElseThrow(() -> new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));
        bookById.setAddedToWish(false);
        bookRepository.save(bookById);
        wishListItemsRepository.deleteById(wishListItems.get().getWishListItemsId());
        return "Book deleted successfully";
    }
}