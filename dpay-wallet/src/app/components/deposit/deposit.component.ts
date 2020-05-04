import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormControl, ValidatorFn } from '@angular/forms';
import { WalletService } from 'src/app/services/wallet.service';

@Component({
  selector: 'app-deposit',
  templateUrl: './deposit.component.html',
  styleUrls: ['./deposit.component.css']
})
export class DepositComponent implements OnInit {
  addMoneyForm: FormGroup;

  username = localStorage.username;
  submitted: boolean = false;

  constructor(private formBuilder: FormBuilder, private router: Router, private walletService: WalletService) { }
  ngOnInit() {
    if (localStorage.username == null) {
      this.router.navigate(['/login']);
    }
    else {
      this.addMoneyForm = this.formBuilder.group({
        amount: ['100', [Validators.required, Validators.max(10000), Validators.pattern("[0-9]*"),this.customPatternValid({
          pattern: /^([^0][a-z A-Z 0-9]*)$/, msg: 'should not start with zero..'})]]
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

  addMoney() {
    this.submitted = true;

    if (this.addMoneyForm.invalid) {
      return;
    }

    this.walletService.doDeposit(this.username, this.addMoneyForm.controls.amount.value).subscribe(
      data => {
        alert(data);
        this.router.navigate(['/dashboard']);
      }, err => {
        console.log(err.error);
      }
    );

  }
}
