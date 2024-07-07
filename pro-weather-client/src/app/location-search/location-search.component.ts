import { Component , Output, EventEmitter} from '@angular/core';

@Component({
  selector: 'app-location-search',
  templateUrl: './location-search.component.html',
  styleUrls: ['./location-search.component.css']
})
export class LocationSearchComponent {
  searchQuery: string = '';
  @Output() search = new EventEmitter<string>();
  constructor() {}

  onSearch() {
    if (!this.searchQuery) {
      // Add your validation logic here if needed
      return;
    }

    
  
  //@Input() isLoggedIn: boolean = true;
    console.log('Search query:', this.searchQuery);
    // Handle the search logic here
  }
}

