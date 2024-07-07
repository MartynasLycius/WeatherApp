

// location.component.ts
import { Component, OnInit } from '@angular/core';
import { FavoriteLocationsService } from '../favorite-locations.service';
import { FavoriteLocation } from '../model/favorite-location.model';

@Component({
  selector: 'app-location',
  templateUrl: './favorite-location.component.html',
  styleUrls: ['./favorite-location.component.css']
})
export class FavoriteLocationComponent implements OnInit {
  favoritelocations: FavoriteLocation[] = [];

  constructor(private favoriteLocationsService: FavoriteLocationsService) { }

  ngOnInit(): void {
    this.loadLocations();
  }

  loadLocations(): void {
    this.favoriteLocationsService.getFavoriteLocations().subscribe(
      data => this.favoritelocations = data,
      error => console.error('Error fetching locations', error)
    );
  }

  deleteLocation(id: number): void {
    this.favoriteLocationsService.deleteFavoriteLocation(id).subscribe(
      () => this.loadLocations(),
      error => console.error('Error deleting location', error)
    );
  }
}

