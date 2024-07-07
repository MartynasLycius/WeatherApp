
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FavoriteLocation } from './model/favorite-location.model';

@Injectable({
  providedIn: 'root'
})
export class FavoriteLocationsService {
  private apiUrl = 'http://localhost:8080/api/favlocations';
  
  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Basic ' + btoa('user:user@123') // Replace with your actual credentials
  });

  constructor(private http: HttpClient) { }

  getFavoriteLocations(): Observable<FavoriteLocation[]> {
    return this.http.get<FavoriteLocation[]>(this.apiUrl, { headers: this.headers });
  }

  getFavoriteLocationById(id: number): Observable<FavoriteLocation> {
    return this.http.get<FavoriteLocation>(`${this.apiUrl}/${id}`, { headers: this.headers });
  }

  creaFavoriteteLocation(location: FavoriteLocation): Observable<FavoriteLocation> {
   console.log(location);
    return this.http.post<FavoriteLocation>(this.apiUrl, location, { headers: this.headers });
  }

  updateFavoriteLocation(id: number, location: FavoriteLocation): Observable<FavoriteLocation> {
    return this.http.put<FavoriteLocation>(`${this.apiUrl}/${id}`, location, { headers: this.headers });
  }

  deleteFavoriteLocation(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.headers });
  }
}
