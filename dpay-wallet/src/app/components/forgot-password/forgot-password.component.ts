import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { WalletService } from 'src/app/services/wallet.service';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
  forgotPassForm: FormGroup;

  user: User;
  submitted: boolean = false;
  constructor(private formBuilder: FormBuilder, private walletService: WalletService, private router: Router) { }

  ngOnInit() {
    this.forgotPassForm = this.formBuilder.group({
      mobileNumber: ['', [Validators.required,Validators.maxLength(10),Validators.minLength(10), Validators.pattern("[0-9]*")]],
      securityQuestion: ['', Validators.required],
      answer: ['', Validators.required]
    });

  }

  verifyQuestion() {
    this.submitted = true;
    if (this.forgotPassForm.invalid) {
      return;
    }

    let mobile = this.forgotPassForm.controls.mobileNumber.value;
    let securityQue = this.forgotPassForm.controls.securityQuestion.value;
    let ans = this.forgotPassForm.controls.answer.value;

    this.walletService.getUserById(mobile).subscribe(data => {
        this.user = data;
        if (securityQue == this.user.securityQuestion && ans == this.user.answer) {
          alert("Your Password is " + this.user.password + ". Change your password for Security after Login");
          this.router.navigate(['/login']);
        }
        else {
          alert("Security Question and answer do not match..");
        }
    }, err => {
      console.log(err.error);
      alert(err.error.message);
      
    });
  }
}
