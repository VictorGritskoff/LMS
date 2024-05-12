var dates = [
  { x: new Date('2024-01-01').getTime(), y: 1500 },
  { x: new Date('2024-02-01').getTime(), y: 1600 },
  { x: new Date('2024-03-01').getTime(), y: 1700 },
  { x: new Date('2024-04-01').getTime(), y: 1800 },
  { x: new Date('2024-05-01').getTime(), y: 1750 }, // новый элемент данных
  { x: new Date('2024-06-01').getTime(), y: 1850 }, // новый элемент данных
  { x: new Date('2024-07-01').getTime(), y: 1900 }, // новый элемент данных
  { x: new Date('2024-08-01').getTime(), y: 1950 }, // новый элемент данных
  { x: new Date('2024-09-01').getTime(), y: 2000 }  // новый элемент данных
];


var options = {
    series: [{
    name: 'Earnings',
    data: dates
  }],
    chart: {
    type: 'area',
    stacked: false,
    height: 310,
    zoom: {
      type: 'x',
      enabled: true,
      autoScaleYaxis: true
    },
    toolbar: {
      autoSelected: 'zoom'
    }
  },
  dataLabels: {
    enabled: false
  },
  markers: {
    size: 0,
  },
  title: {
    text: 'Earnings $ In This Year',
    align: 'center',
    style: {
      fontSize: '26px'
    }
  },
  fill: {
    type: 'gradient',
    gradient: {
      shadeIntensity: 1,
      inverseColors: false,
      opacityFrom: 0.5,
      opacityTo: 0,
      stops: [0, 90, 100]
    },
  },
  yaxis: {
    labels: {
      formatter: function (val) {
        return val.toFixed(0);
      },
    },
    title: {
      text: 'Price',
      style: {
        fontSize: '16px'
      }
    },
  },
  xaxis: {
    type: 'datetime',
  },
  tooltip: {
    shared: false,
    y: {
      formatter: function (val) {
        return val.toFixed(0);
      }
    }
  }
  };

  var chart = new ApexCharts(document.querySelector("#main-page-earnings-chart"), options);
  chart.render();