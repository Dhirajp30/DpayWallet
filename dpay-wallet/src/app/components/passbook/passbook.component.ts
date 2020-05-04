import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { WalletService } from 'src/app/services/wallet.service';
import { Transaction } from 'src/app/models/transaction.model';

@Component({
  selector: 'app-passbook',
  templateUrl: './passbook.component.html',
  styleUrls: ['./passbook.component.css']
})
export class PassbookComponent implements OnInit {

  transactions: Transaction[];
  constructor(private router: Router, private walletService: WalletService) { }

  ngOnInit() {
    if (localStorage.username == null) {
      this.router.navigate(['/login']);
    }
    else {
      this.walletService.getTransactionsById(localStorage.username).subscribe(data => {
        this.transactions = data.sort((a, b) => b.transactionId - a.transactionId);
      },
        err => {
          console.log(err.error);
        });
    }

  }

  logOutUser() {
    if (localStorage.username != null) {
      localStorage.removeItem("username");
      this.router.navigate(['/home']);
    }
  }

}
