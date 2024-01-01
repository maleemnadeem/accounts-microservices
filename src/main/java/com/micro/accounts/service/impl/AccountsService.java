package com.micro.accounts.service.impl;

import com.micro.accounts.constants.AccountsConstants;
import com.micro.accounts.dto.AccountsDto;
import com.micro.accounts.dto.CustomerDto;
import com.micro.accounts.exception.CustomerAlreadyExistsException;
import com.micro.accounts.mapper.AccountsMapper;
import com.micro.accounts.mapper.CustomersMapper;
import com.micro.accounts.model.Accounts;
import com.micro.accounts.model.Customer;
import com.micro.accounts.repository.AccountsRepository;
import com.micro.accounts.repository.CustomerRepository;
import com.micro.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsService implements IAccountsService {
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer mapCustomer = CustomersMapper.mapToCustomer(customerDto, new Customer());
        mapCustomer.setCreatedBy("admin");
        mapCustomer.setCreatedAt(LocalDateTime.now());
        if(customerRepository.findByMobileNumber(
                customerDto.getMobileNumber()).isPresent()){
            throw new CustomerAlreadyExistsException("Phone number is already exist");
        }
        Customer customer = customerRepository.save(mapCustomer);
        accountsRepository.save(createSavingAccount(customer));
    }

    @Override
    public CustomerDto fetchCustomer(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new CustomerAlreadyExistsException("Account is not found")
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new CustomerAlreadyExistsException("Account is not found.")
        );
        CustomerDto customerDto = CustomersMapper.mapToCustomerDto(customer, new CustomerDto());
        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(accounts, new AccountsDto());
        customerDto.setAccountsDto(accountsDto);
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new CustomerAlreadyExistsException("Account can not update")
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new CustomerAlreadyExistsException("Customer can not update")
            );
            CustomersMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new CustomerAlreadyExistsException("can not delete account")
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    private Accounts createSavingAccount(Customer customer){
        Accounts savingAccount = new Accounts();
        savingAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        savingAccount.setAccountNumber(randomAccNumber);
        savingAccount.setAccountType(AccountsConstants.SAVINGS);
        savingAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return savingAccount;
    }
}
