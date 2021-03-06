define({ "api": [
  {
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "optional": false,
            "field": "varname1",
            "description": "<p>No type.</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "varname2",
            "description": "<p>With type.</p>"
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "./doc/main.js",
    "group": "D__Dialog_IoT_BMS_doc_main_js",
    "groupTitle": "D__Dialog_IoT_BMS_doc_main_js",
    "name": ""
  },
  {
    "type": "get",
    "url": "/widgets/device/:device?count=1",
    "title": "data by number of count (deprecated API)",
    "name": "get_data_for_widgets",
    "group": "Dashboard",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "device",
            "description": "<p>deviceId of the device (BMS database).</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "count",
            "description": "<p>number of events.</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "array",
            "optional": false,
            "field": "data",
            "description": "<p>of the device.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "\nHTTP/1.1 200 OK\n{\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "./routes/data.js",
    "groupTitle": "Dashboard"
  },
  {
    "type": "get",
    "url": "widgets/entity/:entityId",
    "title": "Request device by entityId",
    "name": "get_devices_by_entityId",
    "group": "Dashboard",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "entityId",
            "description": "<p>entity of the building.</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "array",
            "optional": false,
            "field": "device",
            "description": "<p>list of the entity.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n[\n  10,\n  20\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "./routes/data.js",
    "groupTitle": "Dashboard"
  },
  {
    "type": "get",
    "url": "db/detailview/device/:deviceId?type='widgetType'&count='nEvents'&stDate='startDate'&enDate='endDate'&minMaxflag='0'",
    "title": "dataQuary",
    "name": "get_history_data",
    "group": "Dashboard",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "deviceId",
            "description": "<p>deviceId of the device.</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "count",
            "description": "<p>number of events.</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "startDate",
            "description": "<p>(should URL encoded).</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "endDate",
            "description": "<p>(should URL encoded).</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "minMaxflag",
            "description": "<p>(can be 0 or 1. if 1 it will calculate the min/ max/ avg).</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "type",
            "description": "<p>of the widget.</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "array",
            "optional": false,
            "field": "data",
            "description": "<p>of the device.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "http://localhost:3000/BMS/db/detailview/device/17?type=environment&count=1&stDate=2018-08-08%2012%3A00%3A00&enDate=2018-08-10%2012%3A00%3A00\n\nHTTP/1.1 200 OK\n{\n    \"2507\":[{\"stateName_s\":\"none\",\"timestamp_s\":\"1533796157429\",\"time_s\":\"2018-08-09T11:59:17.429\",\"mac_s\":\"en000004\"}]\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "./routes/data.js",
    "groupTitle": "Dashboard"
  },
  {
    "type": "get",
    "url": "/widgets/:nodeId",
    "title": "get widget data by entityId",
    "name": "get_widget_data",
    "group": "Dashboard",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "entityId",
            "description": "<p>of the building.</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "array",
            "optional": false,
            "field": "data",
            "description": "<p>of the device.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "\nHTTP/1.1 200 OK\n{\n [[\"enviroment\",1,1],[\"power\",2,1]]\n}\n\n[widget type, x coordinate, y coordinate]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "./routes/data.js",
    "groupTitle": "Dashboard"
  }
] });
