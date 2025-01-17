package com.example.loan_backend.controller;

import java.util.List;

import com.example.loan_backend.models.Loan;
import com.example.loan_backend.services.LoanService;
import com.example.loan_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/loan")
@RestController
public class LoanController {

  @Autowired
  private UserService userService;

  @Autowired
  private LoanService loanService;

  @PostMapping(value = "/reqLoan")
  public ResponseEntity<Object> addloan(@RequestBody Loan loan) {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    loan.setUser(userService.getUniqueUserByEmail(email).get());
    loanService.saveLoan(loan);
    return new ResponseEntity<>(loan, HttpStatus.CREATED);
  }

  @GetMapping(value = "/getAllLoans")
  public List<Loan> getMyLoans() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    return loanService.getLoansByUserEmail(email);
  }

}
