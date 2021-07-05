package com.microusers.bookservice.services;

import com.microusers.bookservice.dto.CouponDto;
import com.microusers.bookservice.exception.BookStoreException;
import com.microusers.bookservice.exception.CouponException;
import com.microusers.bookservice.exception.UserException;
import com.microusers.bookservice.model.Coupons;
import com.microusers.bookservice.model.CouponsDetails;
import com.microusers.bookservice.model.UserDetailsModel;
import com.microusers.bookservice.repository.CouponDetailsRepository;
import com.microusers.bookservice.repository.CouponRepository;
import com.microusers.bookservice.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CouponService implements ICouponService {
    @Autowired
    CouponRepository couponRepository;

    @Autowired
    Token jwtToken;

    @Autowired
    CouponDetailsRepository couponDetailsRepository;

    @Autowired
    private RestTemplate restTemplate;



    @Override
    public Coupons addCouponsToDatabase(CouponDto couponDto) {
        Optional<Coupons> searchByCouponTypeAndMinimumPrice =
                couponRepository.findByCouponsTypeAndAndMinimumPrice(couponDto.couponsType,couponDto.minimumPrice);
        if(searchByCouponTypeAndMinimumPrice.isPresent()){
            throw new CouponException(CouponException.ExceptionType.COUPON_ALREADY_PRESENT);
        }
        Coupons coupons = new Coupons(couponDto.getCouponsType(),
                couponDto.getDiscountPrice(),
                couponDto.getDescription(),
                couponDto.getExpireCouponDate(),
                couponDto.getMinimumPrice());
        Coupons save = couponRepository.save(coupons);
        return save;
    }

    @Override
    public List<Coupons> fetchCoupon(String token,Double totalPrice) {
        UUID userId = jwtToken.decodeJWT(token);
        List<Coupons> coupons = couponRepository.findAll();
        System.out.println("the array in the coupens "+coupons.toString());
        List<Coupons> couponsList = new ArrayList<>();
        for (Coupons coupons1 : coupons) {
            if (coupons1.minimumPrice <= totalPrice) {
                couponsList.add(coupons1);
            }
        }
        System.out.println("the array "+couponsList);
        List<CouponsDetails> couponsDetails = couponDetailsRepository.findByUserId(userId);
        System.out.println("the array in the cod "+couponsDetails);
        for (CouponsDetails couponDetails1 : couponsDetails) {
            couponsList.remove(couponDetails1.coupons);
        }
        System.out.println("the array "+couponsList);
        if (coupons.isEmpty() || couponsList.isEmpty())
            throw new CouponException(CouponException.ExceptionType.COUPON_NOT_AVAILABLE);
        return couponsList;
    }


    @Override
    public Double addCoupon(String token, String coupon, Double totalPrice) {
        UUID userId = jwtToken.decodeJWT(token);
        UserDetailsModel user = findByUserId(token);
        Optional<Coupons> coupons = couponRepository.findByCouponsType(coupon);
        if(!coupons.isPresent()){
            throw new CouponException("COUPON NOT FOUND");
        }
        CouponsDetails couponsDetails = new CouponsDetails(coupons.get(), user.getUserId());
        couponDetailsRepository.save(couponsDetails);
        Double discountPrice = (totalPrice - coupons.get().discountPrice) < 0 ? 0 : (totalPrice - coupons.get().discountPrice);
        return discountPrice;
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

