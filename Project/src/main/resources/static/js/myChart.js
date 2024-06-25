const ctx = document.getElementById("myChart");
let myChart;
let myChart2

function visualisationOfTaskTime(estTime, realTime){

    const percentageDifference = Number(((Math.abs(estTime - realTime) / estTime) * 100).toFixed(2));
    const absDiff = estTime-realTime;
    const numType = absDiff === 0 ? 0 : (absDiff < 0 ? 1 : 2)

    myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Geschätzte Zeit', 'Reale Zeit'],

            datasets: [
                {
                    label: 'Zeit in Stunden',
                    data: [estTime,realTime],
                    backgroundColor: 'rgba(141,110,255, 0.8)',
                    borderColor: 'rgba(141,110,255, 0.8)',
                    borderWidth: 1
                }
            ]
        },
        options: {
            maintainAspectRatio: false,
            indexAxis: 'y',
            elements: {
                bar: {
                    barThickness: 30,
                    maxBarThickness: 30
                }
            },
            layout: {
                padding: {
                    bottom: 10
                }
            },
            responsive: true,
            plugins: {
                legend: {
                    position: 'bottom',
                    labels: {
                        font: {
                            size: 15,
                        },
                        color: "#b6b6b6"
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
                        size: 21,
                    },
                }
            },
            scales: {
                x: {
                    grid: {
                        color: '#b6b6b6'
                    },
                    ticks: {
                        color: '#b6b6b6',
                        font: {
                            size: 13
                        }
                    },
                },
                y: {
                    grid: {
                        color: '#b6b6b6'
                    },
                    ticks: {
                        color: '#cccccc',
                        font: {
                            size: 13
                        }
                    },
                }
            }
        }
    });
    toggleVis(Math.abs(percentageDifference), Math.abs(absDiff), numType);
}

function visualisationOfAllTime(estTime, realTime, names) {
    const colors = generateColors(names.length);
    const percentageDifference = Number(((Math.abs(estTime.reduce((a, b) => a + b) - realTime.reduce((a, b) => a + b)) / estTime.reduce((a, b) => a + b)) * 100).toFixed(2));
    const absDiff = estTime.reduce((a, b) => a + b) - realTime.reduce((a, b) => a + b);
    const numType = absDiff === 0 ? 0 : (absDiff < 0 ? 1 : 2);

    myChart2 = new Chart('myChart2', {
        type: 'bar',
        data: {
            labels: ['Geschätzte Zeit', 'Reale Zeit'],
            datasets: names.map((name, index) => ({
                label: name,
                data: [estTime[index], realTime[index]],
                backgroundColor: [
                    colors[index], // EstTime color
                ],
                borderWidth: 1
            }))
        },
        options: {
            maintainAspectRatio: false,
            indexAxis: 'y',
            elements: {
                bar: {
                    barThickness: 30,
                    maxBarThickness: 30
                }
            },
            layout: {
                padding: {
                    bottom: 10
                }
            },
            responsive: true,
            plugins: {
                legend: {
                    position: 'bottom',
                    labels: {
                        font: {
                            size: 15,
                        },
                        color: "#b6b6b6"
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
                        size: 21,
                    },
                }
            },
            scales: {
                x: {
                    grid: {
                        color: '#b6b6b6'
                    },
                    ticks: {
                        color: '#b6b6b6',
                        font: {
                            size: 13
                        }
                    },
                    stacked: true,
                },
                y: {
                    grid: {
                        color: '#b6b6b6'
                    },
                    ticks: {
                        color: '#cccccc',
                        font: {
                            size: 13
                        }
                    },
                    stacked: true,
                }
            }
        }
    });
    toggleVisAllTasks(Math.abs(percentageDifference), Math.abs(absDiff), numType);
}

function generateColors(numColors) {
    const colors = [];
    for (let i = 0; i < numColors; i++) {
        const hue = i * (360 / numColors);
        colors.push(`hsl(${hue}, 50%, 50%)`);
    }
    return colors;
}

function destroyMyChart(){
    myChart.destroy()
}

function destroyMyChart2(){
    myChart2.destroy()
}