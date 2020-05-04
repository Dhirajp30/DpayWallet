package com.Dpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Dpay.bean.Wallet_Customer;

public interface CustomerRepository extends JpaRepository<Wallet_Customer, String> 
{

}
