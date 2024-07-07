import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';

import { NgChartsModule } from 'ng2-charts';




import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { LocationSearchComponent } from './location-search/location-search.component';
import { LocationListComponent } from './location-list/location-list.component';
import { LocationDetailComponent } from './location-detail/location-detail.component';
import { MenuComponent } from './menu/menu.component';
import { FavoriteLocationComponent } from './favorite-location/favorite-location.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LocationSearchComponent,
    LocationListComponent,
    LocationDetailComponent,
    MenuComponent,
    FavoriteLocationComponent
    
    //FavoriteLocationComponent,
    //ForecastComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule ,
    NgChartsModule
  ],
  providers: [],


  bootstrap: [AppComponent]
})
export class AppModule { }
