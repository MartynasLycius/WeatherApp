import { Component, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  @Output() loginSuccess = new EventEmitter<boolean>();
  constructor(private router: Router
  ) {}

  ngOnInit(): void {
    localStorage.setItem('isLoggedIn', 'false'); // Store login state in localStorage
  }
  
  login() {
    if (this.username === 'user' && this.password === 'user@123') {
      localStorage.setItem('isLoggedIn', 'true'); // Store login state in localStorage
      this.loginSuccess.emit(true);
      this.router.navigate(['locations']);

    } else {
    alert('Invalid username or password==='+this.username );
    }
  }
}
