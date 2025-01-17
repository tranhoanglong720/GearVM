package com.gearvmstore.GearVM.service;

import com.gearvmstore.GearVM.model.Customer;
import com.gearvmstore.GearVM.repository.CustomerRepository;
import com.gearvmstore.GearVM.utility.HashPasswordUtil;
import com.gearvmstore.GearVM.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final HashPasswordUtil hashPasswordUtil;
    private final JwtUtil jwtUtil;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, HashPasswordUtil hashPasswordUtil, JwtUtil jwtUtil) {
        this.customerRepository = customerRepository;
        this.hashPasswordUtil = hashPasswordUtil;
        this.jwtUtil = jwtUtil;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> getCustomersByFilter(String id, String name, String phoneNumber, String email) {
        try {
            return customerRepository.findDistinctByIdEqualsOrNameContainingIgnoreCaseOrPhoneNumberContainingIgnoreCaseOrEmailContainingIgnoreCaseOrderByIdAsc(Long.parseLong(id), name, phoneNumber, email);
        } catch (NumberFormatException e) {
            return customerRepository.findDistinctByIdEqualsOrNameContainingIgnoreCaseOrPhoneNumberContainingIgnoreCaseOrEmailContainingIgnoreCaseOrderByIdAsc(null, name, phoneNumber, email);
        }
    }

    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId).get();
    }

    public Customer updateCustomer(Long customerId, Customer customerDetails) {
        Customer c = customerRepository.findById(customerId).get();
        c.setName(customerDetails.getName());
        c.setGender(customerDetails.getGender());
        c.setPhoneNumber(customerDetails.getPhoneNumber());
        c.setDateOfBirth(customerDetails.getDateOfBirth());
        return customerRepository.save(c);
    }

    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    public Customer createCustomer(Customer c) {
        return customerRepository.save(c);
    }

    public boolean checkEmailExist(String email) {
        return !(customerRepository.findByEmail(email) == null);
    }

    public Customer register(Customer customer) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (customerRepository.findByEmail(customer.getEmail()) == null) {
            customer.setPassword(hashPasswordUtil.generatePasswordHash(customer.getPassword()));
            return customerRepository.save(customer);
        }
        return null;
    }

    public Customer validateLogin(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Customer customer = customerRepository.findByEmail(username);
        System.out.println("sa" + username);
        System.out.println("sa" + customer);

        if (customer == null) customer = customerRepository.findByPhoneNumber(username);

        if (customer == null) return null;

        if (hashPasswordUtil.validatePassword(password, customer.getPassword()))
            return customer;
        return null;
    }

    public String generateToken(String id, String email) {
        return jwtUtil.generateJwtToken(id, email);
    }

    public Customer getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumberStartingWith(phoneNumber);
    }

    public List<String> getAllByPhoneNumber() {
        return customerRepository.findAllPhoneNumber();
    }

    public void updateResetPasswordToken(String token, String email) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer != null) {
            customer.setResetPasswordToken(token);
            customerRepository.save(customer);
        }
    }

    public Customer getByResetPasswordToken(String token) {
        return customerRepository.findByResetPasswordToken(token);
    }

    public Customer getEmailResetPassWord(String email) {
        return customerRepository.findByEmail(email);
    }

    public void updatePassword(Customer customer, String newPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {

        customer.setPassword(hashPasswordUtil.generatePasswordHash(newPassword));
        customer.setResetPasswordToken(null);
        customerRepository.save(customer);
    }
}
