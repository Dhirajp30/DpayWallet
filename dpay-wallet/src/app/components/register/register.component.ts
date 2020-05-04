import { Component, OnInit } from '@angular/core';
import { WalletService } from 'src/app/services/wallet.service';
import { FormGroup, FormBuilder, Validators, ValidatorFn, FormControl } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  addForm: FormGroup;
  submitted: boolean = false;
  passMatchInvalid: boolean = false;

  constructor(private formBuilder: FormBuilder,
    private router: Router, private walletService: WalletService) { }

  ngOnInit(): void {
    this.addForm = this.formBuilder.group({

      customerName: ['', [Validators.required, Validators.maxLength(30), this.customPatternValid({
        pattern: /^([^0-9]*)$/, msg: 'Numbers not Allowed..'}), Validators.pattern("[0-9 A-Z][A-Z a-z 0-9 _ ]*")]],
      age: ['', [Validators.required, Validators.min(15), Validators.maxLength(3), Validators.pattern("[0-9]*"), this.customPatternValid({
        pattern: /^([^0][a-z A-Z 0-9]*)$/, msg: 'should not start with zero..'})]],
      gender: ['', Validators.required],
      mobileNumber: ['', [Validators.required, Validators.maxLength(10), Validators.minLength(10), Validators.pattern("[a-zA-Z6-9][A-Za-z0-9]*"), this.customPatternValid({
        pattern: /^([^a-b A-z]*)$/, msg: 'only numbers are expected'})]],
      emailId: ['', [Validators.required, Validators.maxLength(50), Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(14), Validators.pattern("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].*")]],
      confirmPass: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(14), Validators.pattern("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].*")]],
      securityQuestion: ['', Validators.required],
      answer: ['', [Validators.required, Validators.maxLength(20), Validators.pattern("[A-Z a-z 0-9]{4,}")]]
    });

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

  addUser() {
    this.submitted = true;
    if (this.addForm.invalid) {
      return;
    }
    let password = this.addForm.controls.password.value;
    let confirmPass = this.addForm.controls.confirmPass.value;

    if (password != confirmPass) {
      this.passMatchInvalid = true;
      return;
    }

    //on successful validations, execute below code snippet

    console.log(this.addForm.value);

    this.walletService.registerNewUser(this.addForm.value).subscribe(data => {
      alert(data);
      this.router.navigate(['home']);

    }, err => {
      console.log(err.error);

    })

  }
}
