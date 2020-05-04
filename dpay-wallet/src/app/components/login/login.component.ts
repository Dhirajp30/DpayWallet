import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { WalletService } from 'src/app/services/wallet.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  submitted: boolean = false;

  success: string;

  constructor(private formBuilder: FormBuilder, private router: Router, private walletService: WalletService) { }

  ngOnInit() {
    
      this.loginForm = this.formBuilder.group({
        mobileNumber: ['', [Validators.required,Validators.maxLength(10),Validators.minLength(10), Validators.pattern("[0-9]*")]],
        password: ['', Validators.required]
      });

      if (localStorage.username != null) {
      this.router.navigate(['/dashboard']);
    }

  }

  verifyLogin() {
    this.submitted = true;
    if (this.loginForm.invalid) {
      return;
    }
    let mobile = this.loginForm.controls.mobileNumber.value;
    let password = this.loginForm.controls.password.value;

    this.walletService.successfullLogin(mobile, password).subscribe(data => {
      if (data) {
        localStorage.username = mobile;
        this.router.navigate(['dashboard'])
      }
      else {
        this.invalidLogin = true;
      }
    },
      err => {
        //on reject or on error
        console.log(err.error);
      });


  }

  invalidLogin: boolean = false;


}
