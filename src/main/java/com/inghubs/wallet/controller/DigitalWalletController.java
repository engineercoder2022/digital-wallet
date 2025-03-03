package com.inghubs.wallet.controller;

import com.inghubs.wallet.api.WalletBalanceRequest;
import com.inghubs.wallet.api.DigitalWalletApi;
import com.inghubs.wallet.api.TransactionsResponse;
import com.inghubs.wallet.api.model.request.ApproveRequest;
import com.inghubs.wallet.api.model.request.CreateWalletRequest;
import com.inghubs.wallet.api.model.response.WalletsResponse;
import com.inghubs.wallet.service.DigitalWalletService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/digital-wallet")
public class DigitalWalletController implements DigitalWalletApi {

  @Autowired
  private DigitalWalletService digitalWalletService;

  @Override
  public ResponseEntity<Void> createDigitalWallet(CreateWalletRequest request) {
    digitalWalletService.createDigitalWallet(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .build();
  }

  @Override
  public ResponseEntity<List<WalletsResponse>> getCustomerWallets(Long customerId) {
    return ResponseEntity.ok(digitalWalletService.getCustomerWallets(customerId));
  }

  @Override
  public ResponseEntity<Void> deposit(WalletBalanceRequest request) {
    digitalWalletService.deposit(request);
    return ResponseEntity.status(HttpStatus.OK)
        .build();
  }

  @Override
  public ResponseEntity<List<TransactionsResponse>> getWalletTransactions(Long walletId) {
    return ResponseEntity.ok(digitalWalletService.getWalletTransactions(walletId));
  }

  @Override
  public ResponseEntity<Void> withdraw(WalletBalanceRequest request) {
    digitalWalletService.withdraw(request);
    return ResponseEntity.status(HttpStatus.OK)
        .build();
  }

  @Override
  public ResponseEntity<Void> approve(ApproveRequest request) {
    digitalWalletService.approve(request);
    return ResponseEntity.status(HttpStatus.OK)
        .build();
  }
}
