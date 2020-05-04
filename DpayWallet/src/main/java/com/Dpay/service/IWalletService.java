package com.Dpay.service;

import java.util.List;

import com.Dpay.bean.Wallet_Customer;
import com.Dpay.bean.Wallet_Transactions;

public interface IWalletService 
{
	
	public List<Wallet_Customer> getAllUsers();
	public Wallet_Customer getUserByMobile(String mobile);
	public String createNewUser(Wallet_Customer customer);
	public String editUser(Wallet_Customer customer);
	public boolean loginAuthentication(String mobile,String password);
	public List<Wallet_Transactions> getTransaction(String mobile);
	public String depositToWallet(String mobile,double amount);
	public String withdraw(String mobile,double amount,String password);
	public double getBalance(String mobile);
	public String transfer(String mobile,String receiver,double amount,String password);
	public String changePassword(String mobile,String currentPassword,String newPassword);

}
