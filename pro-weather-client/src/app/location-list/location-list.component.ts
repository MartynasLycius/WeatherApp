import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { LocationDataService } from '../location-data.service';
import { FavoriteLocationsService } from '../favorite-locations.service';

@Component({
  selector: 'app-location-list',
  templateUrl: './location-list.component.html',
  styleUrls: ['./location-list.component.css']
})
export class LocationListComponent {
  isLoggedIn: boolean = false;
  locations: any[] = [];
  dataMap:any;
  filteredLocations: any[] = [];
  currentPage: number = 1;
  itemsPerPage: number = 10;
  totalPages: number = 0;

  // @Input() locations: any[] = [];
  // @Input() isLoggedIn: boolean = true;
  // currentPage: number = 1;
  // itemsPerPage: number = 10;
  // @Input() totalPages: number = 10;

  constructor(private router: Router,
    private locationService: LocationDataService, private favoriteLocationService: FavoriteLocationsService
  ) {}

  get paginatedLocations(): any[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.filteredLocations.slice(startIndex, startIndex + this.itemsPerPage);
  }


  
  
  calculateTotalPages() {
    const totalItems = this.filteredLocations.length;
    this.totalPages = Math.ceil(totalItems / this.itemsPerPage);
  }

  ngOnInit() {
    // Check if user is already logged in (example: check localStorage or token)
    this.isLoggedIn = this.isLoggedInUser(); // Implement this method as per your authentication mechanism
    if (this.isLoggedIn) {
      this.locationService.getLocations('dhaka').subscribe(
        data => {
          this.dataMap = data;
          this.filteredLocations = this.dataMap.results;
          this.calculateTotalPages();
      });
    }
  }

  onSearch(query: string) {
    if (query.trim() === '') {
      this.filteredLocations = [...this.locations]; // Reset to all locations if search query is empty
    } else {
    this.locationService.getLocations(query).subscribe(
      data => {
        this.dataMap = data;
        this.filteredLocations = this.dataMap.results;
        this.calculateTotalPages();
      },
      error => {
        console.error('Error fetching locations:', error);
      }
    );
    }
  }

// Function to handle page change
onPageChange(pageNumber: number) {
  this.currentPage = pageNumber;
}

onNext() {
  if (this.currentPage < this.totalPages) {
    this.currentPage++;
  }
}

onPrev() {
  if (this.currentPage > 1) {
    this.currentPage--;
  }
}

onLast() {
  this.currentPage = this.totalPages;
}

getPaginationNumbers(): number[] {
  const maxPagesToShow = 5;
  let pages: number[] = [];
  if (this.totalPages <= maxPagesToShow) {
    pages = Array.from({ length: this.totalPages }, (_, i) => i + 1);
  } else {
    const start = Math.max(1, this.currentPage - Math.floor(maxPagesToShow / 2));
    const end = Math.min(this.totalPages, start + maxPagesToShow - 1);

    pages = Array.from({ length: end - start + 1 }, (_, i) => start + i);

    if (start > 1) {
      pages.unshift(1);
      if (start > 2) {
        pages.splice(1, 0, -1); // -1 indicates "..."
      }
    }
    if (end < this.totalPages) {
      pages.push(this.totalPages);
      if (end < this.totalPages - 1) {
        pages.splice(pages.length - 1, 0, -1); // -1 indicates "..."
      }
    }
  }
  return pages;
}

// navigateTab(path:string) {

//   this.router.navigate([path], {
//        relativeTo: this.activatedRoute // to make the navigation happen relative to the current route!
//   })
// }


isLoggedInUser(): boolean {
  // Example: Check localStorage or token for authentication state
  return this.isLoggedIn =localStorage.getItem('isLoggedIn') === 'true';
}


toggleFavorite(favoritelocation: any): void {

  this.favoriteLocationService.creaFavoriteteLocation(favoritelocation).subscribe(
    data => {
      console.log("added fav===="+favoritelocation);
    }
  )
}
}
