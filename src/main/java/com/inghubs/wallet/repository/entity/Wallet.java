package com.inghubs.wallet.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Wallet")
public class Wallet {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CUSTOMER_ID", nullable = false)
  private Customer customer;

  @Column(name = "WALLET_NAME")
  private String name;

  @Column(name = "CURRENCY")
  private String currency;

  @Column(name = "ACTIVE_FOR_SHOPPING")
  private boolean activeForShopping;

  @Column(name = "ACTIVE_FOR_WITHDRAW")
  private boolean activeForWithdraw;

  @Column(name = "BALANCE")
  private BigDecimal balance;

  @Column(name = "USABLE_BALANCE")
  private BigDecimal usableBalance;

}
