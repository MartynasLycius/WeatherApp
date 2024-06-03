// chart-component.js

import Chart from 'chart.js/auto';

window.initChart = function (element) {
    if (!element.chartInstance) {
        element.chartInstance = new Chart(element, {
            type: 'line',
            data: {
                labels: [],
                datasets: [
                    {
                        label: 'Temperature (°C)',
                        data: [],
                        borderColor: 'rgb(255, 99, 132)',
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                    },
                    {
                        label: 'Wind Speed (km/h)',
                        data: [],
                        borderColor: 'rgb(54, 162, 235)',
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                    },
                    {
                        label: 'Rainfall (mm)',
                        data: [],
                        borderColor: 'rgb(75, 192, 192)',
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    }
                ]
            },
            options: {
                scales: {
                    x: {
                        type: 'category',
                        labels: [],
                    }
                }
            }
        });
    }
};

window.renderChart = function (element, jsonData) {
    const data = JSON.parse(jsonData);
    const labels = data.time;
    const datasets = data.datasets;

    if (element.chartInstance) {
        element.chartInstance.data.labels = labels;
        element.chartInstance.data.datasets.forEach((dataset, index) => {
            dataset.data = datasets[index];
        });
        element.chartInstance.update();
    }
};
