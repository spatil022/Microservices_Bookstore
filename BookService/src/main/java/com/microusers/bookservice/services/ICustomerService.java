package com.microusers.bookservice.services;


import com.microusers.bookservice.dto.CustomerDetailsDto;
import com.microusers.bookservice.model.CustomerDetails;

import java.util.List;

public interface ICustomerService {
    CustomerDetails addCustomerDetails(CustomerDetailsDto customerDetailsDto, String token);

    List<CustomerDetails> getAllCustumerDetails(String token);
}
