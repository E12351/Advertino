package dlg.lk.bms.controller;

import dlg.lk.bms.dto.device.DeviceDTO;
import dlg.lk.bms.dto.device.EventDTO;
import dlg.lk.bms.dto.device.EventParameterDTO;
import dlg.lk.bms.entity.Device;
import dlg.lk.bms.entity.Entity;
import dlg.lk.bms.entity.Event;
import dlg.lk.bms.entity.EventParameter;
import dlg.lk.bms.exception.CustomException;
import dlg.lk.bms.service.DeviceService;
import dlg.lk.bms.service.EntityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class DeviceController {

    @Autowired
    EntityService entityService;

    @Autowired
    DeviceService deviceService;

    @ApiOperation(value = "Get all devices")
    @RequestMapping(value = "/api/devices", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<DeviceDTO>> findAll() {
        Collection<Device> devices = deviceService.findAll();
        List<DeviceDTO> deviceDTOS = new ArrayList<>();
        for(Device device : devices) {
            deviceDTOS.add(fromDevice(device));
        }

        return new ResponseEntity<>(deviceDTOS, HttpStatus.OK);
    }

    @ApiOperation(value = "Get one device by deviceId")
    @RequestMapping(value = "/api/devices/{deviceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceDTO> findOne(@PathVariable long deviceId) {
        Device device = deviceService.fineOne(deviceId).get();
        DeviceDTO deviceDTO = fromDevice(device);

        return new ResponseEntity<>(deviceDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a device")
    @RequestMapping(value = "/api/devices", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceDTO> create(@ApiParam(value = "device id, event ids and event parameter ids are not required.", required = true)
                                                @RequestBody DeviceDTO deviceDTO) {
        Device device = toDevice(deviceDTO);
        Device savedDevice = deviceService.create(device);
        DeviceDTO savedDeviceDTO = fromDevice(savedDevice);

        return new ResponseEntity<>(savedDeviceDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Update a device")
    @RequestMapping(value = "/api/devices", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceDTO> modify(@RequestBody DeviceDTO deviceDTO) {
        Device device = toDevice(deviceDTO);
        Device savedDevice = deviceService.update(device);
        DeviceDTO savedDeviceDTO = fromDevice(savedDevice);

        return new ResponseEntity<>(savedDeviceDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a device")
    @RequestMapping(value = "/api/devices/{deviceId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable long deviceId) {
        Optional<Device> device = deviceService.fineOne(deviceId);

        if(device.isPresent()) {
            deviceService.delete(device.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Device toDevice(DeviceDTO deviceDTO) {
        Device device = new Device();

        if(deviceDTO.getId() != null && deviceDTO.getId() != 0) {
            device.setId(deviceDTO.getId());
        }

        device.setIotID(deviceDTO.getIotID());
        device.setMacAddress(deviceDTO.getMacAddress());
        device.setRemark(deviceDTO.getRemark());

        List<Entity> entities = new ArrayList<>();
        for(Long entityId : deviceDTO.getEntityIds()) {
            Optional<Entity> entity = entityService.findOne(entityId);

            if (!entity.isPresent()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Entity Not Found.");
            }

            entities.add(entity.get());
        }
        device.setEntities(entities);
        device.setVirtual(deviceDTO.isVirtual());

        List<Event> events = new ArrayList<>();
        for(EventDTO eventDTO : deviceDTO.getEvents()) {
            Event event = toEvent(eventDTO);
            event.setDevice(device);
            events.add(event);
        }
        device.setEvents(events);

        return device;
    }

    private DeviceDTO fromDevice(Device device) {
        DeviceDTO deviceDTO = new DeviceDTO();

        deviceDTO.setId(device.getId());
        deviceDTO.setIotID(device.getIotID());
        deviceDTO.setMacAddress(device.getMacAddress());
        deviceDTO.setRemark(device.getRemark());

        List<Long> entityIds = new ArrayList<>();
        for(Entity entity : device.getEntities()) {
            entityIds.add(entity.getId());
        }
        deviceDTO.setEntityIds(entityIds);
        deviceDTO.setVirtual(device.isVirtual());

        List<EventDTO> eventDTOS = new ArrayList<>();
        for(Event event : device.getEvents()) {
            EventDTO eventDTO = fromEvent(event);
            eventDTOS.add(eventDTO);
        }

        deviceDTO.setEvents(eventDTOS);

        return deviceDTO;
    }

    private Event toEvent(EventDTO eventDTO) {
        Event event = new Event();
        if(eventDTO.getId() != 0) {
            event.setId(eventDTO.getId());
        }
        event.setName(eventDTO.getName());
        event.setType(eventDTO.getType());

        List<EventParameter> eventParameters = new ArrayList<>();
        for(EventParameterDTO eventParameterDTO : eventDTO.getEventParameters()) {
            EventParameter eventParameter = toEventParameter(eventParameterDTO);
            eventParameter.setEvent(event);
            eventParameters.add(eventParameter);
        }
        event.setEventParameters(eventParameters);

        return event;
    }

    private EventDTO fromEvent(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setType(event.getType());
        eventDTO.setName(event.getName());

        List<EventParameterDTO> eventParameterDTOS = new ArrayList<>();
        for(EventParameter eventParameter : event.getEventParameters()) {
            EventParameterDTO eventParameterDTO = fromEventParameter(eventParameter);
            eventParameterDTOS.add(eventParameterDTO);
        }

        eventDTO.setEventParameters(eventParameterDTOS);

        return eventDTO;
    }

    private EventParameter toEventParameter(EventParameterDTO eventParameterDTO) {
        EventParameter eventParameter = new EventParameter();
        if(eventParameterDTO.getId() != 0) {
            eventParameter.setId(eventParameterDTO.getId());
        }
        eventParameter.setName(eventParameterDTO.getName());
        eventParameter.setMappedName(eventParameterDTO.getMappedName());

        return eventParameter;
    }

    private EventParameterDTO fromEventParameter(EventParameter eventParameter) {
        EventParameterDTO eventParameterDTO = new EventParameterDTO();
        eventParameterDTO.setId(eventParameter.getId());
        eventParameterDTO.setName(eventParameter.getName());
        eventParameterDTO.setMappedName(eventParameter.getMappedName());

        return eventParameterDTO;
    }
}
