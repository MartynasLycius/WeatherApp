import { Component } from '@angular/core';
import { LocationDataService } from './location-data.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  isLoggedIn: boolean = false;
  locations: any[] = [];
  dataMap:any;
  filteredLocations: any[] = [];
  currentPage: number = 1;
  itemsPerPage: number = 10;
  totalPages: number = 0;

  constructor(private locationService: LocationDataService,private router: Router) {}


  onLoginSuccess(isLoggedIn: boolean) {
    this.isLoggedIn = isLoggedIn;
    if (this.isLoggedIn) {
      this.router.navigate(['locations']);

      }
    }
 

  isLoggedInUser(): boolean {
    return localStorage.getItem('isLoggedIn') === 'true';
  }
  logout() {
    console.log("logout call");
    localStorage.setItem('isLoggedIn', 'false'); // Store login state in localStorage

  }

}
