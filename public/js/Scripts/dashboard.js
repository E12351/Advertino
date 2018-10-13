$(document).ready(function () {
    init();
});

function init() {
    setInterval(function () {
        var active = getActiveObj();

        active.devices.forEach(function (entry) {
            main.getDatabycount(entry, function (data) {
                // console.log(data);
                if (data[0]['type'] == 'environment') {
                    dashboard.setEnviromentValue(data);
                }
                if (data[0]['type'] == 'power') {
                    dashboard.setValueSmartmeter(data);
                }
            })
        });

    }, 10000);
}

var dashboard = {

    setEnviromentValue: function (msg) {
        if (document.getElementById('co2') != null) {
            document.getElementById('co2').textContent = msg[0]['co2'] + ' ppm';
        }
        if (document.getElementById('temp') != null) {
            document.getElementById('temp').textContent = msg[0]['temperature'] + ' C';
        }
        if (document.getElementById('rh') != null) {
            document.getElementById('rh').textContent = msg[0]['humidity'] + ' %';
        }
    },

    setValueSmartmeter: function (msg) {
        if (document.getElementById('smartmeterphase1v') != null) {
            document.getElementById('smartmeterphase1v').textContent = msg[0]['PhaseAVoltage'] + ' V';
        }
        if (document.getElementById('smartmeterphase2v') != null) {
            document.getElementById('smartmeterphase2v').textContent = msg[0]['PhaseBVoltage'] + ' V';
        }
        if (document.getElementById('smartmeterphase3v') != null) {
            document.getElementById('smartmeterphase3v').textContent = msg[0]['PhaseCVoltage'] + ' V';
        }

        if (document.getElementById('smartmeterphase1A') != null) {
            document.getElementById('smartmeterphase1A').textContent = msg[0]['PhaseACurrent'] + ' A';
        }
        if (document.getElementById('smartmeterphase2A') != null) {
            document.getElementById('smartmeterphase2A').textContent = msg[0]['PhaseBCurrent'] + ' A';
        }
        if (document.getElementById('smartmeterphase3A') != null) {
            document.getElementById('smartmeterphase3A').textContent = msg[0]['PhaseCCurrent'] + ' A';
        }
        if (document.getElementById('smartmeterpower') != null) {
            document.getElementById('smartmeterpower').textContent = msg[0]['TotalEnergy'] + ' kWh';
        }

    }

};
