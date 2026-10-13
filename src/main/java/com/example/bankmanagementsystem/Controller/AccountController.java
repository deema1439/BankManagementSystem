package com.example.bankmanagementsystem.Controller;

import com.example.bankmanagementsystem.Api.ApiResponse;
import com.example.bankmanagementsystem.Model.Account;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    // الأدمن والموظف
    @GetMapping("/get-all")
    public ResponseEntity<?> getAccounts(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(accountService.getAccounts(user.getId()));
    }

    // العميل
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@AuthenticationPrincipal User user,
                                           @RequestBody @Valid Account account) {
        accountService.createAccount(user.getId(), account);
        return ResponseEntity.status(200).body(new ApiResponse("Account created, waiting for activation"));
    }

    // العميل
    @PutMapping("/update/{accountId}")
    public ResponseEntity<?> updateAccount(@AuthenticationPrincipal User user,
                                           @PathVariable Integer accountId,
                                           @RequestBody @Valid Account account) {
        accountService.updateAccount(user.getId(), accountId, account);
        return ResponseEntity.status(200).body(new ApiResponse("Account updated"));
    }

    // العميل
    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal User user,
                                           @PathVariable Integer accountId) {
        accountService.deleteAccount(user.getId(), accountId);
        return ResponseEntity.status(200).body(new ApiResponse("Account deleted"));
    }



    // الأدمن والموظف
    @PutMapping("/activate/{accountId}")
    public ResponseEntity<?> activateAccount(@AuthenticationPrincipal User user,
                                             @PathVariable Integer accountId) {
        accountService.activateAccount(user.getId(), accountId);
        return ResponseEntity.status(200).body(new ApiResponse("Account activated"));
    }

    // العميل
    @GetMapping("/details/{accountId}")
    public ResponseEntity<?> getAccountDetails(@AuthenticationPrincipal User user,
                                               @PathVariable Integer accountId) {
        return ResponseEntity.status(200).body(accountService.getAccountDetails(user.getId(), accountId));
    }

    // العميل
    @GetMapping("/my-accounts")
    public ResponseEntity<?> getMyAccounts(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(accountService.getMyAccounts(user.getId()));
    }

    // العميل
    @PutMapping("/deposit/{accountId}/{amount}")
    public ResponseEntity<?> deposit(@AuthenticationPrincipal User user,
                                     @PathVariable Integer accountId,
                                     @PathVariable Double amount) {
        accountService.deposit(user.getId(), accountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Deposit successful"));
    }

    // العميل
    @PutMapping("/withdraw/{accountId}/{amount}")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal User user,
                                      @PathVariable Integer accountId,
                                      @PathVariable Double amount) {
        accountService.withdraw(user.getId(), accountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Withdraw successful"));
    }

    // العميل
    @PutMapping("/transfer/{fromAccountId}/{toAccountId}/{amount}")
    public ResponseEntity<?> transfer(@AuthenticationPrincipal User user,
                                      @PathVariable Integer fromAccountId,
                                      @PathVariable Integer toAccountId,
                                      @PathVariable Double amount) {
        accountService.transfer(user.getId(), fromAccountId, toAccountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Transfer successful"));
    }

    // الأدمن والموظف
    @PutMapping("/block/{accountId}")
    public ResponseEntity<?> blockAccount(@AuthenticationPrincipal User user,
                                          @PathVariable Integer accountId) {
        accountService.blockAccount(user.getId(), accountId);
        return ResponseEntity.status(200).body(new ApiResponse("Account blocked"));
    }
}