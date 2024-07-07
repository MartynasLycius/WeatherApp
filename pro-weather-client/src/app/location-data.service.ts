import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FavoriteLocation } from './model/favorite-location.model';

@Injectable({
  providedIn: 'root'
})
export class LocationDataService {
  private apiUrlLoc = 'http://localhost:8080/api/locations';
  private apiUrl = 'http://localhost:8080/api/';

  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Basic ' + btoa('user:user@123') // Replace with your actual credentials
  });

  constructor(private http: HttpClient) { }

  // Example of making a GET request with headers to fetch locations
  getLocations(cityName: string): Observable<any[]> {
    const url = `${this.apiUrl}locations?cityName=${cityName}`;
    return this.http.get<any[]>(url, { headers: this.headers });
  }
  getLocationDetails(latitude: string, longitude: string, isHour: boolean, day: string): Observable<any> {
    const url = `${this.apiUrl}weather?latitude=${latitude}&longitude=${longitude}&isHour=${isHour}&day=${day}`;
    return this.http.get<any[]>(url, { headers: this.headers });
  }

}
