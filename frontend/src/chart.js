window.drawChart = function (val1, val2) {
    // alert('hello world: ' + val1);
    var xValues = val1;
    var yValues = val2;

    new Chart("myChart", {
        type: "line",
        data: {
            labels: xValues,
            datasets: [{
                fill: false,
                backgroundColor: "orange",
                borderColor: "orange",
                data: yValues,
            }]
        },
        options: {
            legend: {display: false},
            scales: {
                yAxes: [{tricks: {min: 6, max: 16}}]
            }
        }
    });
}