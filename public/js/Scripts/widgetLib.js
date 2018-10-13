var widgetLib = {
    getWidgetHTML: function (data, gridster, element, callback) {
        $.get("/BMS/widgets_html/" + data, function (html_string) {
            gridster.add_widget.apply(gridster, [html_string], element[1], element[2]);
            // console.log('passe 1');
            return callback('success');

        }, 'html');


    },

    setHTMLtoWidgets: function () {
        // console.log('passe 2');

        if (document.getElementById('environment') != null) {
            console.log( $('#environment').data("id") );
            // $('#mydiv').data('myval',20); //setter
        }
    },

};