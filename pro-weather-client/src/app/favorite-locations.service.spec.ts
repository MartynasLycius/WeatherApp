import { TestBed } from '@angular/core/testing';

import { FavoriteLocationsService } from './favorite-locations.service';

describe('FavoriteLocationsService', () => {
  let service: FavoriteLocationsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FavoriteLocationsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
