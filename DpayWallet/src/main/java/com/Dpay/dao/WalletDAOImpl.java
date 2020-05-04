package com.Dpay.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Dpay.bean.Wallet_Customer;
import com.Dpay.bean.Wallet_Transactions;
import com.Dpay.exception.WalletException;
import com.Dpay.repository.CustomerRepository;

@Repository
public class WalletDAOImpl implements IWalletDAO
{
	private Logger logger = Logger.getRootLogger();
	
	@Autowired
	CustomerRepository customerRepository;
	
	Wallet_Customer wallet_customer;

	@Override
	public List<Wallet_Customer> getAllUsers() 
	{
		logger.info("In WalletDAOImpl at function getAllUsers");
		try {
			logger.info("All users informations are fetched.");
			return customerRepository.findAll();
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		
	}
	
	@Override
	public Wallet_Customer getUserByMobile(String mobile)
	{
		logger.info("In WalletDAOImpl at function getUserByMobile");
		try{
			if(customerRepository.existsById(mobile)) 
			{
				logger.info("User information retrieved by id "+mobile);
				return customerRepository.getOne(mobile);
			}
			else 
			{
				logger.error("User does not exist with mobile number - "+mobile);
				throw new WalletException("User doesn't Exist. Enter Correct Mobile Number..!");
			}	
		}
		catch (WalletException e) {
			logger.error(e.getMessage());
			throw new WalletException(e.getMessage());
		}
	}
		
		

	@Override
	public String createNewUser(Wallet_Customer customer) 
	{
		logger.info("In WalletDAOImpl at function createNewUser");
		try{
			if(customerRepository.existsById(customer.getMobileNumber())) 
			{
				logger.error("User Aleady Exists with mobile number - "+customer.getMobileNumber());
				return "User Already Exists.Try different mobile number";
			}
			else {
				customerRepository.save(customer);
				logger.info(customer.getcustomerName()+" is registered successfully!..");
				return customer.getcustomerName()+" is registered successfully!..";
			}
		}catch (WalletException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}


	@Override
	public String editUser(Wallet_Customer customer) 
	{
		logger.info("In WalletDAOImpl at function editUser");
		try {
			customerRepository.save(customer);
			logger.info(customer.getcustomerName()+" is updated successfully!..");
			return customer.getcustomerName()+" is updated successfully!..";
		}catch (WalletException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}


	@Override
	public boolean loginAuthentication(String mobile, String password) 
	{
		logger.info("In WalletDAOImpl at function loginAuthentication");
		try {
			List<Wallet_Customer> customerList = customerRepository.findAll();
			logger.info("All users informations are fetched.");
			 for(Wallet_Customer customer : customerList) 
			 {
				 if(customer.getMobileNumber().equals(mobile) && customer.getPassword().equals(password)) 
				 {
					 logger.info("Login authentication successful for - "+mobile);
					 return true;
				 }
			 }
			 logger.error("Login authentication failed with mobile-"+mobile+" and Password-"+password);
			 return false;
		}catch (WalletException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public List<Wallet_Transactions> getTransaction(String mobile) 
	{
		logger.info("In WalletDAOImpl at function getTransaction");
		try {
			wallet_customer=customerRepository.getOne(mobile);
			logger.info("User information retrieved by mobile - "+mobile);
			logger.info("All transactions are fetched of mobile - "+mobile);
			return wallet_customer.getTransactionList();
		}catch(WalletException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public String depositToWallet(String mobile, double amount) 
	{
		logger.info("In WalletDAOImpl at function depositToWallet");
		try {
			wallet_customer=customerRepository.getOne(mobile);
			logger.info("User information retrieved by mobile - "+mobile);
			wallet_customer.setBalance(amount+wallet_customer.getBalance());
			logger.info("Money deposited in account with mobile - "+mobile);
			wallet_customer.addTransaction(new Wallet_Transactions(amount+"+ is deposited to wallet"));
			customerRepository.save(wallet_customer);
			logger.info("New transaction = "+amount+" is deposited to wallet of mobile - "+mobile);
			return amount+" is deposited to wallet";
		}catch (WalletException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}


	@Override
	public String withdraw(String mobile, double amount, String password) 
	{
		logger.info("In WalletDAOImpl at function withdraw");
		try {
			wallet_customer=customerRepository.getOne(mobile);
			logger.info("User information retrieved by mobile - "+mobile);
			if(wallet_customer.getPassword().equals(password)) 
			{
				if(wallet_customer.getBalance()>=amount) 
				{
					wallet_customer.setBalance(wallet_customer.getBalance()-amount);
					logger.info("Money withdrawn from account with mobile - "+mobile);
					wallet_customer.addTransaction(new Wallet_Transactions(amount+"- is withdrawn from wallet"));
					customerRepository.save(wallet_customer);
					logger.info("New transaction = "+amount+" is withdrawn from wallet of mobile -"+mobile);
					return "ok";
				}
				else {
					logger.error("Insufficient Balance in Wallet of mobile - "+mobile);
					return "Insufficient Balance in Wallet";
				}
			}
			else {
				logger.error("Incorrect password is entered for mobile - "+mobile);
				return "Enter correct password";
			}
		}catch(WalletException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}


	@Override
	public double getBalance(String mobile)
	{
		logger.info("In WalletDAOImpl at function getBalance");
		try {
			wallet_customer=customerRepository.getOne(mobile);
			logger.info("Balance is fetched of mobile - "+mobile);
			return wallet_customer.getBalance();
		}catch (WalletException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public String transfer(String mobile, String receiver, double amount, String password) 
	{
		logger.info("In WalletDAOImpl at function transfer");
		try {
			wallet_customer=customerRepository.getOne(mobile);
			if(mobile.equals(receiver)) {
				logger.error("Self transaction tried by mobile - "+mobile);
				return "Self transfer not Allowed";
			}
			if(customerRepository.existsById(receiver)) 
			{
				if(wallet_customer.getPassword().equals(password)) 
				{
					Wallet_Customer receiverUser=customerRepository.getOne(receiver);
					if(wallet_customer.getBalance()>=amount) 
					{
						wallet_customer.setBalance(wallet_customer.getBalance()-amount);
						receiverUser.setBalance(amount+receiverUser.getBalance());
						logger.info("Money transfer successful..");
						wallet_customer.addTransaction(new Wallet_Transactions(amount+"- is transferred to "+receiverUser.getcustomerName()+"/"+receiverUser.getMobileNumber()));
						receiverUser.addTransaction(new Wallet_Transactions(amount+"+ is received from "+wallet_customer.getcustomerName()+"/"+wallet_customer.getMobileNumber()));
						logger.info("New Transaction for mobile - "+mobile+" = "+amount+"- is transferred to "+receiverUser.getcustomerName()+"/"+receiverUser.getMobileNumber());
						logger.info("New Transaction for mobile - "+receiver +"= "+amount+"+ is received from "+wallet_customer.getcustomerName()+"/"+wallet_customer.getMobileNumber());
						customerRepository.save(wallet_customer);
						customerRepository.save(receiverUser);
						
						return "ok";
					}
					else {
						logger.error("Insufficient Balance in Wallet of mobile - "+mobile);
						return "Insufficient Balance in Wallet";
					}
				}
				else {
					logger.error("Incorrect password is entered for mobile - "+mobile);
					return "Enter correct password";
				}
			}
			else {
				logger.error("Receiver User Doesn't Exists with mobile - "+receiver);
				return "Receiver User Doesn't Exists. Enter correct Receiver Mobile Number";
			}
		}catch(WalletException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}


	@Override
	public String changePassword(String mobile, String currentPassword, String newPassword) 
	{
		logger.info("In WalletDAOImpl at function changePassword");
		try {
			wallet_customer=customerRepository.getOne(mobile);
			if(wallet_customer.getPassword().equals(currentPassword)) 
			{
				if(currentPassword.equals(newPassword)) {
					logger.error("New password can't be Same as Current for mobile - "+mobile);
					return "New password can't be Same as Current";
				}
				wallet_customer.setPassword(newPassword);
				customerRepository.save(wallet_customer);
				logger.info("Password changed successfully for mobile - "+mobile);
				return "ok";
			}
			else 
			{
				logger.error("Incorrect password is entered for mobile - "+mobile);
				return "Current Password is incorrect";
			}
		}catch (WalletException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}
	
}
