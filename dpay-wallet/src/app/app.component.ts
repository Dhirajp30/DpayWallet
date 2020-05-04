import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Dpay Wallet';

  // Creating date object
  todaysDate = new Date();

  constructor() {
    //setInterval -> asynchronous function
    setInterval(() => {
      this.todaysDate = new Date();
    }, 1000);
  }
}
