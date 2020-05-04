package com.Dpay.controller;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Dpay.bean.Wallet_Customer;
import com.Dpay.bean.Wallet_Transactions;
import com.Dpay.service.WalletServiceImpl;


@RestController
@RequestMapping(value= "/wallet")
public class WalletController 
{
	private Logger logger = Logger.getRootLogger();
	
	@Autowired
	WalletServiceImpl walletService;
	
	public WalletController() 
	{
		walletService= new WalletServiceImpl();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/getAll")
	public List<Wallet_Customer> getAllUsers()
	{
		logger.info("In WalletController at function getAllUsers");
		return walletService.getAllUsers();
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/getUserByMobile/{mobileNumber}")
	public Wallet_Customer getUserByMobile(@PathVariable("mobileNumber")String mobileNumber)
	{
		logger.info("In WalletController at function getUserByMobile");
		return walletService.getUserByMobile(mobileNumber);
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value = "/create")
	public String createNewUser(@RequestBody Wallet_Customer customer)
	{
		logger.info("In WalletController at function createNewUser");
		return walletService.createNewUser(customer);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping(value = "/editUser")
	public String editUser(@RequestBody Wallet_Customer customer)
	{
		logger.info("In WalletController at function editUser");
		return walletService.editUser(customer);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value="/loginVerify/{mobileNumber}/{password}")
	public boolean loginAuthentication(@PathVariable("mobileNumber")String mobile, @PathVariable("password")String password) 
	{
		logger.info("In WalletController at function loginAuthentication");
		return walletService.loginAuthentication(mobile, password);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/getTransactions/{mobileNumber}")
	public List<Wallet_Transactions> getTransaction(@PathVariable("mobileNumber")String mobile)
	{
		logger.info("In WalletController at function getTransaction");
		return walletService.getTransaction(mobile);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping(value = "/deposit/{amount}")
	public String depositToWallet(@RequestBody String mobile,@PathVariable("amount")double amount)
	{
		logger.info("In WalletController at function depositToWallet");
		return walletService.depositToWallet(mobile, amount);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping(value = "/withdraw/{amount}/{password}")
	public String withdraw(@RequestBody String mobile,@PathVariable("amount")double amount, @PathVariable("password")String password)
	{
		logger.info("In WalletController at function withdraw");
		return walletService.withdraw(mobile, amount, password);
			
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/getBalance/{mobileNumber}")
	public double getBalance(@PathVariable("mobileNumber")String mobile)
	{
		logger.info("In WalletController at function getBalance");
		return walletService.getBalance(mobile);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping(value = "/transfer/{receiver}/{amount}/{password}")
	public String transfer(@RequestBody String mobile,@PathVariable("receiver")String receiver,
			@PathVariable("amount")double amount, @PathVariable("password")String password)
	{
		logger.info("In WalletController at function transfer");
		return walletService.transfer(mobile, receiver, amount, password);		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping(value = "/changePassword/{currentPass}/{newPass}")
	public String changePassword(@RequestBody String mobile,
			@PathVariable("currentPass")String currentPassword,@PathVariable("newPass")String newPassword)
	{
		logger.info("In WalletController at function changePassword");
		return walletService.changePassword(mobile, currentPassword, newPassword);
	}
		
}
