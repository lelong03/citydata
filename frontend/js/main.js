document.addEventListener('DOMContentLoaded', function() {
    const baseUrlInput = document.getElementById('baseUrl');

    // Chart for average consumption by source
    const timeRangeSourceSelect = document.getElementById('timeRangeSource');
    const ctxSource = document.getElementById('sourceChart').getContext('2d');
    let sourceChart = new Chart(ctxSource, {
        type: 'bar',
        data: {
            labels: [], // Water sources: e.g., River, Reservoir, Groundwater
            datasets: [{
                label: 'Avg Consumption (liters/household/day)',
                data: [],
                backgroundColor: [
                    'rgba(54, 162, 235, 0.5)',
                    'rgba(75, 192, 192, 0.5)',
                    'rgba(255, 206, 86, 0.5)'
                ],
                borderColor: [
                    'rgba(54, 162, 235, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(255, 206, 86, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    // Chart for daily average consumption
    const timeRangeDaySelect = document.getElementById('timeRangeDay');
    const ctxDaily = document.getElementById('dailyChart').getContext('2d');
    let dailyChart = new Chart(ctxDaily, {
        type: 'line',
        data: {
            labels: [], // Dates (days)
            datasets: [{
                label: 'Daily Avg Consumption (liters/household/day)',
                data: [],
                backgroundColor: 'rgba(153, 102, 255, 0.2)',
                borderColor: 'rgba(153, 102, 255, 1)',
                borderWidth: 2,
                fill: true,
                tension: 0.1
            }]
        },
        options: {
            scales: {
                x: {
                    title: {
                        display: true,
                        text: 'Date'
                    }
                },
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Consumption (liters/day)'
                    }
                }
            }
        }
    });

    // For Daily Water Quality Chart:
    const timeRangeQualitySelect = document.getElementById('timeRangeQuality');
    const ctxQuality = document.getElementById('qualityChart').getContext('2d');
    let qualityChart = new Chart(ctxQuality, {
        type: 'line',
        data: {
            labels: [], // Dates (e.g., "2025-02-01")
            datasets: [
                {
                    label: 'Avg pH',
                    data: [],
                    borderColor: 'rgba(50,122,170,0.6)',
                    backgroundColor: 'rgba(50,122,170,0.6)',
                    fill: false,
                    tension: 0.1,
                    yAxisID: 'y1'
                },
                {
                    label: 'Avg Turbidity',
                    data: [],
                    borderColor: 'rgba(255, 99, 132, 0.6)',
                    backgroundColor: 'rgba(255, 99, 132, 0.6)',
                    fill: false,
                    tension: 0.1,
                    yAxisID: 'y2'
                }
            ]
        },
        options: {
            scales: {
                x: {
                    title: {
                        display: true,
                        text: 'Date'
                    }
                },
                y1: {
                    type: 'linear',
                    position: 'left',
                    beginAtZero: false, // pH is not expected to start at zero
                    title: {
                        display: true,
                        text: 'pH'
                    }
                },
                y2: {
                    type: 'linear',
                    position: 'right',
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Turbidity (NTU)'
                    },
                    grid: {
                        drawOnChartArea: false // Avoid duplicate grid lines
                    }
                }
            }
        }
    });

    // Initialize stacked bar chart for daily water loss rate
    const timeRangeSelect = document.getElementById('timeRangeLoss');
    const ctxLoss = document.getElementById('lossChart').getContext('2d');
    let lossChart = new Chart(ctxLoss, {
        type: 'bar',
        data: {
            labels: [], // Dates (e.g., "2025-02-01")
            datasets: [
                {
                    label: 'Non-Lost Consumption',
                    data: [],
                    backgroundColor: 'rgba(75, 192, 192, 0.6)',
                    stack: 'Stack 0'
                },
                {
                    label: 'Lost Consumption',
                    data: [],
                    backgroundColor: 'rgba(255, 99, 132, 0.6)',
                    stack: 'Stack 0'
                }
            ]
        },
        options: {
            plugins: {
                tooltip: {
                    mode: 'index',
                    intersect: false
                }
            },
            responsive: true,
            scales: {
                x: {
                    stacked: true,
                    title: {
                        display: true,
                        text: 'Date'
                    }
                },
                y: {
                    stacked: true,
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Consumption (liters)'
                    }
                }
            }
        }
    });

    // For Electricity Consumption Metric:
    const elecStartDateInput = document.getElementById('elecStartDate');
    const elecEndDateInput = document.getElementById('elecEndDate');
    const fetchElecBtn = document.getElementById('fetchElecBtn');
    const ctxElectricity = document.getElementById('electricityChart').getContext('2d');

    // Initialize the bar chart for electricity consumption
    let elecChart = new Chart(ctxElectricity, {
        type: 'bar',
        data: {
            labels: [], // District names
            datasets: [{
                label: 'Avg Electricity Consumption (kWh)',
                data: [],
                backgroundColor: 'rgba(54, 162, 235, 0.6)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                x: {
                    title: {
                        display: true,
                        text: 'District'
                    }
                },
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Consumption (kWh)'
                    }
                }
            }
        }
    });

    // Function to fetch data for the average consumption by source chart
    function fetchSourceData() {
        const range = timeRangeSourceSelect.value;
        const url = `http://localhost:8080/api/metrics/avg_consumption_by_source?range=${encodeURIComponent(range)}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                const labels = data.map(item => item.source);
                const consumptionData = data.map(item => item.avgConsumption);

                sourceChart.data.labels = labels;
                sourceChart.data.datasets[0].data = consumptionData;
                sourceChart.update();
            })
            .catch(error => console.error('Error fetching source metric:', error));
    }

    // Function to fetch data for the daily average consumption chart
    function fetchDailyData() {
        const range = timeRangeDaySelect.value;
        const url = `http://localhost:8080/api/metrics/avg_consumption_by_day?range=${encodeURIComponent(range)}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                const labels = data.map(item => item.day);
                const consumptionData = data.map(item => item.avgConsumption);

                dailyChart.data.labels = labels;
                dailyChart.data.datasets[0].data = consumptionData;
                dailyChart.update();
            })
            .catch(error => console.error('Error fetching daily metric:', error));
    }

    // Function to fetch daily water quality data from the backend
    // Function to fetch daily water quality data from the backend API
    function fetchQualityData() {
        const range = timeRangeQualitySelect.value;
        const url = `http://localhost:8080/api/metrics/daily_water_quality?range=${encodeURIComponent(range)}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                // Expected data format: Array of objects { day, avgPh, avgTurbidity }
                const labels = data.map(item => item.day);
                const phData = data.map(item => item.avgPh);
                const turbidityData = data.map(item => item.avgTurbidity);

                // Update chart data and refresh the chart
                qualityChart.data.labels = labels;
                qualityChart.data.datasets[0].data = phData;
                qualityChart.data.datasets[1].data = turbidityData;
                qualityChart.update();
            })
            .catch(error => console.error('Error fetching daily water quality data:', error));
    }

    // Function to fetch daily water loss data from backend
    function fetchLossData() {
        // Get base URL from text box and ensure it has no trailing slash
        let baseUrl = baseUrlInput.value.trim();
        if (baseUrl.endsWith('/')) {
            baseUrl = baseUrl.slice(0, -1);
        }
        const range = timeRangeSelect.value;
        // Build full URL for the API endpoint
        const url = `${baseUrl}/api/metrics/daily_water_loss_rate?range=${encodeURIComponent(range)}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                // Data format: array of objects { day, totalConsumption, lostConsumption, lossRate }
                const labels = data.map(item => item.day);
                const lostData = data.map(item => item.lostConsumption);
                const totalData = data.map(item => item.totalConsumption);
                // Calculate non-lost consumption = totalConsumption - lostConsumption
                const nonLostData = data.map(item => item.totalConsumption - item.lostConsumption);

                // Update chart data
                lossChart.data.labels = labels;
                lossChart.data.datasets[0].data = nonLostData;
                lossChart.data.datasets[1].data = lostData;
                lossChart.update();
            })
            .catch(error => console.error('Error fetching water loss data:', error));
    }

    // Function to fetch electricity consumption data from the backend
    function fetchElectricityData() {
        let baseUrl = baseUrlInput.value.trim();
        if (baseUrl.endsWith('/')) {
            baseUrl = baseUrl.slice(0, -1);
        }
        const startDate = elecStartDateInput.value;
        const endDate = elecEndDateInput.value; // If empty, the backend defaults to today

        const url = `${baseUrl}/api/metrics/avg_electricity_consumption?startDate=${encodeURIComponent(startDate)}&endDate=${encodeURIComponent(endDate)}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                // Data is expected to be an array of objects: { district, avgConsumption }
                const labels = data.map(item => item.district);
                const consumptionData = data.map(item => item.avgConsumption);

                elecChart.data.labels = labels;
                elecChart.data.datasets[0].data = consumptionData;
                elecChart.update();
            })
            .catch(error => console.error('Error fetching electricity consumption data:', error));
    }
    // Set the end-date default value to today's date if not provided
    if (!elecEndDateInput.value) {
        const today = new Date().toISOString().split('T')[0];
        elecEndDateInput.value = today;
    }

    // Fetch data initially
    fetchSourceData();
    fetchDailyData();
    fetchQualityData();
    fetchLossData();
    fetchElectricityData();

    // Add event listeners for each dropdown filter
    timeRangeSourceSelect.addEventListener('change', fetchSourceData);
    timeRangeDaySelect.addEventListener('change', fetchDailyData);
    timeRangeQualitySelect.addEventListener('change', fetchQualityData);
    timeRangeSelect.addEventListener('change', fetchLossData);
    // Fetch data when the button is clicked
    fetchElecBtn.addEventListener('click', fetchElectricityData);

});
