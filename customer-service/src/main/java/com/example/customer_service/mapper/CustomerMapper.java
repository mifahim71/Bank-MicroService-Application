package com.example.customer_service.mapper;

import com.example.customer_service.dto.CustomerCreateResponseDto;
import com.example.customer_service.dto.CustomerResponseDto;
import com.example.customer_service.dto.CustomerUpdateResponseDto;
import com.example.customer_service.entities.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public CustomerCreateResponseDto toCustomerCreateResponseDto(Customer customer){

        CustomerCreateResponseDto responseDto = new CustomerCreateResponseDto();

        responseDto.setCustomerId(customer.getId().toString());
        responseDto.setEmail(customer.getEmail());
        responseDto.setUserId(customer.getUserId());
        responseDto.setName(customer.getName());
        responseDto.setPhone(customer.getPhone());
        responseDto.setAddress(customer.getAddress());

        return responseDto;
    }

    public CustomerResponseDto toCustomerResponseDto(Customer customer){

        CustomerResponseDto responseDto = new CustomerResponseDto();

        responseDto.setCustomerId(customer.getId().toString());
        responseDto.setEmail(customer.getEmail());
        responseDto.setUserId(customer.getUserId());
        responseDto.setName(customer.getName());
        responseDto.setPhone(customer.getPhone());
        responseDto.setAddress(customer.getAddress());

        return responseDto;
    }

    public CustomerUpdateResponseDto toCustomerUpdateResponseDto(Customer customer){

        CustomerUpdateResponseDto responseDto = new CustomerUpdateResponseDto();

        responseDto.setEmail(customer.getEmail());
        responseDto.setName(customer.getName());
        responseDto.setPhone(customer.getPhone());
        responseDto.setAddress(customer.getAddress());
        responseDto.setDob(customer.getDateOfBirth().toString());

        return responseDto;
    }
}
