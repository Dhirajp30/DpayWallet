import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators, FormControl, ValidatorFn } from '@angular/forms';
import { WalletService } from 'src/app/services/wallet.service';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  editProfileForm: FormGroup;
  submitted: boolean = false;
  constructor(private formBuilder: FormBuilder,
    private router: Router, private walletService: WalletService) { }

  ngOnInit() {
    if (localStorage.username == null) {
      this.router.navigate(['/login']);
    }
    else {
      this.editProfileForm = this.formBuilder.group({
        customerName: ['', [Validators.required, Validators.maxLength(30), Validators.pattern("[A-Z][A-Z a-z _ ]*")]],
        age: ['', [Validators.required, Validators.min(15), Validators.maxLength(3), Validators.pattern("[1-9][0-9]*")]],
        gender: ['', Validators.required],
        mobileNumber: [{ value: '', disabled: true }, [Validators.required, Validators.pattern("[6-9][0-9]{9}")]],
        emailId: [{ value: '', disabled: true }, [Validators.required, Validators.email]],
        password: [{ value: '', disabled: true }],
        balance: [{ value: '', disabled: true }],
        securityQuestion: [{ value: '', disabled: true }],
        answer: [{ value: '', disabled: true }]
      });

       
      this.walletService.getUserById(localStorage.username).subscribe(data => {
        this.editProfileForm.setValue(data);
      }), err => {
        console.log(err.error);
      };
    }
    
  }

  logOutUser() {
    if (localStorage.username != null) {
      localStorage.removeItem("username");
      this.router.navigate(['/home']);
    }
  }

  editUser() {
    this.submitted = true;

    if (this.editProfileForm.invalid) {
      return;
    }

    this.walletService.editUser(this.editProfileForm.getRawValue()).subscribe(data => {
      alert(data);
      this.router.navigate(['/dashboard']);
    }, err => {
      console.log(err.error);
    });
  }
}
