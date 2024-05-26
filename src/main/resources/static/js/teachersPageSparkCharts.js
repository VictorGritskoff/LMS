window.Apex = {
  dataLabels: {
    enabled: false
  }
};

// ПЕРВЫЙ ГРАФИК
var weeks = countOrdersData.map(function(item) {
  return item.week;
});

var ordersCount = countOrdersData.map(function(item) {
  return item.courseCount;
});

var spark1 = {
  chart: {
    id: 'sparkline1',
    type: 'line',
    height: 140,
    sparkline: {
      enabled: true
    },
    group: 'sparklines'
  },
  series: [{
    name: 'Количество заказов',
    data: ordersCount
  }],
  xaxis: {
    categories: weeks
  },
  stroke: {
    curve: 'smooth'
  },
  markers: {
    size: 0
  },
  tooltip: {
    fixed: {
      enabled: true,
      position: 'right'
    },
    x: {
      show: true,
      formatter: function(val) {
        return 'Неделя: ' + val;
      }
    }
  },
  title: {
    text: 'Заказы в месяц',
    style: {
      fontSize: '20px'
    }
  },
  colors: ['#734CEA']
};
// ВТОРОЙ ГРАФИК
var salaryMonths = [];
var totalSalaries = [];

// Проход по всем элементам в массиве totalSalary и добавление месяцев и общих зарплат в соответствующие массивы
totalSalary.forEach(function(item) {
  salaryMonths.push(item.month); // Добавление месяца в массив месяцев
  totalSalaries.push(parseFloat(item.totalSalary));
});

var spark2 = {
  chart: {
    id: 'sparkline2',
    type: 'line',
    height: 140,
    sparkline: {
      enabled: true
    },
    group: 'sparklines'
  },
  series: [{
    name: 'Общая зарплата (руб)',
    data: totalSalaries
  }],
  xaxis: {
    categories: salaryMonths
  },
  stroke: {
    curve: 'smooth'
  },
  markers: {
    size: 0
  },
  tooltip: {
    fixed: {
      enabled: true,
      position: 'right'
    },
    x: {
      show: true,
      formatter: function(val) {
        return 'Месяц: ' + val;
      }
    }
  },
  title: {
    text: 'Зарплаты в год',
    style: {
      fontSize: '20px'
    }
  },
  colors: ['#34bfa3']
};

// ТРЕТИЙ ГРАФИК
var salesMonths = [];
var salesInMonth = [];

// Проход по всем элементам в массиве данных о продажах и добавление месяцев и продаж в соответствующие массивы
totalSales.forEach(function(item) {
  salesMonths.push(item.month); // Добавление месяца в массив месяцев
  salesInMonth.push(parseFloat(item.sales));
});

var spark3 = {
  chart: {
    id: 'sparkline3',
    type: 'line',
    height: 140,
    sparkline: {
      enabled: true
    },
    group: 'sparklines'
  },
  series: [{
    name: 'Продажи (руб)',
    data: salesInMonth
  }],
  xaxis: {
    categories: salesMonths
  },
  stroke: {
    curve: 'smooth'
  },
  markers: {
    size: 0
  },
  tooltip: {
    fixed: {
      enabled: true,
      position: 'right'
    },
    x: {
      show: true,
      formatter: function(val) {
        return 'Месяц: ' + val;
      }
    }
  },
  title: {
    text: 'Продажи за год',
    style: {
      fontSize: '20px'
    }
  },
  colors: ['#f4516c']
};

// ЧЕТВЕРТЫЙ ГРАФИК
var revenueMonths = [];
var revenueValues = [];

// Проход по всем элементам в массиве данных о доходах и добавление месяцев и доходов в соответствующие массивы
revenueSpark.forEach(function(item) {
  revenueMonths.push(item.month); // Добавление месяца в массив месяцев
  revenueValues.push(parseFloat(item.revenue)); // Добавление дохода в массив доходов
});

var spark4 = {
  chart: {
    id: 'sparkline4',
    type: 'line',
    height: 140,
    sparkline: {
      enabled: true
    },
    group: 'sparklines'
  },
  series: [{
    name: 'Сумма (руб)',
    data: revenueValues
  }],
  xaxis: {
    categories: revenueMonths
  },
  stroke: {
    curve: 'smooth'
  },
  markers: {
    size: 0
  },
  tooltip: {
    fixed: {
      enabled: true,
      position: 'right'
    },
    x: {
      show: true,
      formatter: function(val) {
        return 'Месяц: ' + val;
      }
    }
  },
  title: {
    text: 'Оборот',
    style: {
      fontSize: '20px'
    }
  },
  colors: ['#00c5dc']
};

new ApexCharts(document.querySelector("#spark1"), spark1).render();
new ApexCharts(document.querySelector("#spark2"), spark2).render();
new ApexCharts(document.querySelector("#spark3"), spark3).render();
new ApexCharts(document.querySelector("#spark4"), spark4).render();