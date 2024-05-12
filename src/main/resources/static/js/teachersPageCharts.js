// mainPageChart1.js
document.addEventListener("DOMContentLoaded", function() {
    // Sales Last Month Chart
    var salesLastMonthCtx = document.getElementById('salesLastMonthChart').querySelector('canvas').getContext('2d');
    var salesLastMonthChart = new Chart(salesLastMonthCtx, {
        type: 'line',
        data: {
            labels: ['Week 1', 'Week 2', 'Week 3', 'Week 4'],
            datasets: [{
                label: 'Sales Last Month',
                data: [400, 450, 500, 600], // Sample data, replace with actual data
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgba(255, 99, 132, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        callback: function(value, index, values) {
                            return '$' + value;
                        },
                        fontColor: 'white' // Устанавливаем цвет шрифта белым
                    },
                    gridLines: {
                        color: 'rgba(255, 255, 255, 0)' // Устанавливаем цвет сетки белым
                    }
                }],
                xAxes: [{
                    ticks: {
                        fontColor: 'white' // Устанавливаем цвет шрифта белым
                    },
                    gridLines: {
                        color: 'rgba(255, 255, 255, 0)' // Убираем сетку у оси X
                    }
                }]
            },
            
            legend: {
                display: false,
                backgroundColor: 'transparent'
            },
            elements: {
                line: {
                    tension: 0, // Disables bezier curves
                }
            }
        }
    });

    // Wage Costs Chart
    var wageCostsCtx = document.getElementById('wageCostsChart').querySelector('canvas').getContext('2d');
    var wageCostsChart = new Chart(wageCostsCtx, {
        type: 'line',
        data: {
            labels: ['Week 1', 'Week 2', 'Week 3', 'Week 4'],
            datasets: [{
                label: 'Wage Costs',
                data: [200, 220, 210, 230], // Sample data, replace with actual data
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        callback: function(value, index, values) {
                            return '$' + value;
                        },
                        fontColor: 'white' // Устанавливаем цвет шрифта белым
                    },
                    gridLines: {
                        color: 'rgba(255, 255, 255, 0)' // Устанавливаем цвет сетки белым
                    }
                }],
                xAxes: [{
                    ticks: {
                        fontColor: 'white' // Устанавливаем цвет шрифта белым
                    },
                    gridLines: {
                        color: 'rgba(255, 255, 255, 0)' // Убираем сетку у оси X
                    }
                }]
            },
            legend: {
                display: false,
                backgroundColor: 'transparent'
            },
            elements: {
                line: {
                    tension: 0, // Disables bezier curves
                }
            }
        }
    });

    // Last Year Sales Chart
    var lastYearSalesCtx = document.getElementById('lastYearSalesChart').querySelector('canvas').getContext('2d');
    var lastYearSalesChart = new Chart(lastYearSalesCtx, {
        type: 'line',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
            datasets: [{
                label: 'Last Year Sales',
                data: [1000, 1200, 1100, 1300, 1400, 1600, 1500, 1700, 1800, 2000, 1900, 2100], // Sample data, replace with actual data
                backgroundColor: 'rgba(255, 206, 86, 0.2)',
                borderColor: 'rgba(255, 206, 86, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        callback: function(value, index, values) {
                            return '$' + value;
                        },
                        fontColor: 'white' // Устанавливаем цвет шрифта белым
                    },
                    gridLines: {
                        color: 'rgba(255, 255, 255, 0)' // Устанавливаем цвет сетки белым
                    }
                }],
                xAxes: [{
                    ticks: {
                        fontColor: 'white' // Устанавливаем цвет шрифта белым
                    },
                    gridLines: {
                        color: 'rgba(255, 255, 255, 0)' // Убираем сетку у оси X
                    }
                }]
            },
            elements: {
                line: {
                    tension: 0,
                }
            },
            legend: {
                display: false
            },
        }
    });

    // Total Revenue Chart
    var totalRevenueCtx = document.getElementById('totalRevenueChart').querySelector('canvas').getContext('2d');
    var totalRevenueChart = new Chart(totalRevenueCtx, {
        type: 'line',
        data: {
            labels: ['Week 1', 'Week 2', 'Week 3', 'Week 4'],
            datasets: [{
                label: 'Total Revenue',
                data: [1500, 1600, 1700, 1800], // Sample data, replace with actual data
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        callback: function(value, index, values) {
                            return '$' + value;
                        },
                        fontColor: 'white' // Устанавливаем цвет шрифта белым
                    },
                    gridLines: {
                        color: 'rgba(255, 255, 255, 0)' // Устанавливаем цвет сетки белым
                    }
                }],
                xAxes: [{
                    ticks: {
                        fontColor: 'white' // Устанавливаем цвет шрифта белым
                    },
                    gridLines: {
                        color: 'rgba(255, 255, 255, 0)' // Убираем сетку у оси X
                    }
                }]
            },
            legend: {
                display: false,
                backgroundColor: 'transparent'
            },
            elements: {
                line: {
                    tension: 0, // Disables bezier curves
                }
            }
        }
    });
});
