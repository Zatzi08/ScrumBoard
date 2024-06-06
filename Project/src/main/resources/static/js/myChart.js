const ctx = document.getElementById("myChart");
let myChart;

function visualisationOfTaskTime(estTime, realTime){

    const percentageDifference = ((Math.abs(estTime - realTime) / estTime) * 100).toFixed(2);
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
                        fontSize: 12
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
    toggleVis(percentageDifference);
}

function destroy(){
    myChart.destroy();
    //myChart = null;
}

