google.charts.load('current', { packages: ['corechart'] });
google.charts.setOnLoadCallback(drawLineChart);

function drawLineChart() {

    var data = google.visualization.arrayToDataTable([
        ['Tempo(min)', 'Status'],
        [0, 0]
    ]);

    var options = {
        title: 'Status API X',
        height: 480,
        width: 1040,
        hAxis: {
            title: "Tempo(min)",
            minValue: 0
        },
        vAxis: {
            title: 'Status',
            maxValue: 1
        },
        curveType: 'function'
    }

    var chart = new google.visualization.AreaChart(document.getElementById('chart'));

    var index = 0;


    // if (index > 10) {
    //     data.splice(index - 10, 1)
    // } else {
    setInterval(() => {
        var random = Math.round(Math.random());
        
        data.addRow([index, random]);
        chart.draw(data, options);
        index++;

    }, 1000)
    // }





    var dataY = google.visualization.arrayToDataTable([
        ['Tempo(min)', 'Status'],
        [0, 0]
    ]);

    var optionsY = {
        title: 'Status API Y',
        height: 480,
        width: 1040,
        hAxis: {
            title: "Tempo(min)",
            minValue: 0,
            // maxValue: 60
        },
        vAxis: {
            title: 'Status(%)',
            maxValue: 1
        }
    }

    var chartY = new google.visualization.AreaChart(document.getElementById('chart_y'));

    var indexY = 0;

    setInterval(() => {
        var randomY = Math.round(Math.random());
        dataY.addRow([indexY, randomY]);
        chartY.draw(dataY, optionsY);
        indexY++;
    }, 1000)
}



function mostrarX() {
    document.getElementById("graficos_x").style.display = "block";
    document.getElementById("graficos_y").style.display = "none";
}
function mostrarY() {
    document.getElementById("graficos_y").style.display = "block";
    document.getElementById("graficos_x").style.display = "none";
}
function mostrarTodos() {
    document.getElementById("graficos_y").style.display = "block";
    document.getElementById("graficos_x").style.display = "block";
}




google.charts.load('current', { packages: ['corechart'] });
google.charts.setOnLoadCallback(drawLineChartCpu);

function drawLineChartCpu() {

    var data = google.visualization.arrayToDataTable([
        ['Tempo(min)', 'CPU'],
        [0, 0]
    ]);

    var options = {
        title: 'Uso CPU',
        height: 240,
        width: 1040,
        colors: ['#f00'],
        curveType: 'function',
        hAxis: {
            title: "Tempo(min)",
            minValue: 0
        },
        vAxis: {
            title: 'CPU',
            maxValue: 100
        }
    }

    var chart = new google.visualization.AreaChart(document.getElementById('chartCpu'));

    var index = 0;

    setInterval(() => {
        var random = Math.floor(Math.random() * 101);
        data.addRow([index, random]);
        chart.draw(data, options);
        index++;
    }, 1000)
}

google.charts.load('current', { packages: ['corechart'] });
google.charts.setOnLoadCallback(drawLineChartMemoria);

function drawLineChartMemoria() {

    var data = google.visualization.arrayToDataTable([
        ['Tempo(min)', 'Memória'],
        [0, 0]
    ]);

    var options = {
        title: 'Uso Memória',
        height: 240,
        width: 1040,
        curveType: 'function',
        colors: ['#0f0'],
        hAxis: {
            title: "Tempo(min)",
            minValue: 0
        },
        vAxis: {
            title: 'Memória',
            maxValue: 100
        }
    }

    var chart = new google.visualization.AreaChart(document.getElementById('chartMemoria'));

    var index = 0;

    setInterval(() => {
        var random = Math.floor(Math.random() * 101);
        data.addRow([index, random]);
        chart.draw(data, options);
        index++;
    }, 1000)
}

google.charts.load('current', { packages: ['corechart'] });
google.charts.setOnLoadCallback(drawLineChartDisk);

function drawLineChartDisk() {

    var data = google.visualization.arrayToDataTable([
        ['Tempo(min)', 'Disco'],
        [0, 0]
    ]);

    var options = {
        title: 'Uso Disco',
        height: 240,
        width: 1040,
        curveType: 'function',
        hAxis: {
            title: "Tempo(min)",
            minValue: 0
        },
        vAxis: {
            title: 'Disco',
            maxValue: 100
        }
    }

    var chart = new google.visualization.AreaChart(document.getElementById('chartDisk'));

    var index = 0;

    setInterval(() => {
        var random = Math.floor(Math.random() * 101);
        data.addRow([index, random]);
        chart.draw(data, options);
        index++;
    }, 1000)
}

google.charts.load('current', { 'packages': ['gauge'] });
google.charts.setOnLoadCallback(drawGaugeChart);

function drawGaugeChart() {

    var data = google.visualization.arrayToDataTable([
        ['Label', 'Value'],
        ['Memory', 80],
        ['CPU', 55],
        ['Disk', 68]
    ]);

    var options = {
        width: 400, height: 120,
        redFrom: 75, redTo: 100,
        yellowFrom: 45, yellowTo: 75,
        greenFrom: 0, greenTo: 45,
        minorTicks: 5
    };

    var chart = new google.visualization.Gauge(document.getElementById('chart_div'));

    // chart.draw(data, options);

    setInterval(function () {
        data.setValue(0, 1, 40 + Math.round(60 * Math.random()));
        chart.draw(data, options);
    }, 1000);
    setInterval(function () {
        data.setValue(1, 1, 40 + Math.round(60 * Math.random()));
        chart.draw(data, options);
    }, 1000);
    setInterval(function () {
        data.setValue(2, 1, 60 + Math.round(20 * Math.random()));
        chart.draw(data, options);
    }, 1000);
}





google.charts.load('current', { packages: ['corechart'] });
google.charts.setOnLoadCallback(drawLineChartCpu2);

function drawLineChartCpu2() {

    var data = google.visualization.arrayToDataTable([
        ['Tempo(min)', 'CPU'],
        [0, 0]
    ]);

    var options = {
        title: 'Uso CPU',
        height: 240,
        width: 1040,
        colors: ['#f00'],
        curveType: 'function',
        hAxis: {
            title: "Tempo(min)",
            minValue: 0
        },
        vAxis: {
            title: 'CPU',
            maxValue: 100
        }
    }

    var chart = new google.visualization.AreaChart(document.getElementById('chartCpu2'));

    var index = 0;

    setInterval(() => {
        var random = Math.floor(Math.random() * 101);
        data.addRow([index, random]);
        chart.draw(data, options);
        index++;
    }, 1000)
}

google.charts.load('current', { packages: ['corechart'] });
google.charts.setOnLoadCallback(drawLineChartMemoria2);

function drawLineChartMemoria2() {

    var data = google.visualization.arrayToDataTable([
        ['Tempo(min)', 'Memória'],
        [0, 0]
    ]);

    var options = {
        title: 'Uso Memória',
        height: 240,
        width: 1040,
        curveType: 'function',
        colors: ['#0f0'],
        hAxis: {
            title: "Tempo(min)",
            minValue: 0
        },
        vAxis: {
            title: 'Memória',
            maxValue: 100
        }
    }

    var chart = new google.visualization.AreaChart(document.getElementById('chartMemoria2'));

    var index = 0;

    setInterval(() => {
        var random = Math.floor(Math.random() * 101);
        data.addRow([index, random]);
        chart.draw(data, options);
        index++;
    }, 1000)
}

google.charts.load('current', { packages: ['corechart'] });
google.charts.setOnLoadCallback(drawLineChartDisk2);

function drawLineChartDisk2() {

    var data = google.visualization.arrayToDataTable([
        ['Tempo(min)', 'Disco'],
        [0, 0]
    ]);

    var options = {
        title: 'Uso Disco',
        height: 240,
        width: 1040,
        curveType: 'function',
        hAxis: {
            title: "Tempo(min)",
            minValue: 0
        },
        vAxis: {
            title: 'Disco',
            maxValue: 100
        }
    }

    var chart = new google.visualization.AreaChart(document.getElementById('chartDisk2'));

    var index = 0;

    setInterval(() => {
        var random = Math.floor(Math.random() * 101);
        data.addRow([index, random]);
        chart.draw(data, options);
        index++;
    }, 1000)
}

google.charts.load('current', { 'packages': ['gauge'] });
google.charts.setOnLoadCallback(drawGaugeChart2);

function drawGaugeChart2() {

    var data = google.visualization.arrayToDataTable([
        ['Label', 'Value'],
        ['Memory', 80],
        ['CPU', 55],
        ['Disk', 68]
    ]);

    var options = {
        width: 400, height: 120,
        redFrom: 75, redTo: 100,
        yellowFrom: 45, yellowTo: 75,
        greenFrom: 0, greenTo: 45,
        minorTicks: 5
    };

    var chart = new google.visualization.Gauge(document.getElementById('chart_div2'));

    // chart.draw(data, options);

    setInterval(function () {
        data.setValue(0, 1, 40 + Math.round(60 * Math.random()));
        chart.draw(data, options);
    }, 1000);
    setInterval(function () {
        data.setValue(1, 1, 40 + Math.round(60 * Math.random()));
        chart.draw(data, options);
    }, 1000);
    setInterval(function () {
        data.setValue(2, 1, 60 + Math.round(20 * Math.random()));
        chart.draw(data, options);
    }, 1000);
}






google.charts.load('current', { packages: ['corechart'] });
google.charts.setOnLoadCallback(drawLineChartCpu3);

function drawLineChartCpu3() {

    var data = google.visualization.arrayToDataTable([
        ['Tempo(min)', 'CPU'],
        [0, 0]
    ]);

    var options = {
        title: 'Uso CPU',
        height: 240,
        width: 1040,
        colors: ['#f00'],
        curveType: 'function',
        hAxis: {
            title: "Tempo(min)",
            minValue: 0
        },
        vAxis: {
            title: 'CPU',
            maxValue: 100
        }
    }

    var chart = new google.visualization.AreaChart(document.getElementById('chartCpu3'));

    var index = 0;

    setInterval(() => {
        var random = Math.floor(Math.random() * 101);
        data.addRow([index, random]);
        chart.draw(data, options);
        index++;
    }, 1000)
}

google.charts.load('current', { packages: ['corechart'] });
google.charts.setOnLoadCallback(drawLineChartMemoria3);

function drawLineChartMemoria3() {

    var data = google.visualization.arrayToDataTable([
        ['Tempo(min)', 'Memória'],
        [0, 0]
    ]);

    var options = {
        title: 'Uso Memória',
        height: 240,
        width: 1040,
        curveType: 'function',
        colors: ['#0f0'],
        hAxis: {
            title: "Tempo(min)",
            minValue: 0
        },
        vAxis: {
            title: 'Memória',
            maxValue: 100
        }
    }

    var chart = new google.visualization.AreaChart(document.getElementById('chartMemoria3'));

    var index = 0;

    setInterval(() => {
        var random = Math.floor(Math.random() * 101);
        data.addRow([index, random]);
        chart.draw(data, options);
        index++;
    }, 1000)
}

google.charts.load('current', { packages: ['corechart'] });
google.charts.setOnLoadCallback(drawLineChartDisk3);

function drawLineChartDisk3() {

    var data = google.visualization.arrayToDataTable([
        ['Tempo(min)', 'Disco'],
        [0, 0]
    ]);

    var options = {
        title: 'Uso Disco',
        height: 240,
        width: 1040,
        curveType: 'function',
        hAxis: {
            title: "Tempo(min)",
            minValue: 0
        },
        vAxis: {
            title: 'Disco',
            maxValue: 100
        }
    }

    var chart = new google.visualization.AreaChart(document.getElementById('chartDisk3'));

    var index = 0;

    setInterval(() => {
        var random = Math.floor(Math.random() * 101);
        data.addRow([index, random]);
        chart.draw(data, options);
        index++;
    }, 1000)
}

google.charts.load('current', { 'packages': ['gauge'] });
google.charts.setOnLoadCallback(drawGaugeChart3);

function drawGaugeChart3() {

    var data = google.visualization.arrayToDataTable([
        ['Label', 'Value'],
        ['Memory', 80],
        ['CPU', 55],
        ['Disk', 68]
    ]);

    var options = {
        width: 400, height: 120,
        redFrom: 75, redTo: 100,
        yellowFrom: 45, yellowTo: 75,
        greenFrom: 0, greenTo: 45,
        minorTicks: 5
    };

    var chart = new google.visualization.Gauge(document.getElementById('chart_div3'));

    // chart.draw(data, options);

    setInterval(function () {
        data.setValue(0, 1, 40 + Math.round(60 * Math.random()));
        chart.draw(data, options);
    }, 1000);
    setInterval(function () {
        data.setValue(1, 1, 40 + Math.round(60 * Math.random()));
        chart.draw(data, options);
    }, 1000);
    setInterval(function () {
        data.setValue(2, 1, 60 + Math.round(20 * Math.random()));
        chart.draw(data, options);
    }, 1000);
}

function mostrar1() {
    document.getElementById("charts").style.display = "block";
    document.getElementById("charts2").style.display = "none";
    document.getElementById("charts3").style.display = "none";
}
function mostrar2() {
    document.getElementById("charts").style.display = "none";
    document.getElementById("charts2").style.display = "block";
    document.getElementById("charts3").style.display = "none";
}
function mostrar3() {
    document.getElementById("charts").style.display = "none";
    document.getElementById("charts2").style.display = "none";
    document.getElementById("charts3").style.display = "block";
}
function mostrarTodas() {
    document.getElementById("charts").style.display = "block";
    document.getElementById("charts2").style.display = "block";
    document.getElementById("charts3").style.display = "block";
}