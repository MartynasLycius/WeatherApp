import { LitElement, html } from 'lit';
import {customElement} from 'lit/decorators.js';
import * as Highcharts from 'highcharts';

class HighchartsComponent extends HTMLElement {
    Meteogram(json, container) {
       Highcharts.chart('chart-container', {
           chart: {
             type: 'line',
             width: 980
           },
           title: {
             text: 'Weather Forecast'
           },
           subtitle: {
             text: 'Source: https://open-meteo.com'
           },
           xAxis: {
             categories: json.time
           },
           yAxis: {
             title: {
               text: 'Units'
             }
           },
           plotOptions: {
             line: {
               dataLabels: {
                 enabled: true
               },
               enableMouseTracking: true
             }
           },
           series: [{
             name: 'Temperature',
             data: json.temperature_2m_max != null ? json.temperature_2m_max : json.temperature_2m
           }, {
             name: 'Wind speed',
             data: json.wind_speed_10m_max != null ? json.wind_speed_10m_max : json.wind_speed_10m
           }, {
             name: 'Rain',
             data: json.rain_sum != null ? json.rain_sum : json.rain
           }]
         });
    }

    render() {
    }
}

customElements.define('highcharts-component', HighchartsComponent);