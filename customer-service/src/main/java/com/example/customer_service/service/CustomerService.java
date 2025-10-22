package com.example.customer_service.service;

import com.example.customer_service.dto.*;
import com.example.customer_service.entities.Customer;
import com.example.customer_service.exceptions.CustomerIdNotFoundException;
import com.example.customer_service.exceptions.DuplicateUserIdException;
import com.example.customer_service.exceptions.UnauthorizedAccessException;
import com.example.customer_service.mapper.CustomerMapper;
import com.example.customer_service.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerCreateResponseDto createCustomer(@Valid CustomerCreateRequestDto requestDto, String userId, String email) {

        if(customerRepository.existsByUserId(userId)){
            throw new DuplicateUserIdException("User Id already exists");
        }

        Customer createdCustomer = Customer.builder()
                .userId(userId)
                .name(requestDto.getName())
                .address(requestDto.getAddress())
                .phone(requestDto.getPhone())
                .dateOfBirth(LocalDate.parse(requestDto.getDob()))
                .createdAt(LocalDateTime.now())
                .email(email)
                .build();

        customerRepository.save(createdCustomer);

        return customerMapper.toCustomerCreateResponseDto(createdCustomer);
    }



    public CustomerResponseDto getCustomerById(UUID id) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerIdNotFoundException("Customer with this id: " + id.toString() + ", isn't available"));

        return customerMapper.toCustomerResponseDto(customer);
    }


    public CustomerResponseDto getCustomerByUserId(String userId) {

        Customer customer = customerRepository.findByUserId(userId).orElseThrow(() -> new CustomerIdNotFoundException("Customer with this User id: " + userId + ", isn't available"));

        return customerMapper.toCustomerResponseDto(customer);
    }

    public List<CustomerResponseDto> findAll(String role) {
        if(!role.equals("ADMIN")){
            throw new UnauthorizedAccessException("Access Denied");
        }

        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customerMapper::toCustomerResponseDto).toList();
    }



    public CustomerUpdateResponseDto updateCustomer(CustomerUpdateRequestDto requestDto, String userId) {

        Customer customer = customerRepository.findByUserId(userId).orElseThrow(() -> new CustomerIdNotFoundException("Customer with this User id: " + userId + ", isn't available"));

        if(requestDto.getAddress() != null && !requestDto.getAddress().isEmpty()){
            customer.setAddress(requestDto.getAddress());
        }

        if(requestDto.getName() != null && !requestDto.getName().isEmpty()){
            customer.setName(requestDto.getName());
        }

        if(requestDto.getPhone() != null && !requestDto.getPhone().isEmpty()){
            customer.setPhone(requestDto.getPhone());
        }

        if(requestDto.getDob() != null && !requestDto.getDob().isEmpty()){
            customer.setDateOfBirth(LocalDate.parse(requestDto.getDob()));
        }

        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toCustomerUpdateResponseDto(savedCustomer);
    }

    @Transactional
    public void deleteCustomer(String userId) {

        Customer customer = customerRepository.findByUserId(userId).orElseThrow(() -> new CustomerIdNotFoundException("Customer with this User id: " + userId + ", isn't available"));

        //Work to do
        customerRepository.delete(customer);

    }
}
