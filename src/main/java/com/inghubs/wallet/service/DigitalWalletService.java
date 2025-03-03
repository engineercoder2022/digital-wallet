package com.inghubs.wallet.service;

import static com.inghubs.wallet.repository.entity.Status.APPROVED;
import static com.inghubs.wallet.repository.entity.Status.PENDING;
import static com.inghubs.wallet.repository.entity.TransactionType.DEPOSIT;

import com.inghubs.wallet.api.TransactionsResponse;
import com.inghubs.wallet.api.WalletBalanceRequest;
import com.inghubs.wallet.api.model.request.ApproveRequest;
import com.inghubs.wallet.api.model.request.CreateWalletRequest;
import com.inghubs.wallet.api.model.response.WalletsResponse;
import com.inghubs.wallet.repository.DigitalWalletRepository;
import com.inghubs.wallet.repository.TransactionRepository;
import com.inghubs.wallet.repository.entity.Customer;
import com.inghubs.wallet.repository.entity.Status;
import com.inghubs.wallet.repository.entity.Transaction;
import com.inghubs.wallet.repository.entity.TransactionType;
import com.inghubs.wallet.repository.entity.Wallet;
import com.inghubs.wallet.util.Mapper;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DigitalWalletService {

  public static final int MAX_WITHDRAW_AMOUNT = 1000;
  public static final int MAX_DEPOSIT_AMOUNT = 100;
  private final DigitalWalletRepository digitalWalletRepository;
  private final TransactionRepository transactionRepository;

  public long createDigitalWallet(CreateWalletRequest request) {
    Wallet wallet = Wallet.builder()
        .name(request.walletName())
        .currency(request.currency())
        .activeForShopping(request.activateForShopping())
        .activeForWithdraw(request.activateForWithdraw())
        .balance(new BigDecimal(0))
        .usableBalance(new BigDecimal(0))
        .customer(Customer.builder().id(request.customerId()).build())
        .build();

    return digitalWalletRepository.save(wallet).getId();
  }


  public List<WalletsResponse> getCustomerWallets(long customerId) {
    List<Wallet> wallets = digitalWalletRepository
        .findAllByCustomer(Customer.builder().id(customerId).build());
    return wallets.stream().map(w -> Mapper.mapToWalletsResponse(w)).toList();
  }

  @Transactional
  public void deposit(WalletBalanceRequest request) {
    Wallet wallet = digitalWalletRepository.findById(request.walletId()).get();
    Status status = getStatus(request.amount(), MAX_DEPOSIT_AMOUNT);
    processDeposit(wallet, request.amount(), status);
    digitalWalletRepository.save(wallet);
    Transaction transaction = Transaction.builder().amount(request.amount())
        .wallet(wallet).status(status)
        .transactionType(DEPOSIT).build();
    transactionRepository.save(transaction);
  }

  private void processDeposit(Wallet wallet, BigDecimal amount, Status status) {
    BigDecimal newBalance = wallet.getBalance().add(amount);
    if (APPROVED.equals(status)) {
      wallet.setUsableBalance(wallet.getUsableBalance().add(amount));
    }
    wallet.setBalance(newBalance);
  }

  private Status getStatus(BigDecimal amount, Integer maxValue) {
    return amount.compareTo(new BigDecimal(maxValue)) > 0 ? PENDING : APPROVED;
  }

  public void withdraw(WalletBalanceRequest request) {
    Wallet wallet = digitalWalletRepository.findById(request.walletId()).get();
    Status status = getStatus(request.amount(), MAX_WITHDRAW_AMOUNT);
    processWithdraw(wallet, request.amount(), status);
    digitalWalletRepository.save(wallet);
    Transaction transaction = Transaction.builder().amount(request.amount())
        .wallet(wallet).status(status)
        .transactionType(TransactionType.WITHDRAW).build();
    transactionRepository.save(transaction);
  }

  private void processWithdraw(Wallet wallet, BigDecimal amount, Status status) {
    BigDecimal newBalance = wallet.getBalance().subtract(amount);
    if (APPROVED.equals(status)) {
      wallet.setUsableBalance(wallet.getUsableBalance().subtract(amount));
    }
    wallet.setBalance(newBalance);
  }

  public List<TransactionsResponse> getWalletTransactions(Long walletId) {
    List<Transaction> allByWallet = transactionRepository.findAllByWallet(
        Wallet.builder().id(walletId).build());
    return allByWallet.stream()
        .map(t -> Mapper.mapToTransactionResponse(t)).toList();
  }

  @Transactional
  public void approve(ApproveRequest request) {
    Transaction trx = transactionRepository
        .findById(request.transactionId()).get();
    if (trx.getStatus().equals(PENDING)) {
      if (request.status().equals(APPROVED)) {
        approveTransaction(request, trx);
      } else {
        denyTransaction(trx);
      }
      trx.setStatus(request.status());
      transactionRepository.save(trx);
      digitalWalletRepository.save(trx.getWallet());
    } else {
      throw new IllegalStateException("Transaction status invalid.");
    }
  }

  private void denyTransaction(Transaction trx) {
    Wallet wallet = trx.getWallet();
    if (trx.getTransactionType().equals(DEPOSIT)) {
      BigDecimal deniedBalance = wallet.getBalance().subtract(trx.getAmount());
      wallet.setBalance(deniedBalance);
    } else {
      BigDecimal deniedBalance = wallet.getBalance().add(trx.getAmount());
      wallet.setBalance(deniedBalance);
    }
  }

  private void approveTransaction(ApproveRequest request, Transaction trx) {
    Wallet wallet = trx.getWallet();
    if (trx.getTransactionType().equals(DEPOSIT)) {
      BigDecimal approvedBalance = wallet.getUsableBalance().add(trx.getAmount());
      wallet.setUsableBalance(approvedBalance);
    } else {
      BigDecimal approvedBalance = wallet.getUsableBalance().subtract(trx.getAmount());
      wallet.setUsableBalance(approvedBalance);
    }
  }
}
