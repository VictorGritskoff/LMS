var options = {
  series: [{
    name: 'Действительное',
    data: expectedCustomersCount
  }],
  chart: {
    height: 350,
    type: 'bar'
  },
  title: {
    enabled: false
  },
  plotOptions: {
    bar: {
      columnWidth: '60%'
    }
  },
  colors: ['#00E396'],
  dataLabels: {
    enabled: false
  },
  legend: {
    show: true,
    showForSingleSeries: true,
    customLegendItems: ['Действительное', 'Ожидаемое'],
    markers: {
      fillColors: ['#00E396', '#775DD0']
    }
  }
};

var chart = new ApexCharts(document.querySelector("#customers-activity-chart"), options);
chart.render();