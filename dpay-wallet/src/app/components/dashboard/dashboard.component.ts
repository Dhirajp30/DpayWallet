import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { WalletService } from 'src/app/services/wallet.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  user: User;
  customerName: string;
  constructor(private router: Router, private walletService: WalletService) {
  }

  ngOnInit() {
    if (localStorage.username == null) {
      this.router.navigate(['/login']);
    }
    else {
      this.walletService.getUserById(localStorage.username).subscribe(data => {
        this.user = data;
        this.customerName = this.user.customerName;
      },
        err => {
          //on reject or on error
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
