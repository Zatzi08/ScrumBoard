const ctx = document.getElementById("myChart");
let myChart;
let myChart2

function visualisationOfTaskTime(estTime, realTime){

    const percentageDifference = Number(((Math.abs(estTime - realTime) / estTime) * 100).toFixed(2));
    const absDiff = estTime-realTime;
    const numType = absDiff == 0 ? 0 : (absDiff < 0 ? 1 : 2)

    myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Geschätzte Zeit', 'Reale Zeit'],

            datasets: [
                {
                    label: 'Zeit in Stunden',
                    data: [estTime,realTime],
                    backgroundColor: 'rgb(255, 99, 132)',
                    borderColor: 'rgb(255, 99, 132)',
                    borderWidth: 1
                }
            ]
        },
        options: {
            indexAxis: 'y',

            elements: {
                bar: {
                    barThickness: 30,
                    maxBarThickness: 30
                }
            },

            responsive: true,

            plugins: {
                legend: {
                    position: 'bottom',
                    labels: {
                        fontColor: "#b6b6b6",
                        fontSize: 15
                    }
                },

                title: {
                    display: true,
                    text: 'Geschätzte Zeit vs Reale Zeit',
                    color: '#b6b6b6',
                    padding: {
                        top: 10,
                        bottom: 30
                    },
                    font: {
                        size: 17,
                    },
                }
            },
            scales: {
                x: {
                    grid: {
                        color: '#b6b6b6'
                    },
                    ticks: {
                        color: '#b6b6b6'
                    }
                },
                y: {
                    grid: {
                        color: '#b6b6b6'
                    },
                    ticks: {
                        color: '#cccccc'
                    }
                }
            }
        }
    });
    toggleVis(Math.abs(percentageDifference), Math.abs(absDiff), numType);
}

function visualisationOfAllTime(estTime, realTime, names){
    myChart2 = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Geschätzte Zeit', 'Reale Zeit'],

            datasets: [
                {
                    label: 'Zeit in Stunden',
                    data: estTime,
                    backgroundColor: 'rgb(255, 99, 132)',
                    borderColor: 'rgb(255, 99, 132)',
                    borderWidth: 1
                },
                {
                    label: 'Zeit in Stunden',
                    data: realTime,
                    backgroundColor: 'rgb(255, 99, 132)',
                    borderColor: 'rgb(255, 99, 132)',
                    borderWidth: 1
                }
            ]
        },
        options: {
            indexAxis: 'y',

            elements: {
                bar: {
                    barThickness: 30,
                    maxBarThickness: 30
                }
            },

            responsive: true,

            plugins: {
                legend: {
                    position: 'bottom',
                    labels: {
                        fontColor: "#b6b6b6",
                        fontSize: 15
                    }
                },

                title: {
                    display: true,
                    text: 'Geschätzte Zeit vs Reale Zeit (alle Tasks)',
                    color: '#b6b6b6',
                    padding: {
                        top: 10,
                        bottom: 30
                    },
                    font: {
                        size: 17,
                    },
                }
            },
            scales: {
                x: {
                    grid: {
                        color: '#b6b6b6'
                    },
                    ticks: {
                        color: '#b6b6b6'
                    },
                    stacked: true,
                },
                y: {
                    grid: {
                        color: '#b6b6b6'
                    },
                    ticks: {
                        color: '#cccccc'
                    }
                }
            }
        }
    });
    toggleVisAllTasks();
}

function destroyMyChart(){
    myChart.destroy()
}

function destroyMyChart2(){
    myChart2.destroy()
}