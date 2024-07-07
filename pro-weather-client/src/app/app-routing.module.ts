import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { LocationSearchComponent } from './location-search/location-search.component';
import { LocationDetailComponent } from './location-detail/location-detail.component';
import { LocationListComponent } from './location-list/location-list.component';

import { FavoriteLocationComponent } from './favorite-location/favorite-location.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LoginComponent },
  { path: 'locations', component: LocationListComponent },
  { path: 'location-detail/:latitude/:longitude/:isHour', component: LocationDetailComponent },
  { path: 'location-detail/:latitude/:longitude/:isHour/:day', component: LocationDetailComponent },
  { path: 'favorites', component: FavoriteLocationComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
