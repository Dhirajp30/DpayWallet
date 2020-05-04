package com.Dpay.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Dpay.bean.Wallet_Customer;
import com.Dpay.bean.Wallet_Transactions;
import com.Dpay.dao.WalletDAOImpl;

@Service
public class WalletServiceImpl implements IWalletService
{
	
	@Autowired
	WalletDAOImpl walletDAO;
	
	public WalletServiceImpl() 
	{
		walletDAO = new WalletDAOImpl();
	}

	@Override
	public List<Wallet_Customer> getAllUsers() 
	{
		return walletDAO.getAllUsers();
	}

	@Override
	public Wallet_Customer getUserByMobile(String mobile) 
	{
		return walletDAO.getUserByMobile(mobile);
	}

	@Override
	public String createNewUser(Wallet_Customer customer) 
	{
		return walletDAO.createNewUser(customer);
	}

	@Override
	public String editUser(Wallet_Customer customer) 
	{
		return walletDAO.editUser(customer);
	}

	@Override
	public boolean loginAuthentication(String mobile, String password) 
	{
		return walletDAO.loginAuthentication(mobile, password);
	}

	@Override
	public List<Wallet_Transactions> getTransaction(String mobile) 
	{
		return walletDAO.getTransaction(mobile);
	}

	@Override
	public String depositToWallet(String mobile, double amount) 
	{
		return walletDAO.depositToWallet(mobile, amount);
	}

	@Override
	public String withdraw(String mobile, double amount, String password) 
	{
		return walletDAO.withdraw(mobile, amount, password);
	}

	@Override
	public double getBalance(String mobile) 
	{
		return walletDAO.getBalance(mobile);
	}

	@Override
	public String transfer(String mobile, String receiver, double amount, String password) 
	{
		return walletDAO.transfer(mobile, receiver, amount, password);
	}

	@Override
	public String changePassword(String mobile, String currentPassword, String newPassword) 
	{
		return walletDAO.changePassword(mobile, currentPassword, newPassword);
	}
}
