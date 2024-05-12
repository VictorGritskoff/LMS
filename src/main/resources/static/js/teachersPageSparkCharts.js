window.Apex = {
    dataLabels: {
      enabled: false
    }
  };
  
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
      name: 'Amount $',
      data: [25, 66, 41, 59, 25, 44, 12, 36, 9, 21, 10, 43]
    }],
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
          return 'Month: ' + val;
        }
      }
    },
    title: {
      text: '$439   Sales Last Month',
      style: {
        fontSize: '20px'
      }
    },
    colors: ['#734CEA']
  }
  
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
      name: 'Amount $',
      data: [12, 14, 2, 47, 32, 44, 14, 55, 41, 69, 35, 40]
    }],
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
          return 'Month: ' + val;
        }
      }
    },
    title: {
      text: '$387    Wage Costs',
      style: {
        fontSize: '20px'
      }
    },
    colors: ['#34bfa3']
  }
  
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
      name: 'Amount $',
      data: [47, 45, 74, 32, 56, 31, 44, 33, 45, 19, 43, 20]
    }],
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
          return 'Month: ' + val;
        }
      }
    },
    colors: ['#f4516c'],
    title: {
      text: '$577    Last Year Sales',
      style: {
        fontSize: '20px'
      }
    },
    xaxis: {
      crosshairs: {
        width: 1
      },
    }
  }
  
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
      name: 'Amount $',
      data: [15, 75, 47, 65, 14, 32, 19, 54, 44, 61, 20, 35]
    }],
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
          return 'Month: ' + val;
        }
      }
    },
    colors: ['#00c5dc'],
    title: {
      text: '$615      Total Revenue',
      style: {
        fontSize: '20px'
      }
    },
    xaxis: {
      crosshairs: {
        width: 1
      },
    }
  }
  
  new ApexCharts(document.querySelector("#spark1"), spark1).render();
  new ApexCharts(document.querySelector("#spark2"), spark2).render();
  new ApexCharts(document.querySelector("#spark3"), spark3).render();
  new ApexCharts(document.querySelector("#spark4"), spark4).render();