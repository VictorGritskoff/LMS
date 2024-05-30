var dates = profitData.map(data => {
  return {
    x: new Date(data.orderTime).getTime(), // Преобразуем в метку времени
    y: data.profit
  };
});

var options = {
  series: [{
    name: 'Выручка',
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
    text: 'Выручка (руб) в этом году',
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
      formatter: function(val) {
        return val.toFixed(0);
      },
    },
    title: {
      text: 'Выручка',
      style: {
        fontSize: '16px'
      }
    },
  },
  xaxis: {
    type: 'datetime',
    labels: {
      datetimeFormatter: {
        year: 'yyyy',
        month: 'MMM',
        day: 'dd'
      }
    }
  },
  tooltip: {
    x: {
      formatter: function(val) {
        return new Date(val).toLocaleDateString('ru-RU');
      }
    },
    y: {
      formatter: function(val) {
        return val.toFixed(0);
      }
    },
    shared: false
  }
};

var chart = new ApexCharts(document.querySelector("#main-page-earnings-chart"), options);
chart.render();
