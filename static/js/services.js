'use strict';

/* Services */

angular.module('monocycleApp.services', [])
    .value('version', '0.1.0-SNAPSHOT')
    .value('charting', {
        chartOptions: {
            title: 'CPU',
            highlighter: {
                show: true,
                sizeAdjust: 1,
                tooltipOffset: 9
            },
            axes: {
                xaxis: {
                    renderer: $.jqplot.DateAxisRenderer,
                    tickOptions: {
                        pad: 0,
                        formatString: "%H:%M",
                        angle: -30,
                        textColor: '#dddddd'
                    }
                },
                yaxis: {
                    pad: 0,
                    rendererOptions: {
                        min: 0,
                        max: 100,
                        minorTicks: 1
                    },
                    ticks: [0, 50, 100],
                    tickOptions: {
                        formatString: "%'d\%",
                        showMark: false
                    }
                }
            },
            series: [
                {
                    fill: true,
                    label: 'CPU'
                }
            ],
            legend: {
                show: true,
                location: 'e'
            }
        }
    })
