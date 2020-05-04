import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators, FormControl, ValidatorFn } from '@angular/forms';
import { WalletService } from 'src/app/services/wallet.service';

@Component({
  selector: 'app-withdraw',
  templateUrl: './withdraw.component.html',
  styleUrls: ['./withdraw.component.css']
})
export class WithdrawComponent implements OnInit {

  withdrawForm: FormGroup;

  submitted: boolean = false;

  constructor(private formBuilder: FormBuilder, private router: Router, private walletService: WalletService) { }

  ngOnInit() {
    if (localStorage.username == null) {
      this.router.navigate(['/login']);
    }
    else {
      this.withdrawForm = this.formBuilder.group({
        amount: ['100', [Validators.required, Validators.max(10000), Validators.pattern("[0-9]*"),this.customPatternValid({
          pattern: /^([^0][a-z A-Z 0-9]*)$/, msg: 'should not start with zero..'})]],
        password: ['', Validators.required]
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

  withdrawMoney() {
    this.submitted = true;

    if (this.withdrawForm.invalid) {
      return;
    }

    this.walletService.withdraw(localStorage.username, this.withdrawForm.controls.amount.value, this.withdrawForm.controls.password.value).subscribe(
      data => {
        if (data == "ok") {
          alert(this.withdrawForm.controls.amount.value + "- is withdrwan from wallet");
          this.router.navigate(['/dashboard']);
        }
        else
          alert(data);
      }, err => {
        console.log(err.error);
      }
    );
  }
}
