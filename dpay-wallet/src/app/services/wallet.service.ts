import { Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { HttpClient } from '@angular/common/http';
import { Transaction } from '../models/transaction.model';

@Injectable({
  providedIn: 'root'
})
export class WalletService {

  baseUrl: string = "http://localhost:8080/wallet/";

  constructor(private http: HttpClient) { }

  registerNewUser(user: User) {
    return this.http.post(this.baseUrl + "create", user, { responseType: 'text' as 'json' });
  }

  editUser(user: User) {
    return this.http.put(this.baseUrl + "editUser", user, { responseType: 'text' as 'json' })
  }

  getAllUsers() {
    return this.http.get<User[]>(this.baseUrl + "getAll");
  }

  successfullLogin(mobile, password) {
    return this.http.get<boolean>(this.baseUrl + "loginVerify/" + mobile + "/" + password);
  }

  getUserById(mobile) {
    return this.http.get<User>(this.baseUrl + "getUserByMobile/" + mobile);
  }

  getTransactionsById(mobile) {
    return this.http.get<Transaction[]>(this.baseUrl + "getTransactions/" + mobile);
  }

  getBalanceById(mobile) {
    return this.http.get<number>(this.baseUrl + "getBalance/" + mobile);
  }

  doDeposit(mobile, amount) {
    return this.http.put(this.baseUrl + "deposit/" + amount, mobile, { responseType: 'text' as 'json' });
  }

  withdraw(mobile, amount, password) {
    return this.http.put(this.baseUrl + "withdraw/" + amount + "/" + password, mobile, { responseType: 'text' as 'json' });
  }

  transfer(mobile, receiver, amount, password) {
    return this.http.put(this.baseUrl + "transfer/" + receiver + "/" + amount + "/" + password, mobile, { responseType: 'text' as 'json' });
  }

  changePassword(mobile, currentPass, newPass) {
    return this.http.put(this.baseUrl + "changePassword/" + currentPass + "/" + newPass, mobile, { responseType: 'text' as 'json' });
  }
}

