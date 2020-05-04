package com.Dpay.bean;


import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Wallet_Customer implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	@Column(length = 30, nullable = false)
	private String customerName;
	@Column(length = 10)
	@Id
	private String mobileNumber;
	@Column(nullable = false, length = 14)
	private String password;
	@Column(length = 15, nullable = false)
	private double balance;
	@Column(length = 50, nullable = false)
	private String emailId;
	@Column(length = 12, nullable = false)
	private String gender;
	@Column(length = 4, nullable = false)
	private int age;
	@Column(length = 20, nullable = false)
	private String securityQuestion;
	@Column(length = 20, nullable = false)
	private String answer;
	@JsonBackReference
	@OneToMany(mappedBy="walletCustomer",cascade=CascadeType.ALL)
	private List<Wallet_Transactions> transactionList;
	
	public Wallet_Customer() 
	{
		
	}
	
	
	public Wallet_Customer(String customerName, String mobileNumber, String password, 
			String emailId, String gender, int age,String securityQuestion, String answer) 
	{
		super();
		this.customerName = customerName;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.emailId = emailId;
		this.gender = gender;
		this.age = age;
		this.securityQuestion = securityQuestion;
		this.answer = answer;
		transactionList= new ArrayList<>();
		balance=0.0;
	}


	
	public String getcustomerName() 
	{
		return customerName;
	}


	public void setcustomerName(String customerName) 
	{
		this.customerName = customerName;
	}


	public String getMobileNumber() 
	{
		return mobileNumber;
	}


	public void setMobileNumber(String mobileNumber) 
	{
		this.mobileNumber = mobileNumber;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) 
	{
		this.password = password;
	}


	public double getBalance() 
	{
		return balance;
	}


	public void setBalance(double balance) 
	{
		this.balance = balance;
	}


	public String getEmailId() 
	{
		return emailId;
	}


	public void setEmailId(String emailId) 
	{
		this.emailId = emailId;
	}


	public String getGender() 
	{
		return gender;
	}


	public void setGender(String gender) 
	{
		this.gender = gender;
	}


	public int getAge() 
	{
		return age;
	}


	public void setAge(int age) 
	{
		this.age = age;
	}


	public String getSecurityQuestion() 
	{
		return securityQuestion;
	}


	public void setSecurityQuestion(String securityQuestion) 
	{
		this.securityQuestion = securityQuestion;
	}


	public String getAnswer() 
	{
		return answer;
	}


	public void setAnswer(String answer) 
	{
		this.answer = answer;
	}


	public List<Wallet_Transactions> getTransactionList() 
	{
		return transactionList;
	}


	public void setTransactionList(List<Wallet_Transactions> transactionList) 
	{
		this.transactionList = transactionList;
	}


	public static long getSerialversionuid() 
	{
		return serialVersionUID;
	}


	
	
	@Override
	public String toString() 
	{
		return "Wallet_Customer [customerName=" + customerName + ", mobileNumber=" + mobileNumber + ", password=" + password
				+ ", balance=" + balance + ", emailId=" + emailId + ", gender=" + gender + ", age=" + age
				+ ", securityQuestion=" + securityQuestion + ", answer=" + answer + ", transactionList="
				+ transactionList + "]";
	}


	public void addTransaction(Wallet_Transactions transaction) 
	{
		transaction.setWalletCustomer(this);
		transaction.setTransactionTime(Calendar.getInstance().getTime());
		transaction.setTransactionDate(Calendar.getInstance().getTime());
		this.getTransactionList().add(transaction);
	}
}
	

