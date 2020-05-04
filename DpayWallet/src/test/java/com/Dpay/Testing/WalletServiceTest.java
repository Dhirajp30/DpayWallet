package com.Dpay.Testing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.Dpay.bean.Wallet_Customer;
import com.Dpay.exception.WalletException;
import com.Dpay.service.WalletServiceImpl;

@Transactional
@SpringBootTest
class WalletServiceTest {

	
	private static Logger logger;
	
	@Autowired
	private WalletServiceImpl walletService;

	@BeforeAll
	static void setUpBeforeClass() {
		logger = Logger.getRootLogger();
		System.out.println("Fetching resources for testing ...");
	}

	@BeforeEach
	void setup() {
		logger.info("Test Case Started");
		walletService.createNewUser(new Wallet_Customer("Rohit Sharma","8765432123","Rohit@123", 
				"rohit@gmail.com","Male", 31,"Your Vehicle Number","6578"));
		System.out.println("Test Case Started");
	}

	@AfterEach
	void tearDown() {
		logger.info("Test Case Over");
		System.out.println("Test Case Over");
	}

	@Test
	@DisplayName("User Registration Successful")
	@Rollback(true)
	public void registrationFirstTest() throws Exception {
		logger.info("Test Case - User Registration Successful");
		String message = walletService.createNewUser(new Wallet_Customer("Yuvraj Singh","7065432123","Yuvraj@123", 
				"yuvraj@gmail.com","Male", 38,"Favourite City","Durban"));
		String expectedMessage = "Yuvraj Singh is registered successfully!..";
		assertEquals(message, expectedMessage);
	}
	
	@Test
	@DisplayName("User Registration Fails - User Already Registered")
	@Rollback(true)
	public void registrationSecondTest() throws Exception {
		logger.info("Test Case - User Registration Fails - User Already Registered");
		String message = walletService.createNewUser(new Wallet_Customer("Rohit Sharma","8765432123","Rohit@123", 
				"rohit@gmail.com","Male", 31,"Your Vehicle Number","6578"));
		String expectedMessage = "User Already Exists.Try different mobile number";
		assertEquals(message, expectedMessage);
	}
	
	@Test
	@DisplayName("User Login Successful")
	@Rollback(true)
	public void loginFirstTest() throws Exception {
		logger.info("Test Case - User Login Successful");

		boolean result = walletService.loginAuthentication("8765432123", "Rohit@123");
		assertEquals(true, result);
	}
	
	@Test
	@DisplayName("User Login Fails - Invalid Credentials")
	@Rollback(true)
	public void loginSecondTest() throws Exception {
		logger.info("Test Case - User Login Fails - Invalid Credentials");
		boolean result = walletService.loginAuthentication("8765432123", "Rohit@12345");
		assertEquals(false, result);
	}
	
	@Test
	@DisplayName("Get User With mobile Successful")
	@Rollback(true)
	public void getUSerFirstTest() throws Exception {
		logger.info("Test Case - Get User With mobile Successful");
		Wallet_Customer customer = new Wallet_Customer("Rohit Sharma","8765432123","Rohit@123", 
				"rohit@gmail.com","Male", 31,"Your Vehicle Number","6578");
		Wallet_Customer existingCustomer = walletService.getUserByMobile("8765432123");
		assertThat(customer).isEqualToComparingFieldByField(existingCustomer);
		
	}
	
	@Test
	@DisplayName("Get User With mobile Fails - User doesn't Exist")
	@Rollback(true)
	public void getUserSecondTest() throws Exception {
		logger.info("Test Case - Get User With mobile Fails - User doesn't Exist");
		String message = null;
		try {
			walletService.getUserByMobile("8865432123");
		}catch (WalletException e) {
			message = e.getMessage();
		}
		String expectedMessage = "User doesn't Exist. Enter Correct Mobile Number..!";
		assertEquals(message, expectedMessage);
		
	}
	
	@Test
	@DisplayName("Get Balance of User By Mobile")
	@Rollback(true)
	public void getBalanceTest() throws Exception {
		logger.info("Test Case - Get Balance of User By Mobile");
		walletService.depositToWallet("8765432123", 100);
		double availableBalance = walletService.getBalance("8765432123");
		assertEquals(100.0, availableBalance);
		
	}
	
	@Test
	@DisplayName("Deposit Money to wallet")
	@Rollback(true)
	public void depositTest() throws Exception {
		logger.info("Test Case - Deposit Money to wallet");
		String message = walletService.depositToWallet("8765432123", 100);
		String expectedMessage = 100.0+" is deposited to wallet";
		assertEquals(message, expectedMessage);
		
	}
	
	@Test
	@DisplayName("Withdraw money Successful")
	@Rollback(true)
	public void withdrawFirstTest() throws Exception {
		logger.info("Test Case - Withdraw money Successful");
		walletService.depositToWallet("8765432123", 100);
		String message  = walletService.withdraw("8765432123", 80, "Rohit@123");
		String expectedMessage = "ok";
		assertEquals(message, expectedMessage);
		
	}
	
	@Test
	@DisplayName("Withdraw money Unsuccessful - Insufficient Balance")
	@Rollback(true)
	public void withdrawSecondTest() throws Exception {
		logger.info("Test Case - Withdraw money Unsuccessful - Insufficient Balance");
		walletService.depositToWallet("8765432123", 100);
		String message  = walletService.withdraw("8765432123", 200, "Rohit@123");
		String expectedMessage = "Insufficient Balance in Wallet";
		assertEquals(message, expectedMessage);
		
	}
	
	@Test
	@DisplayName("Withdraw money Unsuccessful - Incorrect Password")
	@Rollback(true)
	public void withdrawThirdTest() throws Exception {
		logger.info("Test Case - Withdraw money Unsuccessful - Incorrect Password");
		walletService.depositToWallet("8765432123", 100);
		String message  = walletService.withdraw("8765432123", 70, "Rohit@1234");
		String expectedMessage = "Enter correct password";
		assertEquals(message, expectedMessage);
		
	}
	
	@Test
	@DisplayName("Money Transfer Successful")
	@Rollback(true)
	public void transferFirstTest() throws Exception {
		logger.info("Test Case - Money Transfer Successful");
		walletService.createNewUser(new Wallet_Customer("Yuvraj Singh","7065432123","Yuvraj@123", 
				"yuvraj@gmail.com","Male", 38,"Favourite City","Durban"));
		walletService.depositToWallet("8765432123", 100);
		String message  = walletService.transfer("8765432123", "7065432123", 60, "Rohit@123");
		String expectedMessage = "ok";
		assertEquals(message, expectedMessage);
		
	}
	
	@Test
	@DisplayName("Money Transfer Unuccessful - Receiver doesn't exist.")
	@Rollback(true)
	public void transferSecondTest() throws Exception {
		logger.info("Test Case - Money Transfer Unuccessful - Receiver doesn't exist.");
		walletService.depositToWallet("8765432123", 100);
		String message  = walletService.transfer("8765432123", "7065432123", 60, "Rohit@123");
		String expectedMessage = "Receiver User Doesn't Exists. Enter correct Receiver Mobile Number";
		assertEquals(message, expectedMessage);
		
	}
	
	@Test
	@DisplayName("Money Transfer Unuccessful - Self Transfer")
	@Rollback(true)
	public void transferThirdTest() throws Exception {
		logger.info("Test Case - Money Transfer Unuccessful - Self Transfer");
		walletService.depositToWallet("8765432123", 100);
		String message  = walletService.transfer("8765432123", "8765432123", 60, "Rohit@123");
		String expectedMessage = "Self transfer not Allowed";
		assertEquals(message, expectedMessage);
		
	}
	
	@Test
	@DisplayName("Get Transactions By mobile")
	@Rollback(true)
	public void getTransactionsTest() throws Exception {
		logger.info("Test Case - Get Transactions By mobile");
		walletService.depositToWallet("8765432123", 100);
		List<String> transactionList = new ArrayList<>(); 
		walletService.getTransaction("8765432123").stream().forEach(f->transactionList.add(f.getTransactionCaption()));;
		List<String> expectedList = Arrays.asList("100.0+ is deposited to wallet");
		assertEquals(transactionList, expectedList);
		
	}
	
	@Test
	@DisplayName("Edit user successful")
	@Rollback(true)
	public void editUserTest() throws Exception {
		logger.info("Test Case - Edit user successful");
		walletService.depositToWallet("8765432123", 100);
		String message = walletService.editUser(new Wallet_Customer("Rohit S","8765432123","Rohit@123", 
				"rohit@gmail.com","Male", 31,"Your Vehicle Number","6578"));
		String expectedMessage = "Rohit S is updated successfully!..";
		assertEquals(message, expectedMessage);
		String updatedName = walletService.getUserByMobile("8765432123").getcustomerName();
		assertEquals("Rohit S", updatedName);
		
	}
	
	@Test
	@DisplayName("Change Password Successfull")
	@Rollback(true)
	public void changePasswordFirstTest() throws Exception {
		logger.info("Test Case - Change Password Successfull");
		String message = walletService.changePassword("8765432123", "Rohit@123", "Rohit@12345");
		String expectedMessage = "ok";
		assertEquals(message, expectedMessage);
		String updatedPassword = walletService.getUserByMobile("8765432123").getPassword();
		assertEquals("Rohit@12345", updatedPassword);
	}
	
	@Test
	@DisplayName("Change Password Unsuccessfull - Current Password is Wrong")
	@Rollback(true)
	public void changePasswordSecondTest() throws Exception {
		logger.info("Test Case - Change Password Unsuccessfull - Current Password is Wrong");
		String message = walletService.changePassword("8765432123", "Rohit@1234", "Rohit@12345");
		String expectedMessage = "Current Password is incorrect";
		assertEquals(message, expectedMessage);
	}
}
