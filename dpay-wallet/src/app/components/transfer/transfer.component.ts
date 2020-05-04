import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { WalletService } from 'src/app/services/wallet.service';
import { FormBuilder, FormGroup, Validators, FormControl, ValidatorFn } from '@angular/forms';

@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.css']
})
export class TransferComponent implements OnInit {
  users: User[];

  mob: any;

  searchText: any;

  transferForm: FormGroup;

  submitted: boolean = false;

  constructor(private formBuilder: FormBuilder, private router: Router, private walletService: WalletService) { }

  ngOnInit() {
    if (localStorage.username == null) {
      this.router.navigate(['/login']);
    }
    else {
      this.transferForm = this.formBuilder.group({
        mobileNumber: ['', [Validators.required,Validators.maxLength(10),Validators.minLength(10), Validators.pattern("[0-9]*")]],
        amount: ['100', [Validators.required, Validators.max(10000), Validators.pattern("[0-9]*"),this.customPatternValid({
          pattern: /^([^0][a-z A-Z 0-9]*)$/, msg: 'should not start with zero..'})]],
        password: ['', Validators.required]
      });
      this.walletService.getAllUsers().subscribe(data => {
        this.users = data.filter(user => user.mobileNumber != localStorage.username);
      },
        err => {
          //on reject or on error
          console.log(err.stack);
        });
    }
  }

  public customPatternValid(config: any): ValidatorFn {
    return (control: FormControl) => {
      let regExp: RegExp = config.pattern;
      if (control.value && !control.value.match(regExp)) {
        return {
          invalidMsg: config.msg
        };
      }
      else {
        return null;
      }
    };
  }

  logOutUser() {
    if (localStorage.username != null) {
      localStorage.removeItem("username");
      this.router.navigate(['/home']);
    }
  }

  transperMoney() {
    this.submitted = true;

    if (this.transferForm.invalid) {
      return;
    }
    let receiverMobile = this.transferForm.controls.mobileNumber.value;
    let amount = this.transferForm.controls.amount.value;
    let password = this.transferForm.controls.password.value;

    this.walletService.transfer(localStorage.username, receiverMobile, amount, password).subscribe(
      data => {
        if (data == "ok") {
          alert(amount + " is transferred to " + receiverMobile);
          this.router.navigate(['/dashboard']);
        }
        else {
          alert(data);
        }
      }, err => {
        console.log(err.error);
      }
    );
  }

  selectUser(user) {
    this.mob = user.mobileNumber;
    this.transferForm = this.formBuilder.group({
      mobileNumber: [this.mob, [Validators.required,Validators.minLength(10),Validators.maxLength(10), Validators.pattern("[0-9]*")]],
      amount: ['100', [Validators.required, Validators.max(10000), Validators.pattern("[1-9][0-9]{0,4}")]],
      password: ['', Validators.required]
    });
  }
}
