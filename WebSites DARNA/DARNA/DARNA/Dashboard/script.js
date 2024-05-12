const allSideMenu = document.querySelectorAll('#sidebar .side-menu.top li a');

allSideMenu.forEach(item=> {
	const li = item.parentElement;

	item.addEventListener('click', function () {
		allSideMenu.forEach(i=> {
			i.parentElement.classList.remove('active');
		})
		li.classList.add('active');
	})
});
// TOGGLE SIDEBAR
const menuBar = document.querySelector('#content nav .bx.bx-menu');
const sidebar = document.getElementById('sidebar');

menuBar.addEventListener('click', function () {
	sidebar.classList.toggle('hide');
})

// BAR CHART
const barChartOptions = {
	series: [
	  {
		data: [10, 8, 6, 4, 2],
		name: 'Products',
	  },
	],
	chart: {
	  type: 'bar',
	  background: 'transparent',
	  height: 350,
	  toolbar: {
		show: false,
	  },
	},
	colors: ['#2962ff', '#d50000', '#2e7d32', '#ff6d00', '#583cb3'],
	plotOptions: {
	  bar: {
		distributed: true,
		borderRadius: 4,
		horizontal: false,
		columnWidth: '40%',
	  },
	},
	dataLabels: {
	  enabled: false,
	},
	fill: {
	  opacity: 1,
	},
	grid: {
	  borderColor: '#55596e',
	  yaxis: {
		lines: {
		  show: true,
		},
	  },
	  xaxis: {
		lines: {
		  show: true,
		},
	  },
	},
	legend: {
	  labels: {
		colors: '#f5f7ff',
	  },
	  show: true,
	  position: 'top',
	},
	stroke: {
	  colors: ['transparent'],
	  show: true,
	  width: 2,
	},
	tooltip: {
	  shared: true,
	  intersect: false,
	  theme: 'dark',
	},
	xaxis: {
	  categories: ['Laptop', 'Phone', 'Monitor', 'Headphones', 'Camera'],
	  title: {
		style: {
		  color: '#f5f7ff',
		},
	  },
	  axisBorder: {
		show: true,
		color: '#55596e',
	  },
	  axisTicks: {
		show: true,
		color: '#55596e',
	  },
	  labels: {
		style: {
		  colors: '#f5f7ff',
		},
	  },
	},
	yaxis: {
	  title: {
		text: 'Count',
		style: {
		  color: '#f5f7ff',
		},
	  },
	  axisBorder: {
		color: '#55596e',
		show: true,
	  },
	  axisTicks: {
		color: '#55596e',
		show: true,
	  },
	  labels: {
		style: {
		  colors: '#f5f7ff',
		},
	  },
	},
  };
  
  const barChart = new ApexCharts(
	document.querySelector('#bar-chart'),
	barChartOptions
  );
  barChart.render();
  
  // AREA CHART
  const areaChartOptions = {
	series: [
	  {
		name: 'Completed Services',
		data: [11, 32, 28, 32, 34, 52, 67, 55, 60, 70, 85, 65],
	  },
	  {
		name: 'Offers',
		data: [31, 40, 45, 54, 42, 109, 100, 57, 63, 74, 88, 65],
	  },
	],
	chart: {
	  type: 'area',
	  background: 'transparent',
	  height: 350,
	  stacked: false,
	  toolbar: {
		show: false,
	  },
	},
	colors: ['#BDE5FB', '#3CB6FC'],
	labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul','Aug','Sep','Oct','Nov','Dec'],
	dataLabels: {
	  enabled: false,
	},
	fill: {
	  gradient: {
		opacityFrom: 0.4,
		opacityTo: 0.1,
		shadeIntensity: 1,
		stops: [0, 100],
		type: 'vertical',
	  },
	  type: 'gradient',
	},
	grid: {
	  borderColor: '#55596e',
	  yaxis: {
		lines: {
		  show: true,
		},
	  },
	  xaxis: {
		lines: {
		  show: true,
		},
	  },
	},
	legend: {
	  labels: {
		colors: '#f5f7ff',
	  },
	  show: true,
	  position: 'top',
	},
	markers: {
	  size: 6,
	  strokeColors: '#1b2635',
	  strokeWidth: 3,
	},
	stroke: {
	  curve: 'smooth',
	},
	xaxis: {
	  axisBorder: {
		color: '#55596e',
		show: true,
	  },
	  axisTicks: {
		color: '#55596e',
		show: true,
	  },
	  labels: {
		offsetY: 5,
		style: {
		  colors: '#f5f7ff',
		},
	  },
	},
	
	tooltip: {
	  shared: true,
	  intersect: false,
	  theme: 'dark',
	},
  };
  
  const areaChart = new ApexCharts(
	document.querySelector('#area-chart'),
	areaChartOptions
  );
  areaChart.render();





const searchButton = document.querySelector('#content nav form .form-input button');
const searchButtonIcon = document.querySelector('#content nav form .form-input button .bx');
const searchForm = document.querySelector('#content nav form');

searchButton.addEventListener('click', function (e) {
	if(window.innerWidth < 576) {
		e.preventDefault();
		searchForm.classList.toggle('show');
		if(searchForm.classList.contains('show')) {
			searchButtonIcon.classList.replace('bx-search', 'bx-x');
		} else {
			searchButtonIcon.classList.replace('bx-x', 'bx-search');
		}
	}
})





if(window.innerWidth < 768) {
	sidebar.classList.add('hide');
} else if(window.innerWidth > 576) {
	searchButtonIcon.classList.replace('bx-x', 'bx-search');
	searchForm.classList.remove('show');
}


window.addEventListener('resize', function () {
	if(this.innerWidth > 576) {
		searchButtonIcon.classList.replace('bx-x', 'bx-search');
		searchForm.classList.remove('show');
	}
})



const switchMode = document.getElementById('switch-mode');

switchMode.addEventListener('change', function () {
	if(this.checked) {
		document.body.classList.add('dark');
	} else {
		document.body.classList.remove('dark');
	}
})



