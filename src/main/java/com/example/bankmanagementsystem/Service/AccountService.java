package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.Api.ApiException;
import com.example.bankmanagementsystem.Model.Account;
import com.example.bankmanagementsystem.Model.Customer;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Repository.AccountRepository;
import com.example.bankmanagementsystem.Repository.AuthRepository;
import com.example.bankmanagementsystem.Repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;
   // الادمن والموظف منطقيا يقدرون يشوفونها



    public List<Account>getAccounts(Integer user_id){
        User user=authRepository.findUsersById(user_id);
        if(user==null){
            throw new ApiException("User Not found");
        }
        if(!user.getRole().equals("ADMIN")&& !user.getRole().equals("EMPLOYEE")){
            throw new ApiException("Only admin or employee can view all accounts");
        }
        return accountRepository.findAll();
    }

    //customer creat account

    public void createAccount(Integer customer_id,Account account){
        Customer customer=customerRepository.findCustomerById(customer_id);
        if(customer==null){
            throw new ApiException("Customer not found");
        }
        if (accountRepository.findAccountByAccountNumber(account.getAccountNumber()) != null) {
            throw new ApiException("Account number already exists");
        }
        account.setIsActive(false);
        account.setBalance(0.0);
        account.setCustomer(customer);
        accountRepository.save(account);
    }


 //customer see all his accounts

    public List<Account>getMyAccounts(Integer customer_id){
        Customer customer=customerRepository.findCustomerById(customer_id);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        return accountRepository.findAllByCustomer(customer);
    }
   //العميل يعدل
    public void updateAccount(Integer customer_id,Integer account_id,Account account){
        Account oldAccount=checkOwner(customer_id,account_id);
        Account existing=accountRepository.findAccountByAccountNumber(account.getAccountNumber());
        if(existing!=null&&!existing.getId().equals(account_id)){
            throw new ApiException("Account number already exists");
        }
        oldAccount.setAccountNumber(account.getAccountNumber());
        accountRepository.save(oldAccount);
    }

    public void deleteAccount(Integer customer_id, Integer account_id){
        Account account=checkOwner(customer_id,account_id);
        if(account.getBalance()>0){
            throw new ApiException("Withdraw your balance before deleting the account");
        }
        accountRepository.delete(account);
    }


    //endpoints
    //الموظف والادمن
    public void activateAccount(Integer user_id, Integer account_id){
    User user=authRepository.findUsersById(user_id);
    if(user==null){
        throw new ApiException("User not found");
    }
    if(!user.getRole().equals("ADMIN")&& !user.getRole().equals("EMPLOYEE")){
        throw new ApiException("Only admin or employee can activate accounts");
    }
    Account account=accountRepository.findAccountById(account_id);
    if(account==null){
        throw new ApiException("Account not found");
    }
    if(account.getIsActive()){
        throw new ApiException("Account is already active");
    }
    account.setIsActive(true);
    accountRepository.save(account);
    }

    //customer sees his account details
    public Account getAccountDetails(Integer customer_id,Integer account_id){
        return checkOwner(customer_id,account_id);
    }

    //ايداع يعني
    public void deposit(Integer customer_id,Integer account_id,Double amount){
        Account account=checkOwner(customer_id,account_id);
        checkAmount(amount);
        checkActive(account);
        account.setBalance(account.getBalance()+amount);
        accountRepository.save(account);
    }

    //سحب يعني
    public void withdraw(Integer customer_id,Integer account_id, Double amount){
        //اتاكد انه البالنس اصلا مو صفر وان البالنس الي بيسحبه موجود اصلا في الحساب
        Account account=checkOwner(customer_id,account_id);
        checkActive(account);
        checkAmount(amount);
        if(account.getBalance()<amount){
            throw new ApiException("Insufficient balance");
        }
        account.setBalance(account.getBalance()-amount);
        accountRepository.save(account);
    }

    @Transactional
    public void transfer(Integer customer_id,Integer fromAccountId,Integer toAccountId,Double amount){
        Account fromAccount =checkOwner(customer_id,fromAccountId);
        checkActive(fromAccount);
        checkAmount(amount);
        Account toAccount=accountRepository.findAccountById(toAccountId);
        if(fromAccountId.equals(toAccountId)){
            throw new ApiException("Cannot transfer to the same account");
        }
        if(toAccount==null){
            throw new ApiException("Receiver account not found");
        }
        checkActive(toAccount);
        if(fromAccount.getBalance()<amount){
            throw new ApiException("Insufficient balance");
        }
        fromAccount.setBalance(fromAccount.getBalance()-amount);
        toAccount.setBalance(toAccount.getBalance()+amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);


    }

    //الموظف او الادمن يلغون حساب
    public void blockAccount(Integer user_id,Integer account_id){
        User user=authRepository.findUsersById(user_id);
        if(user==null){
            throw new ApiException("User not found");
        }
        if(!user.getRole().equals("ADMIN")&& !user.getRole().equals("EMPLOYEE")){
            throw new ApiException("Only admin or employee can block accounts");
        }
        Account account=accountRepository.findAccountById(account_id);
        if (account == null) {
            throw new ApiException("Account not found");
        }
        if(!account.getIsActive()){
            throw new ApiException("Account is already blocked");
        }
        account.setIsActive(false);
        accountRepository.save(account);
    }




    private Account checkOwner(Integer customer_id,Integer account_id){
      Account account=accountRepository.findAccountById(account_id);
        if (account == null) {
            throw new ApiException("Account not found");
        }
        if(!account.getCustomer().getId().equals(customer_id)){
            throw new ApiException("This account does not belong to you");
        }
        return account;

        }

        private void checkActive(Account account) {
        if(!account.getIsActive()){
            throw new ApiException("Account "+account.getAccountNumber()+" is not active");
        }
    }

      private void checkAmount(Double amount){
        if(amount==null||amount<=0){
            throw new ApiException("Amount must be greater than zero");
        }
        if(amount>1000000000){
            throw new ApiException("Amount exceeds the allowed limit");
        }
      }











}
