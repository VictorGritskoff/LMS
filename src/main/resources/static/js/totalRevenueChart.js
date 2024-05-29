// Создаем массив уникальных дат
let allDates = [...new Set([...expenses.map(e => e.time), ...incomes.map(i => i.time)])];
allDates.sort((a, b) => new Date(a) - new Date(b));

// Создаем объекты для каждой даты с нулевыми значениями прибыли и убытков
let dataMap = {};
allDates.forEach(date => {
  dataMap[date] = { expenses: 0, incomes: 0 };
});

// Заполняем данные для расходов
expenses.forEach(e => {
  dataMap[e.time].expenses = e.totalSalary;
});

// Заполняем данные для доходов
incomes.forEach(i => {
  dataMap[i.time].incomes = i.sales;
});

// Заполняем пропущенные значения расходов предыдущими значениями
let previousExpenses = 0;
let previousIncomes = 0;

for (let date of allDates) {
  if (dataMap[date].expenses === 0) {
    dataMap[date].expenses = previousExpenses;
  } else {
    previousExpenses = dataMap[date].expenses;
  }

  if (dataMap[date].incomes === 0) {
    dataMap[date].incomes = previousIncomes;
  } else {
    previousIncomes = dataMap[date].incomes;
  }
}

// Преобразуем данные в формат, подходящий для графика
let categories = Object.keys(dataMap);
let seriesExpenses = Object.values(dataMap).map(d => d.expenses);
let seriesIncomes = Object.values(dataMap).map(d => d.incomes);

// Обновляем опции графика
var options = {
  series: [{
    name: 'Убытки',
    data: seriesExpenses
  }, {
    name: 'Прибыль',
    data: seriesIncomes
  }],
  chart: {
    height: 350,
    type: 'area'
  },
  dataLabels: {
    enabled: false
  },
  stroke: {
    curve: 'smooth'
  },
  xaxis: {
    type: 'datetime',
    categories: categories
  },
  tooltip: {
    x: {
      format: 'dd/MM/yy HH:mm'
    },
  },
};

var chart = new ApexCharts(document.querySelector("#total-revenue-chart"), options);
chart.render();