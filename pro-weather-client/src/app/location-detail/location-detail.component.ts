import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LocationDataService } from '../location-data.service';
import { NgChartsModule } from 'ng2-charts'
import { ChartConfiguration, ChartOptions, ChartType } from 'chart.js';



@Component({
  selector: 'app-location-detail',
  templateUrl: './location-detail.component.html',
  styleUrls: ['./location-detail.component.css'],

})
export class LocationDetailComponent implements OnInit {


  latitude: string | null = null;
  longitude: string | null = null;
  day: string | null = null;
  isHour: boolean = false;
  locationDetails: any = null;
  locationDetailsHour: any = null;


  // Chart data and options
  public lineChartData: ChartConfiguration['data']['datasets'] = [];
  public lineChartOptions: ChartOptions = {
    responsive: true,
  };
  public lineChartPlugins = [];
  public lineChartLabels: string[] = [];
  public lineChartType: ChartType = 'line';

  constructor(private route: ActivatedRoute,private router: Router,
    private locationService: LocationDataService
  ) {}

  ngOnInit(): void {
    this.latitude = this.route.snapshot.paramMap.get('latitude');
    this.longitude = this.route.snapshot.paramMap.get('longitude');
    this.day = this.route.snapshot.paramMap.get('day');
    const isHourParam = this.route.snapshot.paramMap.get('isHour');

    if (isHourParam) {
      this.isHour = isHourParam.toLowerCase() === 'true';
    }

    if (this.latitude && this.longitude ) {
        this.fetchLocationDetails(this.latitude, this.longitude, this.isHour, this.day!);
    }
  }

  fetchLocationDetails(latitude: string, longitude: string, isHour: boolean, day: string): void {
    this.locationService.getLocationDetails(latitude, longitude, isHour, day).subscribe(
      (data: any) => {
        if(isHour) {
          this.locationDetailsHour = data;
          this.initializeChartDataHour();
          this.locationDetails = null;
        } else {
          this.locationDetails = data;
          this.initializeChartDataDay();
          this.locationDetailsHour = null;
        }
        
      },
      (error: any) => {
        console.error('Error fetching location details:', error);
      }
    );
  }

  


initializeChartDataHour(): void {
  const temperatures = this.locationDetailsHour.hourly.temperature_2m;
  const times = this.locationDetailsHour.hourly.time;
  const rains = this.locationDetailsHour.hourly.rain
  const winds = this.locationDetailsHour.hourly.wind_speed_10m
  this.lineChartData = [
    { data: temperatures, label: 'Temperature (°C)' },
    { data: rains, label: 'Rain (mm)' },
    { data: winds, label: 'Wind Speed (10m)' }
  ];

  this.lineChartLabels = times.map((time: string) => {
    const date = new Date(time);
    return `${date.getHours()}:00`;
  });
  //this.lineChartLabels = times;
}

initializeChartDataDay(): void {
  const maxTemps = this.locationDetails.daily.temperature_2m_max;
  const minTemps = this.locationDetails.daily.temperature_2m_min;
  const sunrises = this.locationDetails.daily.sunrise;
  const sunsets = this.locationDetails.daily.sunset;
  const rain_sums = this.locationDetails.daily.rain_sum;
  const wind_speed_10m_max = this.locationDetails.daily.wind_speed_10m_max;

  const times = this.locationDetails.daily.time;

  this.lineChartData = [
    { data: maxTemps, label: 'Max Temperature (°C)' },
    { data: minTemps, label: 'Min Temperature (°C)' },
    { data: sunrises, label: 'Sunrise' },
    { data: sunsets, label: 'Sunset' },
    { data: rain_sums, label: 'Rain (mm)' },
    { data: minTemps, label: 'Wind Speed (km/h)' }
  ];

  this.lineChartLabels = times;
}
}



