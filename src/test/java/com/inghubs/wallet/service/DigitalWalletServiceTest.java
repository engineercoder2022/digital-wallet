package com.inghubs.wallet.service;

import static com.inghubs.wallet.repository.entity.TransactionType.DEPOSIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.inghubs.wallet.api.model.request.WalletBalanceRequest;
import com.inghubs.wallet.api.model.request.ApproveRequest;
import com.inghubs.wallet.api.model.request.CreateWalletRequest;
import com.inghubs.wallet.repository.DigitalWalletRepository;
import com.inghubs.wallet.repository.TransactionRepository;
import com.inghubs.wallet.repository.entity.Customer;
import com.inghubs.wallet.repository.entity.Status;
import com.inghubs.wallet.repository.entity.Transaction;
import com.inghubs.wallet.repository.entity.Wallet;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DigitalWalletServiceTest {

  @Mock
  DigitalWalletRepository digitalWalletRepository;
  @Mock
  TransactionRepository transactionRepository;
  @InjectMocks
  DigitalWalletService digitalWalletService;

  @Test
  void shouldCreateDigitalWallet() {
    Wallet wallet = Wallet.builder().id(1L).build();
    when(digitalWalletRepository.save(Mockito.any(Wallet.class))).thenReturn(wallet);
    CreateWalletRequest request = mock(CreateWalletRequest.class);
    assertEquals(1L, digitalWalletService.createDigitalWallet(request));
  }

  @Test
  void shouldGetCustomerWallets() {
    Wallet w1 = Wallet.builder().id(1L).activeForWithdraw(true).activeForWithdraw(true).build();
    Wallet w2 = Wallet.builder().id(2L).activeForWithdraw(true).activeForWithdraw(true).build();

    when(digitalWalletRepository.findAllByCustomer(any(Customer.class)))
        .thenReturn(Arrays.asList(w1, w2));
    assertEquals(2, digitalWalletService.getCustomerWallets(1L).size());
  }

  @Test
  void shouldProcessdeposit() {
    WalletBalanceRequest walletBalanceRequest = mock(WalletBalanceRequest.class);
    when(walletBalanceRequest.amount()).thenReturn(new BigDecimal(99));
    when(walletBalanceRequest.walletId()).thenReturn(1L);
    Wallet wallet = Wallet.builder().balance(new BigDecimal(0))
        .usableBalance(new BigDecimal(0)).build();
    when(digitalWalletRepository.findById(1L)).thenReturn(Optional.of(wallet));
    digitalWalletService.deposit(walletBalanceRequest);
    verify(digitalWalletRepository).save(any(Wallet.class));
    verify(transactionRepository).save(any(Transaction.class));
  }

  @Test
  void processWithdraw() {
    WalletBalanceRequest walletBalanceRequest = mock(WalletBalanceRequest.class);
    when(walletBalanceRequest.amount()).thenReturn(new BigDecimal(99));
    when(walletBalanceRequest.walletId()).thenReturn(1L);
    Wallet wallet = Wallet.builder().balance(new BigDecimal(0))
        .usableBalance(new BigDecimal(0)).build();
    when(digitalWalletRepository.findById(1L)).thenReturn(Optional.of(wallet));
    digitalWalletService.withdraw(walletBalanceRequest);
    verify(digitalWalletRepository).save(any(Wallet.class));
    verify(transactionRepository).save(any(Transaction.class));
  }

  @Test
  void getWalletTransactions() {
    Transaction t1 = Transaction.builder().build();
    when(transactionRepository.findAllByWallet(any(Wallet.class))).thenReturn(Arrays.asList(t1));
    assertEquals(1, digitalWalletService.getWalletTransactions(1L).size());
  }

  @Test
  void shouldApprove() {
    Wallet wallet = Wallet.builder().balance(new BigDecimal(50))
        .usableBalance(new BigDecimal(10)).build();
    Transaction tx = Transaction.builder().transactionType(DEPOSIT).amount(new BigDecimal(200))
        .status(Status.PENDING).wallet(wallet).build();
    when(transactionRepository
        .findById(anyLong())).thenReturn(Optional.of(tx));
    ApproveRequest request = ApproveRequest.builder().status(Status.APPROVED).transactionId(1L).build();
    digitalWalletService.approve(request);
    verify(digitalWalletRepository).save(any(Wallet.class));
    verify(transactionRepository).save(any(Transaction.class));
    assertEquals(wallet.getUsableBalance(), new BigDecimal(210));
  }

}