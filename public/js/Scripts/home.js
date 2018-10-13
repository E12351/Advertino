var home = {
    loadDateRange:function () {
        // alert('test');
        $('input[name="daterange"]').daterangepicker({
            timePicker: false,
            timePickerIncrement: 30,
            locale: {
                // format: 'MM/DD/YYYY'
                format: 'YYYY-MM-DD'
            },
            ranges: {
                'Today': [moment(), moment()],
                'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                'This Month': [moment().startOf('month'), moment().endOf('month')],
                'MTD': [moment().startOf('month'), moment()],
                'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            }
        });
    },

    loadSingleDate:function () {
        // alert('test');
        $('input[name="singledate"]').daterangepicker({
            timePicker: false,
            // timePickerIncrement: 30,
            singleDatePicker: true,
            locale: {
                // format: 'MM/DD/YYYY'
                format: 'YYYY-MM-DD'
            },
            // ranges: {
            //     'Today': [moment(), moment()],
            //     'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            //     'Last 7 Days': [moment().subtract(6, 'days'), moment()],
            //     'Last 30 Days': [moment().subtract(29, 'days'), moment()],
            //     'This Month': [moment().startOf('month'), moment().endOf('month')],
            //     'MTD': [moment().startOf('month'), moment()],
            //     'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            // }
        });
    },

    switchDateRange:function() {
        console.log('switchDateRange');
        $('.date-type-area input').on('change', function() {
            if($("input[name='rbDateType']:checked").val() === 'SINGLE_DATE') {
                $('.filter-box-area .single-date').show();
                $('.filter-box-area .date-range').hide();

                $('.grpah-header').text("HOURLY READING OF A DEVICE IN SPECIFIC DATE");
            }
            else {
                $('.filter-box-area .single-date').hide();
                $('.filter-box-area .date-range').show();


                $('.grpah-header').text("HOURLY READING OF A DEVICE IN SPECIFIC DATE RANGE");
            }
        });
    },

    init:function () {
        home.loadDateRange();
        home.loadSingleDate();
        home.switchDateRange();
    }
}

$(document).ready(function () {

    home.init();


    // $(window).scroll(function() {
    //     if ($('input[name="daterange"]').length) {
    //         $('input[name="daterange"]').daterangepicker("close");
    //     }
    // });





    // $('#date1').datepicker({
    //     changeMonth: true,
    //     changeYear: true,
    //     showButtonPanel: true,
    //     dateFormat: "m-d-yy"
    // });




    $('#showDetail').click(function()
    {
        $("#detailViewContent").toggle();
    });



    $('.main-content').height($(window).height() - 108);


    // $('.side-menu').height($('body').height() - 70);
    // $('.side-menu-bg').height($('body').height() - 70);
    // $('.zzzzzzzzzzzzzzzzzzzzzzzzz').height($('body').height() - 70);

    var tree = [
        {
            text: "Dialog - Head Office",
            id:2,
            nodes: [
                {
                    text: "Main Bulding",
                    id:1,
                    nodes: [
                        {
                            text: "Floor 7",
                            id:17
                        }
                        // ,
                        // {
                        //     text: "Floor 8",
                        //     id:18
                        // }
                        // ,
                        // {
                        //     text: "Floor 9",
                        //     id:19
                        // },
                        // {
                        //     text: "Floor 10",
                        //     id:110
                        // },
                        // {
                        //     text: "Floor 11",
                        //     id:111
                        // },
                        // {
                        //     text: "Floor 12",
                        //     id:112
                        // },
                        // {
                        //     text: "Floor 13",
                        //     id:113
                        // }
                    ]
                }
                // ,
                // {
                //     text: "Kit Building",
                //     id:2,
                //     nodes: [
                //         {
                //             text: "Floor 8",
                //             id:28
                //         }
                //     ]
                // }
            ]
        }
    ];

    function getTree() {
        // Some logic to retrieve, or generate tree structure
        return tree;
    }


    var $searchableTree = $('#treeview-searchable').treeview({
        data: getTree()
    });

    $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');


    var search = function (e) {
        var pattern = $('#input-search').val();
        var options = {
            ignoreCase: $('#chk-ignore-case').is(':checked'),
            exactMatch: $('#chk-exact-match').is(':checked'),
            revealResults: $('#chk-reveal-results').is(':checked')
        };
        var results = $searchableTree.treeview('search', [pattern, options]);

        var output = '<p>' + results.length + ' matches found</p>';
        $.each(results, function (index, result) {
            output += '<p>- ' + result.text + '</p>';
        });
        $('#search-output').html(output);
    };

    $('#btn-search').on('click', search);
    $('#input-search').on('keyup', search);

    $('#btn-clear-search').on('click', function (e) {
        $searchableTree.treeview('clearSearch');
        $('#input-search').val('');
        $('#search-output').html('');
    });


    var timerDiv = document.getElementById('timerValue'),
        start = document.getElementById('start'),
        stop = document.getElementById('stop'),
        reset = document.getElementById('reset'),
        t;

// Get time from cookie
    var cookieTime = getCookie('time');

// If timer value is saved in the cookie
    if (cookieTime != null && cookieTime != '00:00:00') {
        var savedCookie = cookieTime;
        var initialSegments = savedCookie.split('|');
        var savedTimer = initialSegments[0];
        var timerSegments = savedTimer.split(':');
        var seconds = parseInt(timerSegments[2]),
            minutes = parseInt(timerSegments[1]),
            hours = parseInt(timerSegments[0]);
        timer();
        document.getElementById('timerValue').textContent = savedTimer;
        $('#stop').removeAttr('disabled');
        $('#reset').removeAttr('disabled');
    } else {
        var seconds = 0, minutes = 0, hours = 0;
        if (timerDiv != null) {
            timerDiv.textContent = "00:00:00";
        }
    }

// New Date object for the expire time
    var curdate = new Date();
    var exp = new Date();

// Set the expire time
    exp.setTime(exp + 2592000000);

    function add() {

        seconds++;
        if (seconds >= 60) {
            seconds = 0;
            minutes++;
            if (minutes >= 60) {
                minutes = 0;
                hours++;
            }
        }

        timerDiv.textContent = (hours ? (hours > 9 ? hours : "0" + hours) : "00")
            + ":" + (minutes ? (minutes > 9 ? minutes : "0" + minutes) : "00")
            + ":" + (seconds > 9 ? seconds : "0" + seconds);

        // Set a 'time' cookie with the current timer time and expire time object.
        var timerTime = timerDiv.textContent.replace("%3A", ":");
        // console.log('timerTime', timerTime);
        setCookie('time', timerTime + '|' + curdate, exp);

        timer();
    }

    function timer() {
        t = setTimeout(add, 1000);
    }

// timer(); // autostart timer

    /* Start button */
    if (start != null) {
        start.onclick = timer;

    }

    /* Stop button */
    if (stop != null) {
        stop.onclick = function () {
            clearTimeout(t);
        };
    }


    /* Clear button */
    if (reset != null) {
        reset.onclick = function () {
            timerDiv.textContent = "00:00:00";
            seconds = 0;
            minutes = 0;
            hours = 0;
            setCookie('time', "00:00:00", exp);
        }
    }

    function setCookie(name, value, expires) {
        document.cookie = name + "=" + value + "; path=/" + ((expires == null) ? "" : "; expires=" + expires.toGMTString());
    }

    function getCookie(name) {
        var cname = name + "=";
        var dc = document.cookie;

        if (dc.length > 0) {
            var begin = dc.indexOf(cname);
            if (begin != -1) {
                begin += cname.length;
                var end = dc.indexOf(";", begin);
                if (end == -1) end = dc.length;
                return unescape(dc.substring(begin, end));
            }
        }
        return null;
    }

    $('.main-content').perfectScrollbar();

    setActiveTab();

    function setActiveTab() {
        $('.detailview-menu-element').click(function () {
            $('.detailview-menu-element').removeClass('active');
            $(this).addClass('active');
        })
    }
});


var detailviewData = {
    temp: [],
    co2: [],
    humid: []
};


var detailview = Highcharts.chart('container', {
    colors: ['#109d73', '#90ee7e', '#f45b5b', '#7798BF', '#aaeeee', '#ff0066',
        '#eeaaee', '#55BF3B', '#DF5353', '#7798BF', '#aaeeee'],
    chart: {
        type: 'areaspline',
        backgroundColor: 'rgba(160, 160, 160, 0)'
    },
    exporting: { enabled: false },
    title: {
        text: null,
        style:{
            fontSize:"12px"
        }
    },
    legend: {
        enabled:false
    },
    xAxis: {

        labels: {
            style: {
                fontSize : '8px'
            }
        }
    },
    yAxis: {
        title: {
            text: ''
        }
    },
    tooltip: {
        shared: true,
        valueSuffix: ' units'
    },
    credits: {
        enabled: false
    },
    plotOptions: {
        areaspline: {
            fillOpacity: 0.5
        },
        series: {
            showInLegend: false,
            states: {
                hover: {
                    enabled: true,
                    lineWidth: 2
                }
            },
            lineWidth: 0,
            marker: {
                enabled: false
            }
        }
    },
    series: [{
        name: null
    }]
});

var activeElement = {
    user: 1,
    entity: '',
    name: '',
    devices: [],
    widgets: []
};


function setActiveObj(nodeId, text, user) {
    activeElement.entity = nodeId;
    activeElement.text = text;
    activeElement.user = user;

}

function getActiveObj() {
    return activeElement;
}

function hideContent() {
    var x = document.getElementById("dashboard_back");
    if (x != null) {
        x.style.display = "none";
    }
}

setInterval(function () {
    // console.log('setInterval enviroment called.');

}, 5000);








