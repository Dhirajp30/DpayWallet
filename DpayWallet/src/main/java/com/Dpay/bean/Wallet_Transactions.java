package com.Dpay.bean;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Wallet_Transactions implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(length = 10)
	private int transactionId;
	@Column(nullable = false,length = 70)
	private String transactionCaption;
	@Column(length = 15,nullable = false)
	@Temporal(TemporalType.DATE)
	private Date transactionDate;
	@Column(length = 15,nullable = false)
	@Temporal(TemporalType.TIME)
	private Date transactionTime;
	//@JsonManagedReference
	 @ManyToOne
	 @JoinColumn(name="mobileNumber")
	 Wallet_Customer walletCustomer;
	 public Wallet_Transactions() 
	 {
		 
	 }
	 
	
	public Wallet_Transactions(String transactionCaption) 
	{
		super();
		this.transactionCaption = transactionCaption;
	}


	public int getTransactionId() 
	{
		return transactionId;
	}


	public void setTransactionId(int transactionId) 
	{
		this.transactionId = transactionId;
	}


	public String getTransactionCaption() 
	{
		return transactionCaption;
	}


	public void setTransactionCaption(String transactionCaption) 
	{
		this.transactionCaption = transactionCaption;
	}


	public Date getTransactionDate() 
	{
		return transactionDate;
	}


	public void setTransactionDate(Date transactionDate) 
	{
		this.transactionDate = transactionDate;
	}


	public Date getTransactionTime() 
	{
		return transactionTime;
	}


	public void setTransactionTime(Date transactionTime) 
	{
		this.transactionTime = transactionTime;
	}


	public Wallet_Customer getWalletCustomer() 
	{
		return walletCustomer;
	}


	public void setWalletCustomer(Wallet_Customer walletCustomer) 
	{
		this.walletCustomer = walletCustomer;
	}


	@Override
	public String toString() 
	{
		return "Wallet_Transactions [transactionId=" + transactionId + ", transactionCaption=" + transactionCaption
				+ ", transactionDate=" + transactionDate + ", transactionTime=" + transactionTime + ", walletCustomer="
				+ walletCustomer + "]";
	}
	
}
