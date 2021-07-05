package com.microusers.bookservice.services;

import com.microusers.bookservice.dto.CustomerDetailsDto;
import com.microusers.bookservice.exception.BookStoreException;
import com.microusers.bookservice.model.CustomerDetails;
import com.microusers.bookservice.model.UserDetailsModel;
import com.microusers.bookservice.repository.CustomerDetailsRepository;
import com.microusers.bookservice.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService implements ICustomerService{
    @Autowired
    Token jwtToken;

//    @Autowired
//    UserDetailsRepository userDetailsRepository;

    @Autowired
    CustomerDetailsRepository customerDetailsRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CustomerDetails addCustomerDetails(CustomerDetailsDto customerDetailsDto, String token) {
        UUID userId = jwtToken.decodeJWT(token);

        UserDetailsModel findTheExistedUser = findByUserId(token);

        if (findTheExistedUser == null){
            throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND);
        }

        List<CustomerDetails> listOfCustomerDetails = new ArrayList<>();
        CustomerDetails customerDetails = new CustomerDetails(customerDetailsDto.pinCode,
                customerDetailsDto.locality,
                customerDetailsDto.address,
                customerDetailsDto.city,
                customerDetailsDto.landmark,
                customerDetailsDto.addressType);
        customerDetails.setUserId(findTheExistedUser.getUserId());

        listOfCustomerDetails.add(customerDetails);
        CustomerDetails save = customerDetailsRepository.save(customerDetails);
        return save;
    }

    @Override
    public List<CustomerDetails> getAllCustumerDetails(String token) {
        UUID userId = jwtToken.decodeJWT(token);

        UserDetailsModel findTheExistedUser = findByUserId(token);

        List<CustomerDetails> customerDetailsbyUserDetails = customerDetailsRepository.findByUserId(findTheExistedUser.getUserId());
        return customerDetailsbyUserDetails;

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
