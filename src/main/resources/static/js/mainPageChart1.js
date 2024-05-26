var profitData = [
  { orderTime: '2024-01-01', profit: 100 },
  { orderTime: '2024-02-01', profit: 250 },
  { orderTime: '2024-03-01', profit: 350 },
  { orderTime: '2024-04-01', profit: 600 },
];

function fillMissingMonths(profitData) {
  var profitByMonth = new Map();

  profitData.forEach(function(item) {
    var orderDate = new Date(item.orderTime);
    var month = orderDate.getMonth() + 1;
    var year = orderDate.getFullYear();
    var key = year + '-' + month;

    if (profitByMonth.has(key)) {
      profitByMonth.get(key).profit += item.profit;
    } else {
      profitByMonth.set(key, { orderTime: year + '-' + month, profit: item.profit });
    }
  });

  var filledProfitData = [];
  for (var monthIndex = 1; monthIndex <= 4; monthIndex++) {
    var key = '2024-' + monthIndex;
    var monthData = profitByMonth.get(key);
    if (monthData) {
      filledProfitData.push(monthData);
    } else {
      filledProfitData.push({ orderTime: key, profit: 0 });
    }
  }

  return filledProfitData;
}

var filledProfitData = fillMissingMonths(profitData);

var dates = filledProfitData.map(item => ({
  x: new Date(item.orderTime).getTime(),
  y: item.profit
}));

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
