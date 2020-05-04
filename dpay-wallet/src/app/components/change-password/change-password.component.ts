import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { WalletService } from 'src/app/services/wallet.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  changePasswordForm: FormGroup;
  submitted: boolean = false;
  passMatchInvalid: boolean = false;
  constructor(private formBuilder: FormBuilder, private router: Router, private walletService: WalletService) { }

  ngOnInit() {
    if (localStorage.username == null) {
      this.router.navigate(['/login']);
    }
    else {
      this.changePasswordForm = this.formBuilder.group({
        currentPass: ['', Validators.required],
        newPass: ['', [Validators.required,Validators.minLength(8),Validators.maxLength(14), Validators.pattern("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].*")]],
        confirmPass: ['', [Validators.required,Validators.minLength(8),Validators.maxLength(14), Validators.pattern("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].*")]]
      });
    }
  }
  logOutUser() {
    if (localStorage.username != null) {
      localStorage.removeItem("username");
      this.router.navigate(['/home']);
    }
  }

  changePassword() {
    this.submitted = true;

    if (this.changePasswordForm.invalid) {
      return;
    }
    let currentPass = this.changePasswordForm.controls.currentPass.value;
    let newPass = this.changePasswordForm.controls.newPass.value;
    let confirmPass = this.changePasswordForm.controls.confirmPass.value;

    if (newPass != confirmPass) {
      this.passMatchInvalid = true;
      return;
    }

    this.walletService.changePassword(localStorage.username, currentPass, newPass).subscribe(data => {
      if (data == "ok") {
        alert("Password is changed Successfully...");
        this.router.navigate(['/profile']);
      } else {
        alert(data);
        this.router.navigateByUrl('/Refresh', { skipLocationChange: true }).then(() => {
          this.router.navigate(['/change-password']);
        });
      }
    }, err => {
      console.log(err.error);
    });
  }
}
