import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FavoriteLocationComponent } from './favorite-location.component';

describe('FavoriteLocationComponent', () => {
  let component: FavoriteLocationComponent;
  let fixture: ComponentFixture<FavoriteLocationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FavoriteLocationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FavoriteLocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
