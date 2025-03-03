package com.inghubs.wallet.util;

import com.inghubs.wallet.api.model.response.TransactionsResponse;
import com.inghubs.wallet.api.model.response.WalletsResponse;
import com.inghubs.wallet.repository.entity.Transaction;
import com.inghubs.wallet.repository.entity.Wallet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Mapper {

  public static WalletsResponse mapToWalletsResponse(Wallet w) {
    return WalletsResponse.builder().walletName(w.getName())
        .activeForShopping(w.isActiveForShopping())
        .activeForWithdraw(w.isActiveForWithdraw())
        .balance(w.getBalance())
        .usableBalance(w.getUsableBalance())
        .currency(w.getCurrency()).build();
  }

  public static TransactionsResponse mapToTransactionResponse(Transaction t) {
    return TransactionsResponse.builder()
        .transactionType(t.getTransactionType())
        .status(t.getStatus()).amount(t.getAmount()).build();
  }
}
