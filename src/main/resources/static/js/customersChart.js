var clientMonths = [];
var clientCountValues = [];

clientCount.forEach(function(item) {
  // Получение числового значения месяца из данных
  var monthNumber = item.month;

  // Преобразование числового значения месяца в его текстовое сокращение
  var monthNames = ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"];
  var monthName = monthNames[monthNumber - 1]; // Вычитаем 1, так как массивы в JavaScript индексируются с нуля

  // Добавление сокращенного названия месяца в массив
  clientMonths.push(monthName);
  clientCountValues.push(parseFloat(item.client));
});

var options = {
  series: [{
    name: "Клиенты",
    data: clientCountValues
  }],
  chart: {
    height: 350,
    type: 'line',
    zoom: {
      enabled: false
    }
  },
  dataLabels: {
    enabled: false
  },
  stroke: {
    curve: 'straight'
  },
  title: {
    enabled: false
  },
  grid: {
    row: {
      colors: ['#f3f3f3', 'transparent'],
      opacity: 0.5
    },
  },
  xaxis: {
    name: "Месяц",
    categories: clientMonths,
  }
};

var chart = new ApexCharts(document.querySelector("#chart"), options);
chart.render();
